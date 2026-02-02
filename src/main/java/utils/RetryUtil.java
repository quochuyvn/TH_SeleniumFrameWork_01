package utils;

import config.ConfigKeys;
import config.ConfigReader;
import org.openqa.selenium.*;

import java.util.function.Supplier;

public class RetryUtil {

    private static final int RETRY_TIMES =
            ConfigReader.getInt(ConfigKeys.RETRY_TIMES, 3);

    private static final long RETRY_DELAY =
            ConfigReader.getInt(ConfigKeys.RETRY_DELAY, 500);

    private RetryUtil() {
    }

    public static <T> T retry(Supplier<T> action, String actionName) {
        RuntimeException lastException = null;

        for (int attempt = 1; attempt <= RETRY_TIMES; attempt++) {
            try {
                return action.get();
            } catch (StaleElementReferenceException |
                     ElementClickInterceptedException |
                     TimeoutException e) {

                lastException = e;
                sleep(RETRY_DELAY);

                if (attempt == RETRY_TIMES) {
                    throw new RuntimeException(
                            actionName + " failed after " + RETRY_TIMES + " retries", e);
                }
            }
        }
        
        // This should never happen as we throw in the loop, but compiler needs this
        if (lastException != null) {
            throw lastException;
        }
        throw new RuntimeException(actionName + " failed with no exception captured");
    }

    public static void retry(Runnable action, String actionName) {
        retry(() -> {
            action.run();
            return null;
        }, actionName);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
