package com.ar.laboratory.fileprocessingservice.processing.infrastructure.kafka;

import com.ar.laboratory.fileprocessingservice.processing.application.model.FileUploadedEvent;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.EventPublisherPort;
import com.ar.laboratory.fileprocessingservice.shared.infrastructure.util.JsonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/** Publica el evento de archivo subido en Kafka. Activo con {@code app.kafka.enabled=true}. */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaEventPublisher implements EventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonHandler jsonHandler;
    private final String topic;

    public KafkaEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            JsonHandler jsonHandler,
            @Value("${app.kafka.topic:file.uploaded}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonHandler = jsonHandler;
        this.topic = topic;
    }

    @Override
    public void publishFileUploaded(FileUploadedEvent event) {
        kafkaTemplate.send(topic, event.taskId().toString(), jsonHandler.toJson(event));
        log.info("Evento publicado en {} para task {}", topic, event.taskId());
    }
}
