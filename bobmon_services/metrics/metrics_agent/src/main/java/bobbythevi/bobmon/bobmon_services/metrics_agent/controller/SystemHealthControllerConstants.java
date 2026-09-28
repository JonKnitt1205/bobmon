package bobbythevi.bobmon.bobmon_services.metrics_agent.controller;

import org.springframework.http.HttpMethod;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemHealthControllerConstants {
    public static final String SYSTEM_HEALTH_PATH = "/system_health";
    
    public static enum PATH {
        SYSTEM_HEALTH(SYSTEM_HEALTH_PATH);

        public final String value;

        PATH(String value) {
            this.value = value;
        }

        @Override 
        public String toString() {
            return value;
        }
    }

    public static String LOG_CONTROLLER(PATH path, HttpMethod method) {
        return String.format("Recieved %s request for path %s", method.name(), path.value);
    }
}
