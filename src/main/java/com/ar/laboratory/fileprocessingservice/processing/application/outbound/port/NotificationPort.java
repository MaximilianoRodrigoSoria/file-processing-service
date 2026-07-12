package com.ar.laboratory.fileprocessingservice.processing.application.outbound.port;

import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;

/** Puerto de salida para notificar al usuario cuando el procesamiento termina. */
public interface NotificationPort {
    void notifyCompleted(ProcessingTask task);
}
