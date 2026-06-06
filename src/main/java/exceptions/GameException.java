package exceptions;

public class GameException extends Exception {

    public enum ErrorSeverity { LOW, MEDIUM, HIGH, CRITICAL }

    private String errorCode;
    private ErrorSeverity severity;

    public GameException(String message) {
        super(message);
        this.errorCode = "GAME_000";
        this.severity = ErrorSeverity.MEDIUM;
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "GAME_000";
        this.severity = ErrorSeverity.MEDIUM;
    }

    public GameException(String errorCode, String message, ErrorSeverity severity) {
        super(message);
        this.errorCode = errorCode;
        this.severity = severity;
    }

    public String getErrorCode() { return errorCode; }
    public ErrorSeverity getSeverity() { return severity; }
}
