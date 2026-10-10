package bobbythevi.bobmon.bobmon_services.metrics_agent.monitor;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bobbythevi.bobmon.bobmon_services.metrics_agent.configuration.SystemInfoConfig.SystemInfoBean;
import bobbythevi.bobmon.bobmon_services.metrics_agent.repository.SystemHealthRepository;
import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class SystemHealthMonitor {
    private final SystemHealthRepository systemHealthRepository;
    private final SystemInfoBean systemInfoBean;

    private static final int SCHEDULED_DELAY_MS = 1000;

    @Scheduled(fixedRate = SCHEDULED_DELAY_MS)
    public void collectSystemStats() {
        SystemStatsResource systemStatsResource = SystemStatsResource.builder()
            .timestamp(ZonedDateTime.now())
            .memoryUsage(calculateMemoryUsage())
            .cpuUsage(calculateCpuUsage())
            .gpuMemoryUsages(calculateGpuMemoryUsages())
            .build();
        systemHealthRepository.writeMetrics(systemStatsResource);
    }

    protected double calculateMemoryUsage() {
        long totalMemory = systemInfoBean.getMemory().getTotal();
        if (totalMemory == 0) { return 100.0; }

        long availableMemory = systemInfoBean.getMemory().getAvailable();
        return (double) (totalMemory - availableMemory) / totalMemory * 100.0;
    }

    protected double calculateCpuUsage() {
        return 0.0; // Placeholder for CPU usage calculation logic TODO
    }

    protected List<Double> calculateGpuMemoryUsages() {
        return systemInfoBean.getGpuStatsMap().entrySet().stream()
            .map(entry -> {
                long totalVRam = entry.getKey().getVRam();
                if (totalVRam == 0) {
                    return 100.0;
                }

                long usedVRam = entry.getValue().getVramUsed();
                return (double) usedVRam / totalVRam * 100.0;
            })
            .toList();
    }   
}
