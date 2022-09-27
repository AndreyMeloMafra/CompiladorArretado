package utils;

public class ReconizeUtils {

    private final String LETTER_REGEX = "[a-z]";
    private final String NUMBER_REGEX = "[0-9]";
    private final String RELATIONAL_OP_REGEX = "[>|<|=]";
    private final String ARITMETIC_OP_REGEX = "[+|-|*|/|%]";
    private final String ATTRIBUTION_OP_REGEX = "[=]";
    private final String ESPECIAL_CHARACTER_REGEX = "[,|;|(|)|}|{]";
    private final String UNDERSCORE_REGEX = "[_]";
    private final String ENDFILE_REGEX = "[$]";
    private final String DOT_REGEX = "[.]";
    private final String RESERVED_WORDS_REGEX = "[int|float|char|while|main|if|else]";
    private final String QUOTES = "[']";

    public boolean isLetter(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(LETTER_REGEX);
    }

    public boolean isNumber(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(NUMBER_REGEX);
    }

    public boolean isRelationalOperator(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(RELATIONAL_OP_REGEX);
    }

    public boolean isAritmeticOperator(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(ARITMETIC_OP_REGEX);
    }

    public boolean isAttributionOperator(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(ATTRIBUTION_OP_REGEX);
    }

    public boolean isEspecialCharacter(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(ESPECIAL_CHARACTER_REGEX);
    }

    public boolean isEmptySpace(char value) {
        String charToString = String.valueOf(value);
        return charToString.equals(" ") ||
                charToString.equals("\t") ||
                charToString.equals("\n") ||
                charToString.equals("\r");
    }

    public boolean isUnderscore(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(UNDERSCORE_REGEX);
    }

    public boolean isEndOfFile(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(ENDFILE_REGEX);
    }

    public boolean isDot(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(DOT_REGEX);
    }

    public boolean isReservedWord(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(RESERVED_WORDS_REGEX);
    }

    public boolean isQuotes(char value) {
        String charToString = String.valueOf(value);
        return charToString.matches(QUOTES);
    }
}
