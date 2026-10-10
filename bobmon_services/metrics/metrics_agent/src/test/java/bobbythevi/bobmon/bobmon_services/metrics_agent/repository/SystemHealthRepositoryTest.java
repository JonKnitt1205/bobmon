package bobbythevi.bobmon.bobmon_services.metrics_agent.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;

class SystemHealthRepositoryTest {

    @Test
    void writeMetricsStoresAndReadsMetricsForTimeWindow() throws Exception {
        Path tempDb = Files.createTempFile("bobmon-metrics", ".db");
        DataSource dataSource = new DriverManagerDataSource(
            "jdbc:h2:mem:" + tempDb.toAbsolutePath() + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
            "sa",
            "sa"
        );
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("""
            CREATE TABLE system_metrics (
                id BIGSERIAL PRIMARY KEY,
                captured_at TEXT NOT NULL,
                memory_usage DOUBLE PRECISION NOT NULL,
                cpu_usage DOUBLE PRECISION NOT NULL,
                gpu_memory_usages TEXT NOT NULL
            );
            """);

        SystemHealthRepository repository = new SystemHealthRepository(jdbcTemplate);
        ZonedDateTime timestamp = ZonedDateTime.now();
        SystemStatsResource metric = SystemStatsResource.builder()
            .timestamp(timestamp)
            .memoryUsage(42.5)
            .cpuUsage(73.0)
            .gpuMemoryUsages(List.of(22.0, 33.0, 44.0))
            .build();

        repository.writeMetrics(metric);
        List<SystemStatsResource> results = repository.getSystemHealthStats(
            timestamp.minusMinutes(1),
            timestamp.plusMinutes(1)
        );

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getMemoryUsage()).isEqualTo(42.5);
        assertThat(results.getFirst().getCpuUsage()).isEqualTo(73.0);
        assertThat(results.getFirst().getGpuMemoryUsages()).containsExactly(22.0, 33.0, 44.0);
    }
}
