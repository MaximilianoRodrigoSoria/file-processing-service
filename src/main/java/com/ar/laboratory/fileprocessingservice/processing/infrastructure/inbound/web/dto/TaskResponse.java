package com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Vista de una tarea. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse {
    private UUID id;
    private String filename;
    private String status;
    private String resultKey;
    private Instant createdAt;
}
