cat > src/main/java/exceptions/InvalidProjectException.java << 'EOF'
package exceptions;

public class InvalidProjectException extends GameException {
    public InvalidProjectException(String message) {
        super("PROJ_001", message, ErrorSeverity.MEDIUM);
    }
}
EOF