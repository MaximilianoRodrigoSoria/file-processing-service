package com.ar.laboratory.fileprocessingservice.processing.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ar.laboratory.fileprocessingservice.processing.application.model.FileUploadedEvent;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.EventPublisherPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.FileProcessorPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.NotificationPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.TaskRepositoryPort;
import com.ar.laboratory.fileprocessingservice.processing.domain.exception.TaskNotFoundException;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.TaskStatus;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Casos de uso de procesamiento")
class ProcessingUseCasesTest {

    @Mock private TaskRepositoryPort tasks;
    @Mock private EventPublisherPort publisher;
    @Mock private FileProcessorPort processor;
    @Mock private NotificationPort notification;

    @Test
    @DisplayName("ingest crea PENDING y publica el evento")
    void ingest() {
        when(tasks.save(any(ProcessingTask.class))).thenAnswer(inv -> inv.getArgument(0));

        ProcessingTask task = new IngestFileUseCase(tasks, publisher).execute("a.png", "k");

        assertThat(task.getStatus()).isEqualTo(TaskStatus.PENDING);
        verify(publisher).publishFileUploaded(any(FileUploadedEvent.class));
    }

    @Test
    @DisplayName("process completa la tarea y notifica")
    void process() {
        UUID id = UUID.randomUUID();
        ProcessingTask pending = ProcessingTask.pending("a.png", "k", Instant.now());
        when(tasks.findById(id)).thenReturn(Optional.of(pending));
        when(processor.process("k")).thenReturn("processed/k");
        when(tasks.save(any(ProcessingTask.class))).thenAnswer(inv -> inv.getArgument(0));

        new ProcessFileUseCase(tasks, processor, notification).execute(id);

        ArgumentCaptor<ProcessingTask> captor = ArgumentCaptor.forClass(ProcessingTask.class);
        verify(tasks).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(captor.getValue().getResultKey()).isEqualTo("processed/k");
        verify(notification).notifyCompleted(any(ProcessingTask.class));
    }

    @Test
    @DisplayName("process de una tarea ya terminal es idempotente")
    void idempotent() {
        UUID id = UUID.randomUUID();
        ProcessingTask done = ProcessingTask.pending("a.png", "k", Instant.now());
        done.markProcessing(Instant.now());
        done.markCompleted("res", Instant.now());
        when(tasks.findById(id)).thenReturn(Optional.of(done));

        new ProcessFileUseCase(tasks, processor, notification).execute(id);

        verify(processor, never()).process(any());
        verify(tasks, never()).save(any());
    }

    @Test
    @DisplayName("process de tarea inexistente → TaskNotFound")
    void notFound() {
        UUID id = UUID.randomUUID();
        when(tasks.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(
                        () -> new ProcessFileUseCase(tasks, processor, notification).execute(id))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
