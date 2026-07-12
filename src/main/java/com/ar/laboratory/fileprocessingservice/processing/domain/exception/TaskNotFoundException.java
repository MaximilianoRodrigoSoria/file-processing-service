package com.ar.laboratory.fileprocessingservice.processing.domain.exception;

import java.util.UUID;

/** No se encontró la tarea. */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID id) {
        super("Tarea no encontrada: " + id);
    }
}
