package errors;

public class BadFormatCharException extends RuntimeException {
    public BadFormatCharException(String value) {
        super("Erro: char mal formatado " + value);
    }
}
