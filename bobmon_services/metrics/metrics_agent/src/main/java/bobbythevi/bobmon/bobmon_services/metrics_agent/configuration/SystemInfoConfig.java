package bobbythevi.bobmon.bobmon_services.metrics_agent.configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.Getter;
import oshi.ffm.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GpuStats;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

@Configuration 
public class SystemInfoConfig {
    @Bean
    public SystemInfoBean systemInfoBean() {
        return new SystemInfoBean();
    }

    @Component
    @Getter
    public static class SystemInfoBean {
        @Getter(AccessLevel.NONE)
        private final HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
        
        private final Map<GraphicsCard, GpuStats> gpuStatsMap = hardware.getGraphicsCards().stream()
            .collect(Collectors.toMap(Function.identity(), gc -> gc.createStatsSession()));
        private final GlobalMemory memory = hardware.getMemory();
        private final CentralProcessor processor = hardware.getProcessor();

        @PreDestroy 
        public void destroy() {
            gpuStatsMap.values().forEach(gpuStat -> gpuStat.close());
        }
    }
}
