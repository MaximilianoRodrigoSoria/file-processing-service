package com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.mapper;

import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.TaskStatus;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.entity.ProcessingTaskEntity;
import org.springframework.stereotype.Component;

/** Conversión ProcessingTaskEntity ↔ ProcessingTask. */
@Component
public class ProcessingTaskEntityMapper {

    public ProcessingTask toDomain(ProcessingTaskEntity e) {
        if (e == null) {
            return null;
        }
        return ProcessingTask.builder()
                .id(e.getId())
                .filename(e.getFilename())
                .storageKey(e.getStorageKey())
                .status(e.getStatus() == null ? null : TaskStatus.valueOf(e.getStatus()))
                .resultKey(e.getResultKey())
                .lastError(e.getLastError())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public ProcessingTaskEntity toEntity(ProcessingTask t) {
        return ProcessingTaskEntity.builder()
                .id(t.getId())
                .filename(t.getFilename())
                .storageKey(t.getStorageKey())
                .status(t.getStatus() == null ? null : t.getStatus().name())
                .resultKey(t.getResultKey())
                .lastError(t.getLastError())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }
}
