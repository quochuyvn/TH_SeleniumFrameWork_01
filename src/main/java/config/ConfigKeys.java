package config;

public interface ConfigKeys {

    /* Environment & URL */
    String ENV = "env";
    String BASE_URL_DEV = "base.url.dev";
    String BASE_URL_STAGING = "base.url.staging";
    String BASE_URL_PROD = "base.url.prod";
    String LOGIN_URL = "login.url";

    /* Browser & Execution */
    String BROWSER = "browser";
    String OS = "os";
    String RUN_MODE = "run.mode";

    /* Grid */
    String GRID_URL = "grid.url";

    /* Waits */
    String IMPLICIT_WAIT = "implicit.wait";
    String PAGE_LOAD_TIMEOUT = "page.load.timeout";
    String EXPLICIT_WAIT = "explicit.wait";

    /* Highlight */
    String HIGHLIGHT_ENABLED = "highlight.enabled";
    String HIGHLIGHT_BORDER_COLOR = "highlight.border.color";
    String HIGHLIGHT_DELAY = "highlight.delay";
    String HIGHLIGHT_TIMES = "highlight.times";

    /* Retry */
    String RETRY_TIMES = "retry.times";
    String RETRY_DELAY = "retry.delay";
}
