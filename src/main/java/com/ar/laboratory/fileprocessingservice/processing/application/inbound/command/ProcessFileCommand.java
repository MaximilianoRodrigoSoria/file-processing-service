package com.ar.laboratory.fileprocessingservice.processing.application.inbound.command;

import java.util.UUID;

/** Puerto de entrada: procesar una tarea (disparado por el consumer de Kafka). */
public interface ProcessFileCommand {
    void execute(UUID taskId);
}
