package com.ar.laboratory.fileprocessingservice.processing.domain.model;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Tarea de procesamiento de un archivo subido. */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingTask {

    private UUID id;
    private String filename;
    private String storageKey;
    private TaskStatus status;
    private String resultKey;
    private String lastError;
    private Instant createdAt;
    private Instant updatedAt;

    public static ProcessingTask pending(String filename, String storageKey, Instant now) {
        return ProcessingTask.builder()
                .id(UUID.randomUUID())
                .filename(filename)
                .storageKey(storageKey)
                .status(TaskStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    /** Idempotente: si ya se completó, no reprocesa. */
    public boolean isTerminal() {
        return status == TaskStatus.COMPLETED || status == TaskStatus.FAILED;
    }

    public void markProcessing(Instant now) {
        this.status = TaskStatus.PROCESSING;
        this.updatedAt = now;
    }

    public void markCompleted(String resultKey, Instant now) {
        this.status = TaskStatus.COMPLETED;
        this.resultKey = resultKey;
        this.updatedAt = now;
    }

    public void markFailed(String reason, Instant now) {
        this.status = TaskStatus.FAILED;
        this.lastError = reason;
        this.updatedAt = now;
    }
}
