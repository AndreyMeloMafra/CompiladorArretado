package errors;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String value) {
        super("Erro: token mal formatado " + value);
    }
}
