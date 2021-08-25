package operators;

import java.util.Objects;

public abstract class AbstractBinaryLogicalExpression implements Expression {
    private final Expression left;
    private final Expression right;

    protected AbstractBinaryLogicalExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int getPriority();
    protected abstract String getSymbol();
    protected abstract boolean isRightAssociative();

    public String toString() {
        return "(" + left + getSymbol() + right + ")";
    }

    public String toPrefixString() {
        return "(" + getSymbol() + "," + left.toPrefixString() + "," + right.toPrefixString() + ")";
    }

    private String getMiniString(Expression expression, boolean bracketed) {
        return (bracketed ? "(" : "") + expression.toMiniString() + (bracketed ? ")" : "");
    }

    private boolean needsPriorityBrackets(Expression expression) {
        return expression instanceof AbstractBinaryLogicalExpression
                && ((AbstractBinaryLogicalExpression) expression).getPriority()
                < this.getPriority();
    }

    private boolean needsAssociativeBrackets(Expression expression) {
        return expression instanceof AbstractBinaryLogicalExpression
                && ((AbstractBinaryLogicalExpression) expression).isRightAssociative();
    }

    public String toMiniString() {
        return getMiniString(left, needsPriorityBrackets(left) || needsAssociativeBrackets(left))
                + getSymbol()
                + getMiniString(right, needsPriorityBrackets(right));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBinaryLogicalExpression that = (AbstractBinaryLogicalExpression) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}
