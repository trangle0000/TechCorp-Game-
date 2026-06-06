package exceptions;

/**
 * Base exception class for all game-related errors.
 */
public class GameException extends Exception {

    /**
     * Creates a game exception with a message.
     * @param message the error message
     */
    public GameException(String message) {
        super(message);
    }

    /**
     * Creates a game exception with a message and cause.
     * @param message the error message
     * @param cause the underlying cause
     */
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }
}