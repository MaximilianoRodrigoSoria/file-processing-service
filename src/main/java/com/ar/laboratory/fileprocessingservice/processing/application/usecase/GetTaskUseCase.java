package com.ar.laboratory.fileprocessingservice.processing.application.usecase;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.GetTaskCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.TaskRepositoryPort;
import com.ar.laboratory.fileprocessingservice.processing.domain.exception.TaskNotFoundException;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/** Consulta una tarea. POJO sin framework. */
@RequiredArgsConstructor
public class GetTaskUseCase implements GetTaskCommand {

    private final TaskRepositoryPort tasks;

    @Override
    public ProcessingTask execute(UUID id) {
        return tasks.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }
}
