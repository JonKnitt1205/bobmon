package bobbythevi.bobmon.bobmon_services.metrics_agent.repository;

import org.springframework.stereotype.Repository;

import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;

@Repository 
public class SystemHealthRepository {
    public void writeMetrics(SystemStatsResource systemStatsResource) {
        // write metrics
        // TODO
    }
}
