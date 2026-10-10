package bobbythevi.bobmon.bobmon_services.metrics_agent.repository;

import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SystemHealthRepository {
    private final JdbcTemplate jdbcTemplate;

    public void writeMetrics(SystemStatsResource systemStatsResource) {
        String gpuValues = systemStatsResource.getGpuMemoryUsages().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));

        jdbcTemplate.update(
            "INSERT INTO system_metrics (captured_at, memory_usage, cpu_usage, gpu_memory_usages) VALUES (?, ?, ?, ?)",
            systemStatsResource.getTimestamp().toString(),
            systemStatsResource.getMemoryUsage(),
            systemStatsResource.getCpuUsage(),
            gpuValues
        );
    }

    public List<SystemStatsResource> getSystemHealthStats(ZonedDateTime startTime, ZonedDateTime endTime) {
        return jdbcTemplate.query(
            "SELECT captured_at, memory_usage, cpu_usage, gpu_memory_usages " +
                "FROM system_metrics WHERE captured_at BETWEEN ? AND ? ORDER BY captured_at ASC",
            ps -> {
                ps.setString(1, startTime.toString());
                ps.setString(2, endTime.toString());
            },
            systemStatsRowMapper()
        );
    }

    private RowMapper<SystemStatsResource> systemStatsRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            String capturedAt = rs.getString("captured_at");
            String gpuValues = rs.getString("gpu_memory_usages");
            List<Double> gpuUsageList = gpuValues == null || gpuValues.isBlank()
                ? List.of()
                : Arrays.stream(gpuValues.split(","))
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());

            return SystemStatsResource.builder()
                .timestamp(ZonedDateTime.parse(capturedAt))
                .memoryUsage(rs.getDouble("memory_usage"))
                .cpuUsage(rs.getDouble("cpu_usage"))
                .gpuMemoryUsages(gpuUsageList)
                .build();
        };
    }
}
