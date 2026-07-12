package com.ar.laboratory.fileprocessingservice.processing.infrastructure.web;

import com.ar.laboratory.fileprocessingservice.processing.application.inbound.command.ProcessFileCommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Tests de integración con PostgreSQL real. Kafka está desactivado (perfil test); el procesamiento
 * se dispara manualmente vía {@link ProcessFileCommand}.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@ActiveProfiles("test")
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("ProcessingController - Integration Tests")
class ProcessingControllerIT {

    private static final String BASE = "/file-processing-service/api/v1";

    @Container @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @LocalServerPort private int port;
    @Autowired private ProcessFileCommand processFileCommand;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    @DisplayName("ingesta (202 PENDING) → procesar → COMPLETED con resultKey")
    void flow() throws Exception {
        byte[] bytes =
                client.post()
                        .uri(BASE + "/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("filename", "foto.png", "storageKey", "uploads/foto.png"))
                        .exchange()
                        .expectStatus()
                        .isAccepted()
                        .expectBody()
                        .jsonPath("$.status")
                        .isEqualTo("PENDING")
                        .returnResult()
                        .getResponseBodyContent();
        JsonNode task = objectMapper.readTree(bytes);
        UUID id = UUID.fromString(task.get("id").asText());

        processFileCommand.execute(id);

        client.get()
                .uri(BASE + "/tasks/" + id)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.status")
                .isEqualTo("COMPLETED")
                .jsonPath("$.resultKey")
                .isNotEmpty();
    }

    @Test
    @DisplayName("tarea inexistente → 404")
    void missing() {
        client.get()
                .uri(BASE + "/tasks/" + UUID.randomUUID())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("ingesta sin storageKey → 400")
    void invalid() {
        client.post()
                .uri(BASE + "/files")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("filename", "x.png"))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
