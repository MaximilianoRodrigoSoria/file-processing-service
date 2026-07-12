package com.ar.laboratory.fileprocessingservice.processing.infrastructure.config;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.GetTaskCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.IngestFileCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.ProcessFileCommand;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.EventPublisherPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.FileProcessorPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.NotificationPort;
import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.TaskRepositoryPort;
import com.ar.laboratory.fileprocessingservice.processing.application.usecase.GetTaskUseCase;
import com.ar.laboratory.fileprocessingservice.processing.application.usecase.IngestFileUseCase;
import com.ar.laboratory.fileprocessingservice.processing.application.usecase.ProcessFileUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Wiring de los casos de uso de procesamiento. */
@Configuration
public class ProcessingConfig {

    @Bean
    public IngestFileCommand ingestFileCommand(
            TaskRepositoryPort tasks, EventPublisherPort publisher) {
        return new IngestFileUseCase(tasks, publisher);
    }

    @Bean
    public ProcessFileCommand processFileCommand(
            TaskRepositoryPort tasks, FileProcessorPort processor, NotificationPort notification) {
        return new ProcessFileUseCase(tasks, processor, notification);
    }

    @Bean
    public GetTaskCommand getTaskCommand(TaskRepositoryPort tasks) {
        return new GetTaskUseCase(tasks);
    }
}
