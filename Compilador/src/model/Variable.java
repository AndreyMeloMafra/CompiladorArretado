package model;

public class Variable {
    private String type;
    private String name;
    private int scope;

    public Variable(String type, String name, int scope) {
        this.type = type;
        this.name = name;
        this.scope = scope;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getScope() {
        return this.scope;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("type: ");
        sb.append(this.getType());
        sb.append(", Name: ");
        sb.append(this.getName());
        sb.append(", Scope: ");
        sb.append(this.getScope());

        return sb.toString();
    }
}
