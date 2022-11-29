package errors;

public class SyntaticException extends RuntimeException {
    public SyntaticException(String value) {
        super("Error: Expected " + value);
    }
}
