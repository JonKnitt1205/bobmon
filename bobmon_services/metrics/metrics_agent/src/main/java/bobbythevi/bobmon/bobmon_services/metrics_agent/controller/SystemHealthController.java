package bobbythevi.bobmon.bobmon_services.metrics_agent.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import bobbythevi.bobmon.bobmon_common_utils.RequestLogFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import bobbythevi.bobmon.bobmon_services.metrics_agent.service.SystemHealthService;
import bobbythevi.bobmon.bobmon_services.metrics_common.model.SystemStatsResource;
import bobbythevi.bobmon.bobmon_services.metrics_common.path.SystemHealthControllerConstants;
import bobbythevi.bobmon.bobmon_services.metrics_common.path.SystemHealthControllerConstants.PATH;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(SystemHealthControllerConstants.SYSTEM_HEALTH_PATH)
@Validated
@RequiredArgsConstructor
public class SystemHealthController {
    private static final Logger log = LoggerFactory.getLogger(SystemHealthController.class);

    private final SystemHealthService systemHealthService;

    @GetMapping
    public List<SystemStatsResource> getSystemHealthStats(
      @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
      @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime) {
        log.info(RequestLogFormatter.format(HttpMethod.GET.name(), PATH.SYSTEM_HEALTH.toString()));
        return systemHealthService.getSystemHealthStats(startTime, endTime);
    }
}
