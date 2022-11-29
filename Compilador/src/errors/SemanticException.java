package errors;

public class SemanticException extends RuntimeException {
    public SemanticException(String value) {
        super("Error: variable" + " '" + value + "' " + "already created in this scope ");
    }
}
