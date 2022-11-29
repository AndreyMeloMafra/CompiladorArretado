package enums;

public enum RegexEnum {
    LETTER_REGEX("[a-z]", 0),
    NUMBER_REGEX("[0-9]", 1),
    GREATTER_THEN_OP_REGEX("[>]", 2),
    LESS_THEN_OP_REGEX("[<]", 3),
    ARITMETIC_OP_REGEX("[+|-|*|/|%]", 4),
    ATTRIBUTION_OP_REGEX("[=]", 5),
    ESPECIAL_CHARACTER_REGEX("[,|;|/)/|/(/|}|{]", 6),
    UNDERSCORE_REGEX("[_]", 7),
    ENDFILE_REGEX("[$]", 8),
    DOT_REGEX("[.]", 9),
    RESERVED_WORDS_REGEX("(int|float|char|while|main|if|else)", 9),
    QUOTES("[']", 10),

    VARIABLES_TYPES("(int|float|char)", 11);

    public String regex;
    public int state;

    RegexEnum(String value, int state) {
        regex = value;
        this.state = state;
    }
}
