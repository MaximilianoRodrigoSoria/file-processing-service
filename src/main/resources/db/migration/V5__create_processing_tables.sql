CREATE TABLE IF NOT EXISTS app.processing_tasks (
    id          UUID         PRIMARY KEY,
    filename    VARCHAR(300) NOT NULL,
    storage_key VARCHAR(500) NOT NULL,
    status      VARCHAR(20)  NOT NULL,
    result_key  VARCHAR(500),
    last_error  TEXT,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL
);
COMMENT ON TABLE app.processing_tasks IS 'Tareas de procesamiento de archivos (pipeline Kafka)';
