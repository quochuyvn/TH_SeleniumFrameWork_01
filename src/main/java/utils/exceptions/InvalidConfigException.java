package utils.exceptions;

/**
 * =====================================================
 * InvalidConfigException
 * =====================================================
 * Thrown when configuration is missing or invalid
 * 
 * Examples:
 *   - Required config key not found in config.properties
 *   - Config value is null or empty
 *   - Config value is in invalid format (e.g., timeout = "not-a-number")
 *   - Required property not set in environment variables
 * 
 * Usage:
 *   String baseUrl = ConfigReader.getRequired("base.url");
 *   if (baseUrl == null || baseUrl.isEmpty()) {
 *       throw new InvalidConfigException(
 *           "Required config 'base.url' not found in config.properties");
 *   }
 * =====================================================
 */
public class InvalidConfigException extends AutomationException {
    
    public InvalidConfigException(String message) {
        super(message);
    }

    public InvalidConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
