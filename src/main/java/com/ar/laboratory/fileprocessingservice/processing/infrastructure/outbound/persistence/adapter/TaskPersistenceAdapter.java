package com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.adapter;

import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.TaskRepositoryPort;
import com.ar.laboratory.fileprocessingservice.processing.domain.model.ProcessingTask;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.mapper.ProcessingTaskEntityMapper;
import com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.repository.ProcessingTaskJpaRepository;
import com.ar.laboratory.fileprocessingservice.shared.infrastructure.exception.InfrastructureException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Adaptador de persistencia de tareas. */
@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskRepositoryPort {

    private final ProcessingTaskJpaRepository repository;
    private final ProcessingTaskEntityMapper mapper;

    @Override
    public ProcessingTask save(ProcessingTask task) {
        try {
            return mapper.toDomain(repository.save(mapper.toEntity(task)));
        } catch (Exception e) {
            throw new InfrastructureException("Error guardando tarea", e);
        }
    }

    @Override
    public Optional<ProcessingTask> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
