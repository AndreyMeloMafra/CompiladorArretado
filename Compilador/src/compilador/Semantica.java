package compilador;

import errors.SemanticException;
import model.Variable;

import java.util.ArrayList;
import java.util.List;

public class Semantica {

    private List<Variable> variables;

    public Semantica() {
        this.variables = new ArrayList<>();
    }

    public void add(Variable variable) {
        if(!variableExistsInScope(variable)) {
            variables.add(variable);
        } else {
            throw new SemanticException(variable.getName());
        }
    }

    public boolean variableExistsInScope(Variable variable) {
        return this.variables.stream().filter(var -> var.getName().equals(variable.getName()) && var.getScope() == variable.getScope()).count() > 0;
    }

    public boolean variableExists(Token token) {
        return this.variables.stream().filter(variable -> token.getLexema().equals(variable.getName())).count() > 0;
    }

    public List<Variable> getVariables() {
        return variables;
    }
}
