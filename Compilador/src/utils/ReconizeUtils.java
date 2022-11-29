package utils;

import enums.RegexEnum;

public class ReconizeUtils {

    public boolean charMatch(char value, RegexEnum regexEnum) {
        String charToString = String.valueOf(value);
        return charToString.matches(regexEnum.regex);
    }

    public boolean isEmptySpace(char value) {
        String charToString = String.valueOf(value);
        return charToString.equals(" ") ||
                charToString.equals("\t") ||
                charToString.equals("\n") ||
                charToString.equals("\r");
    }

    public boolean stringMatch(String value, RegexEnum regexEnum) {
        return value.matches(regexEnum.regex);
    }
}
