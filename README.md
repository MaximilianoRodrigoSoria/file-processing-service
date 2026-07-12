<!-- banner-badges -->
<p align="center">
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/" target="_blank" rel="noopener noreferrer">
    <img width="100%" src="docs/img/banner.gif" alt="File Processing (Kafka) — Maximiliano Rodrigo Soria">
  </a>
</p>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/github/license/MaximilianoRodrigoSoria/file-processing-service?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="License"></a>
  <img src="https://img.shields.io/github/last-commit/MaximilianoRodrigoSoria/file-processing-service?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Last commit">
  <img src="https://img.shields.io/github/repo-size/MaximilianoRodrigoSoria/file-processing-service?style=flat-square&labelColor=1A1C1F&color=06C69C" alt="Repo size">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-06C69C?style=flat-square&labelColor=1A1C1F&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Apache_Kafka-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=apachekafka&logoColor=white" alt="Apache_Kafka">
  <img src="https://img.shields.io/badge/MinIO-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=minio&logoColor=white" alt="MinIO">
  <img src="https://img.shields.io/badge/PostgreSQL-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Docker-•-06C69C?style=flat-square&labelColor=1A1C1F&logo=docker&logoColor=white" alt="Docker">
</p>

# File Processing (Kafka)

Procesamiento de archivos en segundo plano dirigido por eventos: el usuario sube una imagen o documento, se publica un evento en Kafka y un worker lo procesa usando S3 local (MinIO), con notificacion al terminar.

> Proyecto de portafolio backend. Sigue el estandar de **arquitectura hexagonal (Ports & Adapters)**, Java 21 y Spring Boot, con quality gates (Spotless, Checkstyle, PMD, SpotBugs, ArchUnit), testing con Testcontainers y observabilidad (Micrometer + Prometheus).

## Caracteristicas

- Ingesta de archivos y persistencia en S3 local (MinIO)
- Publicacion de eventos en Kafka (producer / consumer group)
- Worker de procesamiento en segundo plano
- Idempotencia del consumer (entrega at-least-once)
- Reintentos con retry topic y Dead-Letter Queue
- Notificacion al usuario cuando el procesamiento termina
- Observabilidad del lag del consumer y throughput

## Stack

Java 21 · Apache Kafka · MinIO · PostgreSQL · Docker · Gradle · Flyway · Docker · JUnit 5 · Testcontainers

## Arquitectura

Organizado por **feature** en capas `domain -> application -> infrastructure`, con la regla de dependencia verificada por ArchUnit. La logica de negocio (dominio y casos de uso) no depende de framework ni de infraestructura; los adaptadores (web, persistencia, mensajeria) implementan puertos definidos por la aplicacion.

## API

Contexto `/file-processing-service`. `POST /api/v1/files` ingesta un archivo y publica un evento en **Kafka**; el consumer lo procesa en segundo plano; `GET /api/v1/tasks/{id}` da el estado.

## Estado

✅ Nucleo funcional implementado: ingesta de archivos, publicacion de evento en **Kafka** (producer), consumer que procesa en background, procesamiento idempotente, notificacion al completar y estados de la tarea. Persistencia JPA/PostgreSQL + migracion Flyway, tests (unit + Testcontainers; en tests Kafka esta desactivado y el procesamiento se dispara a mano). Kafka es opcional por `app.kafka.enabled`; con un fallback no-op para dev/test. Capa siguiente: S3/MinIO real, retry topic + DLQ, y notificacion en tiempo real.

---

<p align="center">
  <strong>Maximiliano Rodrigo Soria</strong><br>
  <a href="https://www.linkedin.com/in/soriamaximilianorodrigo/">LinkedIn</a> · <a href="mailto:maximilianorodrigosoria@gmail.com">maximilianorodrigosoria@gmail.com</a>
</p>
