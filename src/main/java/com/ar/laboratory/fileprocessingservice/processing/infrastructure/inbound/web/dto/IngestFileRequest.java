package com.ar.laboratory.fileprocessingservice.processing.infrastructure.inbound.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Ingesta de un archivo ya subido al object store. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngestFileRequest {

    @NotBlank(message = "El filename es obligatorio")
    private String filename;

    @NotBlank(message = "El storageKey es obligatorio")
    private String storageKey;
}
