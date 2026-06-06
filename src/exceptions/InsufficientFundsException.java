package exceptions;

/**
 * Thrown when a company doesn't have enough cash for an operation.
 */
public class InsufficientFundsException extends GameException {

    /**
     * Creates an exception with a message.
     * @param message the error message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }

    /**
     * Creates an exception with a message and cause.
     * @param message the error message
     * @param cause the underlying cause
     */
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}