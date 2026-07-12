package com.ar.laboratory.fileprocessingservice.processing.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ProcessingTask")
class ProcessingTaskTest {

    private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

    @Test
    @DisplayName("ciclo: PENDING → PROCESSING → COMPLETED")
    void lifecycle() {
        ProcessingTask t = ProcessingTask.pending("a.png", "k", NOW);
        assertThat(t.getStatus()).isEqualTo(TaskStatus.PENDING);
        assertThat(t.isTerminal()).isFalse();
        t.markProcessing(NOW);
        t.markCompleted("res", NOW);
        assertThat(t.getStatus()).isEqualTo(TaskStatus.COMPLETED);
        assertThat(t.getResultKey()).isEqualTo("res");
        assertThat(t.isTerminal()).isTrue();
    }

    @Test
    @DisplayName("markFailed deja la tarea en estado terminal con el error")
    void failed() {
        ProcessingTask t = ProcessingTask.pending("a.png", "k", NOW);
        t.markFailed("boom", NOW);
        assertThat(t.getStatus()).isEqualTo(TaskStatus.FAILED);
        assertThat(t.getLastError()).isEqualTo("boom");
        assertThat(t.isTerminal()).isTrue();
    }
}
