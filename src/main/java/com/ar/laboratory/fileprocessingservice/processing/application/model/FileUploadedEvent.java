package com.ar.laboratory.fileprocessingservice.processing.application.model;

import java.util.UUID;

/** Evento publicado al subir un archivo; lo consume el worker para procesarlo. */
public record FileUploadedEvent(UUID taskId, String storageKey) {}
