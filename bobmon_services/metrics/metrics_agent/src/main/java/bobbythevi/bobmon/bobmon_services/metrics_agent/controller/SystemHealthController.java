package bobbythevi.bobmon.bobmon_services.metrics_agent.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import bobbythevi.bobmon.bobmon_services.metrics_agent.controller.SystemHealthControllerConstants.PATH;
import bobbythevi.bobmon.bobmon_services.metrics_agent.service.SystemHealthService;
import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(SystemHealthControllerConstants.SYSTEM_HEALTH_PATH)
@Validated
@AllArgsConstructor
@Slf4j
public class SystemHealthController {
    private final SystemHealthService systemHealthService;

    @GetMapping
    public List<SystemStatsResource> getSystemHealthStats(
      @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
      @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime) {
        log.info(SystemHealthControllerConstants.LOG_CONTROLLER(PATH.SYSTEM_HEALTH, HttpMethod.GET));
        return systemHealthService.getSystemHealthStats(startTime, endTime);
    }
}
