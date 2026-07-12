package com.ar.laboratory.fileprocessingservice.processing.infrastructure.kafka;

import com.ar.laboratory.fileprocessingservice.processing.application.model.FileUploadedEvent;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.EventPublisherPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/** Publisher no-op cuando Kafka está deshabilitado (dev/tests). */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpEventPublisher implements EventPublisherPort {

    @Override
    public void publishFileUploaded(FileUploadedEvent event) {
        log.debug("[no-op] evento file.uploaded para task {} (Kafka deshabilitado)", event.taskId());
    }
}
