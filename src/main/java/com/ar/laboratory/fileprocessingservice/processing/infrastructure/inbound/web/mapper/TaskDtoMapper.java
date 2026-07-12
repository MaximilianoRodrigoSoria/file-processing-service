package com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.mapper;

import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.dto.TaskResponse;
import org.springframework.stereotype.Component;

/** Conversión ProcessingTask → DTO. */
@Component
public class TaskDtoMapper {
    public TaskResponse toResponse(ProcessingTask t) {
        return TaskResponse.builder()
                .id(t.getId())
                .filename(t.getFilename())
                .status(t.getStatus() == null ? null : t.getStatus().name())
                .resultKey(t.getResultKey())
                .createdAt(t.getCreatedAt())
                .build();
    }
}
