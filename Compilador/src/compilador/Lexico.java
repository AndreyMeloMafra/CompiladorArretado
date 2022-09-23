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
        this.state = 0;

        StringBuffer lexema = new StringBuffer();
        while (this.hasNextChar()) {
            character = this.nextChar();
            switch (this.state) {
                case 0:
                    if (this.reconizeUtils.isEmptySpace(character)) {
                        this.state = 0;
                    } else if (this.reconizeUtils.isLetter(character) || this.reconizeUtils.isUnderscore(character)) {
                        lexema.append(character);
                        this.state = 1;
                    } else if (this.reconizeUtils.isNumber(character)) {
                        lexema.append(character);
                        this.state = 2;
                    } else if (this.reconizeUtils.isEspecialCharacter(character)) {
                        lexema.append(character);
                        this.state = 5;
                    } else if (this.reconizeUtils.isEndOfFile(character)) {
                        lexema.append(character);
                        this.state = 99;
                        this.back();
                    } else {
                        lexema.append(character);
                        throw new RuntimeException("Erro: token inválido \"" + lexema.toString() + "\"");
                    }
                    break;
                case 1:
                    if (this.reconizeUtils.isLetter(character) || this.reconizeUtils.isNumber(character)
                            || this.reconizeUtils.isUnderscore(character)) {
                        lexema.append(character);
                        this.state = 1;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_IDENTIFICADOR);
                    }
                    break;
                case 2:
                    if (this.reconizeUtils.isNumber(character)) {
                        lexema.append(character);
                        this.state = 2;
                    } else if (this.reconizeUtils.isDot(character)) {
                        lexema.append(character);
                        this.state = 3;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_INTEIRO);
                    }
                    break;
                case 3:
                    if (this.reconizeUtils.isNumber(character)) {
                        lexema.append(character);
                        this.state = 4;
                    } else {
                        throw new RuntimeException("Erro: número float inválido \"" + lexema.toString() + "\"");
                    }
                    break;
                case 4:
                    if (this.reconizeUtils.isNumber(character)) {
                        lexema.append(character);
                        this.state = 4;
                    } else {
                        this.back();
                        return new Token(lexema.toString(), Token.TIPO_REAL);
                    }
                    break;
                case 5:
                    this.back();
                    return new Token(lexema.toString(), Token.TIPO_CARACTER_ESPECIAL);
                case 99:
                    return new Token(lexema.toString(), Token.TIPO_FIM_CODIGO);
            }
        }
        return token;
    }
}
