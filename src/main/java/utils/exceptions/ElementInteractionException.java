package utils.exceptions;

/**
 * =====================================================
 * ElementInteractionException
 * =====================================================
 * Thrown when element interaction fails
 * 
 * Examples of when to throw:
 *   - Element not clickable after timeout
 *   - Cannot type into element
 *   - Cannot clear input field
 *   - Element stale (DOM changed)
 *   - Cannot submit form
 * 
 * Usage:
 *   throw new ElementInteractionException(
 *       "Failed to click button 'Submit' - element not clickable", 
 *       originalException
 *   );
 * =====================================================
 */
public class ElementInteractionException extends AutomationException {
    
    public ElementInteractionException(String message) {
        super(message);
    }

    public ElementInteractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
