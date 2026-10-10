package bobbythevi.bobmon.bobmon_services.metrics_agent.repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;

@Repository
public class SystemHealthRepository {

    private static final String INSERT_SQL = """
        INSERT INTO system_metrics (captured_at, memory_usage, cpu_usage, gpu_memory_usages)
        VALUES (?, ?, ?, ?)
        """;

    private static final String SELECT_SQL = """
        SELECT captured_at, memory_usage, cpu_usage, gpu_memory_usages
        FROM system_metrics
        WHERE captured_at BETWEEN ? AND ?
        ORDER BY captured_at DESC
        """;

    private final JdbcTemplate jdbcTemplate;

    public SystemHealthRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeMetrics(SystemStatsResource systemStatsResource) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, systemStatsResource.getTimestamp().toOffsetDateTime().toString());
            preparedStatement.setDouble(2, systemStatsResource.getMemoryUsage());
            preparedStatement.setDouble(3, systemStatsResource.getCpuUsage());
            preparedStatement.setArray(4, connection.createArrayOf("DOUBLE",
                systemStatsResource.getGpuMemoryUsages().toArray(new Double[0])));
            return preparedStatement;
        });
    }

    public List<SystemStatsResource> getSystemHealthStats(ZonedDateTime startTime, ZonedDateTime endTime) {
        return jdbcTemplate.query(SELECT_SQL, ps -> {
            ps.setString(1, startTime.toOffsetDateTime().toString());
            ps.setString(2, endTime.toOffsetDateTime().toString());
        }, rowMapper());
    }

    private RowMapper<SystemStatsResource> rowMapper() {
        return (rs, rowNum) -> {
            Array gpuMemoryUsages = rs.getArray("gpu_memory_usages");
            List<Double> gpuValues = gpuMemoryUsages == null
                ? List.of()
                : Arrays.stream((Object[]) gpuMemoryUsages.getArray())
                    .map(value -> ((Number) value).doubleValue())
                    .toList();

            return SystemStatsResource.builder()
                .timestamp(ZonedDateTime.parse(rs.getString("captured_at")))
                .memoryUsage(rs.getDouble("memory_usage"))
                .cpuUsage(rs.getDouble("cpu_usage"))
                .gpuMemoryUsages(gpuValues)
                .build();
        };
    }
}
