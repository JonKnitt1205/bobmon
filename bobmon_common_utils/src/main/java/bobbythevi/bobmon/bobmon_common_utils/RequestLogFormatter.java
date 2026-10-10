package bobbythevi.bobmon.bobmon_common_utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestLogFormatter {
    public static String format(String method, String path) {
        return String.format("Received %s request for path %s", method, path);
    }
}
