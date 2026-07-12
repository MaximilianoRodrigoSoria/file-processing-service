package com.ar.laboratory.fileprocessingservice.processing.infrastructure.notification;

import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.NotificationPort;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Notificación de ejemplo: registra el fin del procesamiento (real: WebSocket/email/webhook). */
@Slf4j
@Component
public class LogNotificationAdapter implements NotificationPort {

    @Override
    public void notifyCompleted(ProcessingTask task) {
        log.info("[notify] tarea {} completada (result={})", task.getId(), task.getResultKey());
    }
}
