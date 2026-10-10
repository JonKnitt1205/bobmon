package bobbythevi.bobmon.bobmon_services.metrics_common.model;

import java.time.ZonedDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "SystemStatsResource", description = "System resource usage snapshot generated at a point in time")
public class SystemStatsResource {
    @Schema(description = "Current memory usage percentage", minimum = "0")
    @PositiveOrZero
    private final double memoryUsage;

    @Schema(description = "Current CPU usage percentage", minimum = "0")
    @PositiveOrZero
    private final double cpuUsage;

    @Schema(description = "Per-GPU memory usage percentages")
    @NotNull
    @Valid
    private final List<@NotNull @PositiveOrZero Double> gpuMemoryUsages;

    @Schema(description = "Timestamp when the metrics were captured")
    @NotNull
    private final ZonedDateTime timestamp;
}
