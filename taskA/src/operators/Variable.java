package operators;

import java.util.Objects;

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public String toPrefixString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
