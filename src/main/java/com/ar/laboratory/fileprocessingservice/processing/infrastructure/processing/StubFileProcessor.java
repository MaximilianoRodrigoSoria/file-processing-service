package com.ar.laboratory.fileprocessingservice.processing.infrastructure.processing;

import com.ar.laboratory.fileprocessingservice.processing.application.outbound.port.FileProcessorPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Procesador de ejemplo (resize/convert/OCR simulado). Devuelve la clave del resultado. */
@Slf4j
@Component
public class StubFileProcessor implements FileProcessorPort {

    @Override
    public String process(String storageKey) {
        String resultKey = "processed/" + storageKey;
        log.info("[processor] {} -> {}", storageKey, resultKey);
        return resultKey;
    }
}
