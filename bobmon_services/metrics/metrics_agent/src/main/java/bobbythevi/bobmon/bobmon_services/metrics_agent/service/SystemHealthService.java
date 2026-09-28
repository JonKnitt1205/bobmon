package bobbythevi.bobmon.bobmon_services.metrics_agent.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;

@Service 
public class SystemHealthService {
    public List<SystemStatsResource> getSystemHealthStats(ZonedDateTime startTime, ZonedDateTime endTime) {
        return null; // TODO
    }
}
