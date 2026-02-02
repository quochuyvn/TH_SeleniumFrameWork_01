package utils.exceptions;

/**
 * =====================================================
 * GridConnectionException
 * =====================================================
 * Thrown when connection to Selenium Grid fails
 * 
 * Likely causes:
 *   - Grid URL invalid (MalformedURLException)
 *   - Grid server not running (connection refused)
 *   - Network timeout
 *   - Authentication failed
 * 
 * Usage:
 *   try {
 *       RemoteWebDriver driver = new RemoteWebDriver(gridUrl, options);
 *   } catch (Exception e) {
 *       throw new GridConnectionException(
 *           "Cannot connect to Selenium Grid at " + gridUrl, e);
 *   }
 * =====================================================
 */
public class GridConnectionException extends AutomationException {
    
    public GridConnectionException(String message) {
        super(message);
    }

    public GridConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
