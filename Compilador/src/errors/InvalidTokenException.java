package errors;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String value) {
        super("Erro: float mal formatado " + value);
    }
}
