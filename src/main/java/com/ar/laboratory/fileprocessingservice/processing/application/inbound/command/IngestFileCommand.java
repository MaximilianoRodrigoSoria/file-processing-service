package com.ar.laboratory.fileprocessingservice.processing.application.inbound.command;

import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;

/** Puerto de entrada: ingestar un archivo y encolar su procesamiento. */
public interface IngestFileCommand {
    ProcessingTask execute(String filename, String storageKey);
}
