package compilador;

import enums.TokenType;

public class Token {
    private TokenType type;
    private String lexema;
    
    public Token(String lexema, TokenType type){
        this.lexema = lexema;
        this.type = type;
    }
    
    public String getLexema(){
        return this.lexema;
    }
    
    public TokenType getType(){
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
