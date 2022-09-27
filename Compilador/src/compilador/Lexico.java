/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import errors.BadFormatCharException;
import errors.BadFormatFloatException;
import utils.ReconizeUtils;

/**
 *
 * @author tarci
 */

public class Lexico {
    private char[] conteudo;
    private int indiceConteudo;
    private final ReconizeUtils reconizeUtils = new ReconizeUtils();
    private int state;
    private Token token;
    private int type_token;

    public Lexico(String caminhoCodigoFonte) {
        try {
            String conteudoStr;
            conteudoStr = new String(Files.readAllBytes(Paths.get(caminhoCodigoFonte)));
            this.conteudo = conteudoStr.toCharArray();
            this.indiceConteudo = 0;
            this.state = 0;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Retorna próximo char
    private char nextChar() {
        return this.conteudo[this.indiceConteudo++];
    }

    // Verifica existe próximo char ou chegou ao final do código fonte
    private boolean hasNextChar() {
        return indiceConteudo < this.conteudo.length;
    }

    // Retrocede o índice que aponta para o "char da vez" em uma unidade
    private void back() {
        this.indiceConteudo--;
    }

    // Método retorna próximo token válido ou retorna mensagem de erro.
    public Token nextToken() {
        Token token = null;
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
                default: 
                    return this.token;
            }
        }
        return token;
    }

    private void zeroState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isNumber(character)) {
            lexema.append(character);
            this.state = 1;
        } else if (this.reconizeUtils.isQuotes(character)) {
            lexema.append(character);
            this.state = 4;
        } else if (this.reconizeUtils.isLetter(character)) {
            lexema.append(character);
            this.state = 7;
        } else if (this.reconizeUtils.isRelationalOperator(character)) {
            lexema.append(character);
            this.state = 8;
        } else if (this.reconizeUtils.isAritmeticOperator(character)) {
            lexema.append(character);
            this.state = 9;
        } else if (this.reconizeUtils.isAttributionOperator(character)) {
            lexema.append(character);
            this.state = 10;
        }
    }

    private void firstState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isDot(character)) {
            lexema.append(character);
            this.state = 2;
        } else if (this.reconizeUtils.isNumber(character)) {
            lexema.append(character);
            this.state = 1;
        } else if (this.reconizeUtils.isLetter(character)) {
            lexema.append(character);
            throw new BadFormatFloatException(lexema.toString());
        } else {
            this.type_token = Token.TIPO_INTEIRO;
            this.token = new Token(lexema.toString(), this.type_token);
        }
    } 

    private void secondState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isNumber(character)){
            lexema.append(character);
            this.state = 3;
        } else {
            lexema.append(character);
            throw new BadFormatFloatException(lexema.toString());
        }
    }

    private void thirdState(char character, StringBuffer lexema) {
        lexema.append(character);
        this.type_token = Token.TIPO_REAL;
        this.token = new Token(lexema.toString(), this.type_token);
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
*/
}
