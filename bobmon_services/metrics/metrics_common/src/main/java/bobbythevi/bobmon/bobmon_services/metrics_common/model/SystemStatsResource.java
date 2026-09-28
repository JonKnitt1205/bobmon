package bobbythevi.bobmon.bobmon_services.metrics_common.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SystemStatsResource {
    @PositiveOrZero
    private final double memoryUsage;

    @PositiveOrZero
    private final double cpuUsage;

    @NotNull
    @Valid
    private final List<@NotNull @PositiveOrZero Double> gpuMemoryUsages;
}
