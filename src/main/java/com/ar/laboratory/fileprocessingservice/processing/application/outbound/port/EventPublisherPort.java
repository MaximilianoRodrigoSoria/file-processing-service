package com.ar.laboratory.fileprocessingservice.processing.application.outbound.port;

import com.ar.laboratory.fileprocessingservice.processing.application.model.FileUploadedEvent;

/** Puerto de salida para publicar el evento de archivo subido (a Kafka). */
public interface EventPublisherPort {
    void publishFileUploaded(FileUploadedEvent event);
}
