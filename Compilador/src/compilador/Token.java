/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import enums.TokenType;

/**
 *
 * @author tarci
 */
public class Token {
    private TokenType type; //type do token
    private String lexema; //conteúdo do token
    
    public Token(String lexema, TokenType type){
        this.lexema = lexema;
        this.type = type;
    }
    
    public String getLexema(){
        return this.lexema;
    }
    
    public TokenType gettype(){
        return this.type;
    }
    
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.lexema);
        stringBuilder.append(" - ");
        stringBuilder.append(this.type.tokenValue);

        return stringBuilder.toString();
    }
    
    
}
