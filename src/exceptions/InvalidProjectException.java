package exceptions;

/**
 * Thrown when a project has an invalid state or operation.
 */
public class InvalidProjectException extends GameException {

    /**
     * Creates an exception with a message.
     * @param message the error message
     */
    public InvalidProjectException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and cause.
     * @param message the error message
     * @param cause the underlying cause
     */
    public InvalidProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}