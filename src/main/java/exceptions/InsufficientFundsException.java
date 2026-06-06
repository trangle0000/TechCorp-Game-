package exceptions;

public class InsufficientFundsException extends GameException {
    private double required;
    private double available;

    public InsufficientFundsException(String action, double required, double available) {
        super("FUNDS_001", String.format("Insufficient funds for %s. Required: $%.0f, Available: $%.0f",
                action, required, available), ErrorSeverity.HIGH);
        this.required = required;
        this.available = available;
    }

    public double getRequired() { return required; }
    public double getAvailable() { return available; }
    public double getShortfall() { return required - available; }
}
