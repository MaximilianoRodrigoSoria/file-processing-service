package com.ar.laboratory.fileprocessingservice.processing.application.outbound.port;

import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de tareas. */
public interface TaskRepositoryPort {
    ProcessingTask save(ProcessingTask task);

    Optional<ProcessingTask> findById(UUID id);
}
