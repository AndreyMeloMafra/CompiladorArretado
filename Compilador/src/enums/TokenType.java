package enums;

public enum TokenType {
    INTEGER("Inteiro"),
    FLOAT("Real"),
    CHAR("Char"),
    IDENTIFIER("Identificador"),
    RELATIONAL_OPERATOR("Operador Relacional"),
    ARITHMETIC_OPERATOR("Operador Aritmético"),
    ATTRIBUTION_OPERATOR("Operador de Atribuição"),
    ESPECIAL_CHARACTER("Caracter Especial"),
    RESERVED_WORD("Palavra Reservada"),
    CODE_END("Fim de Código");


    public String tokenValue;

    TokenType(String value) {
        tokenValue = value;
    }
}

