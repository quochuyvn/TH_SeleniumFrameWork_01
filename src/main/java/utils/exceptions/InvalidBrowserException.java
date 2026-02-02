package utils.exceptions;

/**
 * =====================================================
 * InvalidBrowserException
 * =====================================================
 * Thrown when browser parameter is null, empty, or unsupported
 * 
 * Examples:
 *   - Browser = "chrome " (whitespace)
 *   - Browser = null (not provided)
 *   - Browser = "safari" (unsupported on this framework)
 *   - Browser = "" (empty string)
 * 
 * Usage:
 *   String browser = validateBrowser(browserParam);
 *   BrowserType type = BrowserType.fromString(browser);
 *   // throws InvalidBrowserException if invalid
 * =====================================================
 */
public class InvalidBrowserException extends AutomationException {
    
    public InvalidBrowserException(String message) {
        super(message);
    }

    public InvalidBrowserException(String message, Throwable cause) {
        super(message, cause);
    }
}
