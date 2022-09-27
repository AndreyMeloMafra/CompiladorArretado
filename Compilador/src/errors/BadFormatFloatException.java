package errors;

public class BadFormatFloatException extends RuntimeException {
    public BadFormatFloatException(String value) {
        super("Erro: float mal formatado " + value);
    }
}
