package exceptions;

public class InvalidProjectException extends GameException {
    public InvalidProjectException(String message) {
        super("PROJ_001", message, GameException.ErrorSeverity.MEDIUM);
    }
}
