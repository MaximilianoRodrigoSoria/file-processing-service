package com.ar.laboratory.fileprocessingservice.processing.application.outbound.port;

/** Puerto de salida para procesar el archivo y devolver la clave del resultado. */
public interface FileProcessorPort {
    String process(String storageKey);
}
