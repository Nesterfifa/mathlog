package taskC.parser;

import taskC.operators.Variable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("x"));
        variables.add(new Variable("x"));
        System.out.println(Arrays.toString(variables.toArray(new Variable[2])));
    }
}
