/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        int estado = 0;

        StringBuffer lexema = new StringBuffer();
        while (this.hasNextChar()) {
            character = this.nextChar();
            switch (estado) {
                case 0:
                    token = runZeroState(character, lexema);
                    break;
                case 1:
                    token = runFirstState(character, lexema);
                    break;
                case 2:
                    token = runSecondState(character, lexema);
                    break;
                case 3:
                    token = runThirdState(character, lexema);
                    break;
                case 4:
                    token = runForthState(character, lexema);
                    break;
                case 5:
                    token = runFifthState(character, lexema);
                    break;
                case 99:
                    token = runNineNineState(character, lexema);
                    break;
            }
        }
        return token;
    }

    private Token runZeroState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isEmptySpace(character)) {
            this.state = 0;
            return null;
        } else if (this.reconizeUtils.isLetter(character) ||
                this.reconizeUtils.isUnderscore(character)) {
            lexema.append(character);
            this.state = 1;
            return null;
        } else if (this.reconizeUtils.isNumber(character)) {
            lexema.append(character);
            this.state = 2;
            return null;
        } else if (this.reconizeUtils.isEspecialCharacter(character)) {
            lexema.append(character);
            this.state = 5;
            return null;
        } else if (this.reconizeUtils.isEndOfFile(character)) {
            lexema.append(character);
            this.back();
            this.state = 99;
            return null;
        } else {
            lexema.append(character);
            throw new RuntimeException("Erro: token inválido \"" + lexema.toString() + "\"");
        }
    }

    private Token runFirstState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isLetter(character) ||
                this.reconizeUtils.isNumber(character) ||
                this.reconizeUtils.isUnderscore(character)) {
            lexema.append(character);
            this.state = 1;
            return null;
        } else {
            this.back();
            return new Token(lexema.toString(), Token.TIPO_IDENTIFICADOR);
        }
    }

    private Token runSecondState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isNumber(character)) {
            lexema.append(character);
            this.state = 2;
            return null;
        } else if (this.reconizeUtils.isDot(character)) {
            lexema.append(character);
            this.state = 3;
            return null;
        } else {
            this.back();
            return new Token(lexema.toString(), Token.TIPO_INTEIRO);
        }
    }

    private Token runThirdState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isNumber(character)) {
            lexema.append(character);
            this.state = 4;
            return null;
        } else {
            throw new RuntimeException("Erro: número float inválido \"" + lexema.toString() + "\"");
        }
    }

    private Token runForthState(char character, StringBuffer lexema) {
        if (this.reconizeUtils.isNumber(character)) {
            lexema.append(character);
            this.state = 4;
            return null;
        } else {
            this.back();
            return new Token(lexema.toString(), Token.TIPO_REAL);
        }
    }

    private Token runFifthState(char character, StringBuffer lexema) {
        this.back();
        return new Token(lexema.toString(), Token.TIPO_CARACTER_ESPECIAL);
    }

    private Token runNineNineState(char character, StringBuffer lexema) {
        return new Token(lexema.toString(), Token.TIPO_FIM_CODIGO);
    }

}
