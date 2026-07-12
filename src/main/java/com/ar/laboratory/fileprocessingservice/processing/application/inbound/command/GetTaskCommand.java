package com.ar.laboratory.fileprocessingservice.processing.application.inbound.command;

import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import java.util.UUID;

/** Puerto de entrada: consultar el estado de una tarea. */
public interface GetTaskCommand {
    ProcessingTask execute(UUID id);
}
