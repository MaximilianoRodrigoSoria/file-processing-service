package com.ar.laboratory.fileprocessingservice.processing.application.usecase;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.ProcessFileCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.FileProcessorPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.NotificationPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.TaskRepositoryPort;
import com.ar.laboratory.fileprocessingservice.processing.domain.exception.TaskNotFoundException;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Procesa una tarea (invocado por el consumer de Kafka). Idempotente: si ya está en estado
 * terminal, no reprocesa. Notifica al terminar.
 */
@Slf4j
@RequiredArgsConstructor
public class ProcessFileUseCase implements ProcessFileCommand {

    private final TaskRepositoryPort tasks;
    private final FileProcessorPort processor;
    private final NotificationPort notification;

    @Override
    public void execute(UUID taskId) {
        ProcessingTask task =
                tasks.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        if (task.isTerminal()) {
            log.debug("Tarea {} ya está en estado terminal → ignorada (idempotencia)", taskId);
            return;
        }
        task.markProcessing(Instant.now());
        try {
            String resultKey = processor.process(task.getStorageKey());
            task.markCompleted(resultKey, Instant.now());
            tasks.save(task);
            notification.notifyCompleted(task);
            log.info("Tarea {} COMPLETED", taskId);
        } catch (Exception e) {
            task.markFailed(e.getMessage(), Instant.now());
            tasks.save(task);
            log.error("Tarea {} FAILED", taskId, e);
        }
    }
}
