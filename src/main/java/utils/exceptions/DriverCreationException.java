package utils.exceptions;

/**
 * =====================================================
 * DriverCreationException
 * =====================================================
 * Thrown when WebDriver creation fails (local mode)
 * 
 * Examples:
 *   - ChromeDriver binary not found
 *   - Driver options invalid
 *   - Incompatible browser version
 * 
 * Usage:
 *   try {
 *       return new ChromeDriver(options);
 *   } catch (Exception e) {
 *       throw new DriverCreationException(
 *           "Failed to create ChromeDriver", e);
 *   }
 * =====================================================
 */
public class DriverCreationException extends AutomationException {
    
    public DriverCreationException(String message) {
        super(message);
    }

    public DriverCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
