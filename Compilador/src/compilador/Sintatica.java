package compilador;

import enums.RegexEnum;
import enums.TokenType;
import errors.SyntaticException;
import model.Variable;
import utils.ReconizeUtils;

import java.util.ArrayList;
import java.util.List;

public class Sintatica {
    private Token token;
    private Lexico lexico;
    private int scope;

    private Semantica semantica;

    private final ReconizeUtils reconizeUtils = new ReconizeUtils();

    public Sintatica(String path) {
        this.lexico = new Lexico(path);
        this.semantica = new Semantica();
    }

    public void start() {
        programa();
        showInfos();
    }
    private void showInfos() {
        System.out.println("Variables created: " + this.semantica.getVariables().size());

        for (Variable variable : this.semantica.getVariables()) {
            System.out.println(variable.toString());
        }
    }

    public void icrScope() {
        this.scope++;
    }

    public void dcrScope() {
        this.scope--;
    }

    private void programa() {
        this.token = this.lexico.nextToken();

        if (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.token.getLexema().equals("int")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("INT");
        }

        if (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.token.getLexema().equals("main")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("MAIN");
        }

        if (this.token != null && this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("(")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("(");
        }

        if (this.token != null && this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals(")")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException(")");
        }

        if (this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("{")) {
            icrScope();
            this.bloco();
        } else {
            throw new SyntaticException("{");
        }
    }

    private void bloco() {
        this.token = this.lexico.nextToken();

        while (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.reconizeUtils
                .stringMatch(this.token.getLexema(), RegexEnum.VARIABLES_TYPES)) {
            this.declVar();
        }

        while (this.token != null && this.token.getType() == TokenType.RESERVED_WORD ||
                this.token.getType() == TokenType.IDENTIFIER ||
                this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("{")) {
            this.comando();
        }


        if (this.token != null && this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("}")) {
            dcrScope();
            return;
        } else {
            throw new SyntaticException("}");
        }
    }

    private void declVar() {
        String variableName = "";
        String variableType = "";

        if (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.reconizeUtils
                .stringMatch(this.token.getLexema(), RegexEnum.VARIABLES_TYPES)) {
            variableType = this.token.getLexema();
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("valid type");
        }

        if (this.token != null && this.token.getType() == TokenType.IDENTIFIER) {
            variableName = this.token.getLexema();
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("variable name");
        }

        if (this.token != null && this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals(";")) {
            this.token = this.lexico.nextToken();
            semantica.add(new Variable(variableType, variableName, this.scope));
        } else {
            throw new SyntaticException(";");
        }
    }

    private void comando() {
        if (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.token.getLexema().equals("while")) {
            this.iteracao();
        }

        if (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.token.getLexema().equals("if")) {
            this.condicao();
        }

        if (this.token != null &&
                this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("{") ||
                this.token.getType() == TokenType.IDENTIFIER) {
            this.comandoBasico();
        }
    }

    private void comandoBasico() {
        if (this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("{")) {
            icrScope();
            this.bloco();
        }

        if (this.token.getType() == TokenType.IDENTIFIER) {
            this.atribuicao();
        }
    }

    private void atribuicao() {
        this.token = this.lexico.nextToken();

        if (this.token.getType() == TokenType.ATTRIBUTION_OPERATOR) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("ATTRIBUTION_OPERATOR");
        }

        if (this.token != null && this.isValidToArithmeticExpression()) {
            this.expressaoAritimetica();
        } else {
            throw new SyntaticException("IDENTIFIER");
        }

        this.endLine();
    }

    private void expressaoAritimetica() {
        this.token = this.lexico.nextToken();

        while (this.token != null && this.token.getType() == TokenType.ARITHMETIC_OPERATOR) {
            if (this.token != null && this.token.getType() == TokenType.ARITHMETIC_OPERATOR) {
                this.token = this.lexico.nextToken();

                if (!this.semantica.variableExists(this.token)) {
                    throw new RuntimeException("Variable not found");
                }
            } else {
                throw new SyntaticException("ARITHMETIC_OPERATOR");
            }

            if (this.token != null && this.isValidToArithmeticExpression()) {
                this.token = this.lexico.nextToken();
            } else {
                throw new SyntaticException("IDENTIFIER");
            }
        }
    }

    private boolean isValidToArithmeticExpression() {
        return this.token.getType() == TokenType.IDENTIFIER ||
                this.token.getType() == TokenType.INTEGER ||
                this.token.getType() == TokenType.FLOAT;
    }

    private void iteracao() {
        this.token = this.lexico.nextToken();

        if(!(this.token != null &&  this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("("))) {
            throw new SyntaticException("(");
        }

        this.expressaoRelacional();

        if(this.token != null &&  this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals(")")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException(")");
        }

        this.comando();
    }

    private void expressaoRelacional() {
        this.token = this.lexico.nextToken();
        this.expressaoAritimetica();

        if (this.token != null && this.token.getType() == TokenType.RELATIONAL_OPERATOR) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException("RELATIONAL_OPERATOR");
        }

        this.expressaoAritimetica();
    }

    private void endLine() {
        if (this.token != null && this.token.getLexema().equals(";")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException(";");
        }
    }

    private void condicao() {
        this.token = this.lexico.nextToken();

        if(!(this.token != null &&  this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals("("))) {
            throw new SyntaticException("(");
        }

        this.expressaoRelacional();

        if(this.token != null &&  this.token.getType() == TokenType.ESPECIAL_CHARACTER && this.token.getLexema().equals(")")) {
            this.token = this.lexico.nextToken();
        } else {
            throw new SyntaticException(")");
        }

        this.comando();
        this.token = this.lexico.nextToken();

        if (this.token != null && this.token.getType() == TokenType.RESERVED_WORD && this.token.getLexema().equals("else")) {
            this.token = this.lexico.nextToken();
            this.comando();
        }
    }
}
