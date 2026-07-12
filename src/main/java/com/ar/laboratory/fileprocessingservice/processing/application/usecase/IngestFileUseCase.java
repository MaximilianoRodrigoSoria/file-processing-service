package com.ar.laboratory.fileprocessingservice.processing.application.usecase;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.IngestFileCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.model.FileUploadedEvent;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.EventPublisherPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.TaskRepositoryPort;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Registra la tarea y publica el evento para procesamiento asíncrono. POJO sin framework. */
@Slf4j
@RequiredArgsConstructor
public class IngestFileUseCase implements IngestFileCommand {

    private final TaskRepositoryPort tasks;
    private final EventPublisherPort publisher;

    @Override
    public ProcessingTask execute(String filename, String storageKey) {
        ProcessingTask task =
                tasks.save(ProcessingTask.pending(filename, storageKey, Instant.now()));
        publisher.publishFileUploaded(new FileUploadedEvent(task.getId(), storageKey));
        log.info("Archivo ingerido task={} key={}", task.getId(), storageKey);
        return task;
    }
}
