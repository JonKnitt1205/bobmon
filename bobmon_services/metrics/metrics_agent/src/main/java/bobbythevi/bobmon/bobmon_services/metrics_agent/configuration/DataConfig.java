package bobbythevi.bobmon.bobmon_services.metrics_agent.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import bobbythevi.bobmon.bobmon_data.config.PostgresDataSourceConfig;

@Configuration
@Import(PostgresDataSourceConfig.class)
public class DataConfig {
}
