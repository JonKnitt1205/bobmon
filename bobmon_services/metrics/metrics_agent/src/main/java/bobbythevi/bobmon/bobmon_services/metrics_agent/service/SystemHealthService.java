package bobbythevi.bobmon.bobmon_services.metrics_agent.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import bobbythevi.bobmon.bobmon_services.metrics_agent.repository.SystemHealthRepository;
import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class SystemHealthService {
    private final SystemHealthRepository systemHealthRepository;

    public List<SystemStatsResource> getSystemHealthStats(ZonedDateTime startTime, ZonedDateTime endTime) {
        return systemHealthRepository.getSystemHealthStats(startTime, endTime);
    }
}
