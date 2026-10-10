package bobbythevi.bobmon.bobmon_services.metrics_common.path;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemHealthControllerConstants {
    public static final String SYSTEM_HEALTH_PATH = "/system_health";

    public enum PATH {
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
}
