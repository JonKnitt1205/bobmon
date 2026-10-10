CREATE TABLE IF NOT EXISTS system_metrics (
    id BIGSERIAL PRIMARY KEY,
    captured_at TEXT NOT NULL,
    memory_usage DOUBLE PRECISION NOT NULL,
    cpu_usage DOUBLE PRECISION NOT NULL,
    gpu_memory_usages TEXT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_system_metrics_captured_at
    ON system_metrics (captured_at);
