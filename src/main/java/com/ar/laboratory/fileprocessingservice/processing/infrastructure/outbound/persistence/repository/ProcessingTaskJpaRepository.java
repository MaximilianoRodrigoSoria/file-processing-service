package com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.repository;

import com.ar.laboratory.fileprocessingservice.processing.infrastructure.outbound.persistence.entity.ProcessingTaskEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio JPA de tareas. */
public interface ProcessingTaskJpaRepository extends JpaRepository<ProcessingTaskEntity, UUID> {}
