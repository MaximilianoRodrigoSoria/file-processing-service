package com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.controller;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.GetTaskCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.IngestFileCommand;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.dto.IngestFileRequest;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.dto.TaskResponse;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.mapper.TaskDtoMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** API de ingesta y consulta de tareas de procesamiento. */
@Tag(name = "Processing", description = "Ingesta de archivos y estado del procesamiento")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RateLimiter(name = "processing-api")
public class ProcessingController {

    private final IngestFileCommand ingestFileCommand;
    private final GetTaskCommand getTaskCommand;
    private final TaskDtoMapper mapper;

    @PostMapping("/files")
    public ResponseEntity<TaskResponse> ingest(@Valid @RequestBody IngestFileRequest request) {
        var task = ingestFileCommand.execute(request.getFilename(), request.getStorageKey());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toResponse(task));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toResponse(getTaskCommand.execute(id)));
    }
}
