package com.ar.laboratory.fileprocessingservice.processing.infrastructure.kafka;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.ProcessFileCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.model.FileUploadedEvent;
import com.ar.laboratory.fileprocessingservice.shared.infrastructure.util.JsonHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/** Consumer de Kafka: procesa el evento de archivo subido. Activo con {@code app.kafka.enabled=true}. */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class FileUploadedListener {

    private final ProcessFileCommand processFileCommand;
    private final JsonHandler jsonHandler;

    @KafkaListener(topics = "${app.kafka.topic:file.uploaded}", groupId = "${app.kafka.group-id:file-processing}")
    public void onMessage(String payload) {
        FileUploadedEvent event = jsonHandler.fromJson(payload, FileUploadedEvent.class);
        log.info("Evento recibido para task {}", event.taskId());
        processFileCommand.execute(event.taskId());
    }
}
