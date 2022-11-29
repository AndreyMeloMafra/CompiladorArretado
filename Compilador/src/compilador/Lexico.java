package compilador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import enums.TokenType;
import enums.RegexEnum;
import errors.BadFormatCharException;
import errors.BadFormatFloatException;
import errors.InvalidTokenException;
import utils.ReconizeUtils;

/**
 * @author tarci
 */

public class Lexico {
    private String conteudo;
    private int indiceConteudo;
    private final ReconizeUtils reconizeUtils = new ReconizeUtils();
    private int state;
    private Token token;

    public Lexico(String path) {
        try {
            this.conteudo = new String(Files.readAllBytes(Paths.get(path)));
            this.indiceConteudo = 0;
            this.state = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private char nextChar() {
        return this.conteudo.charAt(this.indiceConteudo++);
    }

    private boolean hasNextChar() {
        return indiceConteudo < this.conteudo.length();
    }

    private void back() {
        this.indiceConteudo--;
    }

    public List<Token> start() {
        Token t = null;
        List<Token> tokens = new ArrayList<>();
        while ((t = this.nextToken()) != null) {
            tokens.add(t);
            System.out.println(t.toString());
        }
        return tokens;
    }

    public Token nextToken() {
        char character;
        this.state = 0;

        StringBuffer lexema = new StringBuffer();

        while (this.hasNextChar()) {
            character = this.nextChar();
            switch (this.state) {
                case 0:
                    zeroState(character, lexema);
                    break;
                case 1:
                    firstState(character, lexema);
                    break;
                case 2:
                    secondState(character, lexema);
                    break;
                case 3:
                    thirdState(character, lexema);
                    break;
                case 4:
                    forthState(character, lexema);
                    break;
                case 5:
                    fifthState(character, lexema);
                    break;
                case 6:
                    sixthState(lexema);
                    break;
                case 7:
                    seventhState(character, lexema);
                    break;
                case 8:
                    eighthState(character, lexema);
                    break;
                case 9:
                    ninethState(lexema);
                    break;
                case 10:
                    tenthState(character, lexema);
                    break;
                case 11:
                    eleventhState(lexema);
                    break;
                case 12:
                    twelfthState(lexema);
                    break;
                case 13:
                    thirteenth(character, lexema);
                    break;
                case 14:
                    fourteenth(lexema);
                    break;
                default:
                    this.back();
                    return this.token;
            }
        }
        return null;
    }

    private void zeroState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isEmptySpace(character)) {
            this.state = 0;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.NUMBER_REGEX)) {
            lexema.append(character);
            this.state = 1;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.QUOTES)) {
            lexema.append(character);
            this.state = 4;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.LETTER_REGEX)) {
            lexema.append(character);
            this.state = 7;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.LESS_THEN_OP_REGEX)) {
            lexema.append(character);
            this.state = 8;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.ARITMETIC_OP_REGEX)) {
            lexema.append(character);
            this.state = 9;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.ATTRIBUTION_OP_REGEX)) {
            lexema.append(character);
            this.state = 10;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.ESPECIAL_CHARACTER_REGEX)) {
            lexema.append(character);
            this.state = 12;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.GREATTER_THEN_OP_REGEX)) {
            lexema.append(character);
            this.state = 13;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.ENDFILE_REGEX)) {
            lexema.append(character);
            this.state = 99;
        } else {
            lexema.append(character);
            throw new InvalidTokenException(lexema.toString());
        }

    }

    private void firstState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.DOT_REGEX)) {
            lexema.append(character);
            this.state = 2;
        } else if (this.reconizeUtils.charMatch(character, RegexEnum.NUMBER_REGEX)) {
            lexema.append(character);
            this.state = 1;
        } else {
            this.back();
            this.state = 99;
            this.token = new Token(lexema.toString(), TokenType.INTEGER);
        }
    }

    private void secondState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.NUMBER_REGEX)) {
            lexema.append(character);
            this.state = 3;
        } else {
            lexema.append(character);
            throw new BadFormatFloatException(lexema.toString());
        }
    }

    private void thirdState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.NUMBER_REGEX)) {
            lexema.append(character);
            this.state = 3;
        } else {
            this.back();
            this.state = 99;
            this.token = new Token(lexema.toString(), TokenType.FLOAT);
        }
    }

    private void forthState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.LETTER_REGEX) || this.reconizeUtils.charMatch(character, RegexEnum.NUMBER_REGEX)) {
            lexema.append(character);
            this.state = 5;
        } else {
            lexema.append(character);
            throw new BadFormatCharException(lexema.toString());
        }
    }

    private void fifthState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.QUOTES)) {
            lexema.append(character);
            this.state = 6;
        } else {
            lexema.append(character);
            throw new BadFormatCharException(lexema.toString());
        }
    }

    private void sixthState(StringBuffer lexema) {
        this.back();
        this.state = 99;
        this.token = new Token(lexema.toString(), TokenType.CHAR);
    }

    private void seventhState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.LETTER_REGEX) || this.reconizeUtils.charMatch(character, RegexEnum.NUMBER_REGEX)) {
            lexema.append(character);
        } else if (this.reconizeUtils.stringMatch(lexema.toString(), RegexEnum.RESERVED_WORDS_REGEX)) {
            this.state = 11;
        } else {
            this.back();
            this.state = 99;
            this.token = new Token(lexema.toString(), TokenType.IDENTIFIER);
        }
    }

    private void eighthState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.ATTRIBUTION_OP_REGEX) || this.reconizeUtils.charMatch(character, RegexEnum.GREATTER_THEN_OP_REGEX)) {
            lexema.append(character);
            this.state = 14;
        } else {
            this.back();
            this.state = 99;
            this.token = new Token(lexema.toString(), TokenType.RELATIONAL_OPERATOR);
        }
    }

    private void ninethState(StringBuffer lexema) {
        this.back();
        this.state = 99;
        this.token = new Token(lexema.toString(), TokenType.ARITHMETIC_OPERATOR);
    }

    private void tenthState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.ATTRIBUTION_OP_REGEX)) {
            lexema.append(character);
            this.state = 14;
        } else {
            this.back();
            this.state = 99;
            this.token = new Token(lexema.toString(), TokenType.ATTRIBUTION_OPERATOR);
        }
    }

    private void eleventhState(StringBuffer lexema) {
        this.back();
        this.state = 99;
        this.token = new Token(lexema.toString(), TokenType.RESERVED_WORD);
    }

    private void twelfthState(StringBuffer lexema) {
        this.back();
        this.state = 99;
        this.token = new Token(lexema.toString(), TokenType.ESPECIAL_CHARACTER);
    }

    private void thirteenth(char character, StringBuffer lexema) {
        if (this.reconizeUtils.charMatch(character, RegexEnum.ATTRIBUTION_OP_REGEX)) {
            lexema.append(character);
            this.state = 14;
        } else {
            this.back();
            this.state = 99;
            this.token = new Token(lexema.toString(), TokenType.RELATIONAL_OPERATOR);
        }
    }

    private void fourteenth(StringBuffer lexema) {
        this.back();
        this.state = 99;
        this.token = new Token(lexema.toString(), TokenType.RELATIONAL_OPERATOR);
    }
     /* 
 Estados:

    0 - inicial
    1 - TIPO_INTEIRO (FINAL)
    2 - ERROR_BAD_FORMAT_FLOAT
    3 - FLOAT (FINAL)
    4 - ERROR_BAD_FORMAT_CHAR
    5 - ERROR_BAD_FORMAT_CHAR
    6 - CHAR (FINAL)
    7 - IDENTIFICADOR (FINAL)
    8 - RELATIONAL_OPERATOR (FINAL) 
    9 - ARITMETIC_OPERATOR (FINAL)
    10 - ATTRIBUTION_OPERATOR (FINAL)
    11 - RESERVED_WORDS (FINAL)
    12 - ESPECIAL CHARACTER
*/
}
