import operators.Expression;

import java.util.Map;
import java.util.stream.IntStream;

public class Vertex {
    private final Map<Expression, Integer> hypothesis;
    private Expression expression;
    private String symbol = null;

    public Vertex(Map<Expression, Integer> hypothesis, Expression expression, String symbol) {
        this.hypothesis = Map.copyOf(hypothesis);
        this.expression = expression;
        this.symbol = symbol;
    }

    public Vertex(Vertex other) {
        hypothesis = Map.copyOf(other.hypothesis);
        expression = other.expression;
        symbol = other.symbol;
    }

    public Expression getExpression() {
        return expression;
    }

    public Map<Expression, Integer> getHypothesis() {
        return hypothesis;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        hypothesis.forEach((e, cnt)
                -> IntStream.range(0, cnt).forEach(i -> builder.append(e.toMiniString()).append(',')));
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        builder.append("|-").append(expression.toMiniString()).append(" ").append(symbol);
        return builder.toString();
    }
}
