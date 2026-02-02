package utils.exceptions;

/**
 * =====================================================
 * AutomationException
 * =====================================================
 * Base exception for all automation framework errors
 * 
 * Use: Never throw this directly; use specific subclasses
 * Examples:
 *   - ElementInteractionException (click/type failed)
 *   - GridConnectionException (Grid unavailable)
 *   - InvalidConfigException (config missing)
 * =====================================================
 */
public class AutomationException extends RuntimeException {
    
    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}
