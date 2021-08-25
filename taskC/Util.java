package taskC;

import taskC.operators.*;

public class Util {
    private static Expression subSample = null;
    private static final Variable ZERO = new Variable("0");

    public static boolean isAxiomScheme1(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getRight() instanceof Impl
                && ((Impl) e).getLeft().equals(((Impl) ((Impl) e).getRight()).getRight());
    }

    public static boolean isAxiomScheme2(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getRight() instanceof Impl
                && ((Impl) e).getLeft() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getLeft() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getRight() instanceof Impl
                && ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getRight() instanceof Impl
                && ((Impl) ((Impl) e).getLeft()).getLeft().equals(
                        ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getLeft())
                && ((Impl) ((Impl) e).getLeft()).getLeft().equals(
                        ((Impl) ((Impl) ((Impl) e).getRight()).getRight()).getLeft())
                && ((Impl) ((Impl) e).getLeft()).getRight().equals(
                        ((Impl) ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getRight()).getLeft())
                && ((Impl) ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getRight()).getRight().equals(
                        ((Impl) ((Impl) ((Impl) e).getRight()).getRight()).getRight());
    }

    public static boolean isAxiomScheme3(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getRight() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getRight() instanceof And
                && ((Impl) e).getLeft().equals(((And) ((Impl) ((Impl) e).getRight()).getRight()).getLeft())
                && ((Impl) ((Impl) e).getRight()).getLeft().equals(
                        ((And) ((Impl) ((Impl) e).getRight()).getRight()).getRight());
    }

    public static boolean isAxiomScheme4(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof And
                && (((And) ((Impl) e).getLeft()).getLeft().equals(((Impl) e).getRight()));
    }

    public static boolean isAxiomScheme5(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof And
                && ((And) ((Impl) e).getLeft()).getRight().equals(((Impl) e).getRight());
    }

    public static boolean isAxiomScheme6(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getRight() instanceof Or
                && ((Impl) e).getLeft().equals(((Or) ((Impl) e).getRight()).getRight());
    }

    public static boolean isAxiomScheme7(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getRight() instanceof Or
                && ((Impl) e).getLeft().equals(((Or) ((Impl) e).getRight()).getLeft());
    }

    public static boolean isAxiomScheme8(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof Impl
                && ((Impl) e).getRight() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getLeft() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getRight() instanceof Impl
                && ((Impl) ((Impl) ((Impl) e).getRight()).getRight()).getLeft() instanceof Or
                && ((Impl) ((Impl) e).getLeft()).getLeft().equals(
                        ((Or) ((Impl) ((Impl) ((Impl) e).getRight()).getRight()).getLeft()).getLeft())
                && ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getLeft().equals(
                        ((Or) ((Impl) ((Impl) ((Impl) e).getRight()).getRight()).getLeft()).getRight())
                && ((Impl) ((Impl) e).getLeft()).getRight().equals(
                        ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getRight())
                && ((Impl) ((Impl) e).getLeft()).getRight().equals(
                        ((Impl) ((Impl) ((Impl) e).getRight()).getRight()).getRight());
    }

    public static boolean isAxiomScheme9(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof Impl
                && ((Impl) e).getRight() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getLeft() instanceof Impl
                && ((Impl) ((Impl) e).getRight()).getRight() instanceof Not
                && ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getRight() instanceof Not
                && ((Impl) ((Impl) e).getLeft()).getLeft().equals(
                        ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getLeft())
                && ((Impl) ((Impl) e).getLeft()).getLeft().equals(
                        ((Not) ((Impl) ((Impl) e).getRight()).getRight()).getExpression())
                && ((Impl) ((Impl) e).getLeft()).getRight().equals(
                        ((Not) ((Impl) ((Impl) ((Impl) e).getRight()).getLeft()).getRight()).getExpression());
    }

    public static boolean isAxiomScheme10(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof Not
                && ((Not) ((Impl) e).getLeft()).getExpression() instanceof Not
                && ((Not) ((Not) ((Impl) e).getLeft()).getExpression()).getExpression().equals(((Impl) e).getRight());
    }

    public static Object isAxiomScheme11(Expression e) {
        subSample = null;
        if (!(e instanceof Impl
                && ((Impl) e).getLeft() instanceof ForAll)) {
            return 0;
        }
        return subsCorrectlyInAx(
                        ((ForAll) ((Impl) e).getLeft()).getExpression(),
                ((Impl) e).getRight(),
                ((ForAll) ((Impl) e).getLeft()).getVariable(), true);
    }

    public static Object isAxiomScheme12(Expression e) {
        subSample = null;
        if (!(e instanceof Impl
                && ((Impl) e).getRight() instanceof Exists)) {
            return 0;
        }
        return subsCorrectlyInAx(
                        ((Exists) ((Impl) e).getRight()).getExpression(),
                ((Impl) e).getLeft(),
                ((Exists) ((Impl) e).getRight()).getVariable(), true);
    }

    public static boolean isAxiom1(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof Equal
                && ((Impl) e).getRight() instanceof Equal
                && ((Equal) ((Impl) e).getRight()).getLeft() instanceof Next
                && ((Equal) ((Impl) e).getRight()).getRight() instanceof Next
                && ((Equal) ((Impl) e).getLeft()).getLeft()
                .equals(((Next) ((Equal) ((Impl) e).getRight()).getLeft()).getExpression())
                && ((Equal) ((Impl) e).getLeft()).getRight()
                .equals(((Next) ((Equal) ((Impl) e).getRight()).getRight()).getExpression());
    }

    public static boolean isAxiom2(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getRight() instanceof Impl
                && ((Impl) e).getLeft() instanceof Equal
                && ((Impl) ((Impl) e).getRight()).getLeft() instanceof Equal
                && ((Impl) ((Impl) e).getRight()).getRight() instanceof Equal
                && ((Equal) ((Impl) e).getLeft()).getLeft()
                .equals(((Equal) ((Impl) ((Impl) e).getRight()).getLeft()).getLeft())
                && ((Equal) ((Impl) e).getLeft()).getRight()
                .equals(((Equal) ((Impl) ((Impl) e).getRight()).getRight()).getLeft())
                && ((Equal) ((Impl) ((Impl) e).getRight()).getLeft()).getRight()
                .equals(((Equal) ((Impl) ((Impl) e).getRight()).getRight()).getRight());
    }

    public static boolean isAxiom3(Expression e) {
        return e instanceof Impl
                && ((Impl) e).getLeft() instanceof Equal
                && ((Impl) e).getRight() instanceof Equal
                && ((Equal) ((Impl) e).getLeft()).getLeft() instanceof Next
                && ((Equal) ((Impl) e).getLeft()).getRight() instanceof Next
                && ((Next) ((Equal) ((Impl) e).getLeft()).getLeft()).getExpression()
                .equals(((Equal) ((Impl) e).getRight()).getLeft())
                && ((Next) ((Equal) ((Impl) e).getLeft()).getRight()).getExpression()
                .equals(((Equal) ((Impl) e).getRight()).getRight());
    }

    public static boolean isAxiom4(Expression e) {
        return e instanceof Equal
                && ((Equal) e).getRight().equals(ZERO)
                && ((Equal) e).getLeft() instanceof Not
                && ((Not) ((Equal) e).getLeft()).getExpression() instanceof Next;
    }

    public static boolean isAxiom5(Expression e) {
        return e instanceof Equal
                && ((Equal) e).getLeft() instanceof Add
                && ((Add) ((Equal) e).getLeft()).getRight() instanceof Next
                && ((Equal) e).getRight() instanceof Next
                && ((Next) ((Equal) e).getRight()).getExpression() instanceof Add
                && ((Add) ((Equal) e).getLeft()).getLeft()
                .equals(((Add) ((Next) ((Equal) e).getRight()).getExpression()).getLeft())
                && ((Next) ((Add) ((Equal) e).getLeft()).getRight()).getExpression()
                .equals(((Add) ((Next) ((Equal) e).getRight()).getExpression()).getRight());
    }

    public static boolean isAxiom6(Expression e) {
        return e instanceof Equal
                && ((Equal) e).getLeft() instanceof Add
                && ((Add) ((Equal) e).getLeft()).getRight().equals(ZERO)
                && ((Add) ((Equal) e).getLeft()).getLeft().equals(((Equal) e).getRight());
    }

    public static boolean isAxiom7(Expression e) {
        return e instanceof Equal
                && ((Equal) e).getLeft() instanceof Mul
                && ((Mul) ((Equal) e).getLeft()).getRight().equals(ZERO)
                && ((Equal) e).getRight().equals(ZERO);
    }

    public static boolean isAxiom8(Expression e) {
        return e instanceof Equal
                && ((Equal) e).getLeft() instanceof Mul
                && ((Mul) ((Equal) e).getLeft()).getRight() instanceof Next
                && ((Equal) e).getRight() instanceof Add
                && ((Add) ((Equal) e).getRight()).getLeft() instanceof Mul
                && ((Mul) ((Equal) e).getLeft()).getLeft()
                .equals(((Mul) ((Add) ((Equal) e).getRight()).getLeft()).getLeft())
                && ((Mul) ((Equal) e).getLeft()).getLeft()
                .equals(((Add) ((Equal) e).getRight()).getRight())
                && ((Next) ((Mul) ((Equal) e).getLeft()).getRight()).getExpression()
                .equals(((Mul) ((Add) ((Equal) e).getRight()).getLeft()).getRight());
    }

    public static boolean isAxiomSchemeA9(Expression e) {
        subSample = ZERO;
        boolean subZero = e instanceof Impl
                && ((Impl) e).getLeft() instanceof And
                && ((And) ((Impl) e).getLeft()).getRight() instanceof ForAll
                && ((ForAll) ((And) ((Impl) e).getLeft()).getRight()).getExpression() instanceof Impl
                && ((Impl) e).getRight()
                .equals(((Impl) ((ForAll) ((And) ((Impl) e).getLeft()).getRight()).getExpression()).getLeft())
                && subsCorrectlyInAx(
                        ((Impl) e).getRight(),
                ((And) ((Impl) e).getLeft()).getLeft(),
                ((ForAll) ((And) ((Impl) e).getLeft()).getRight()).getVariable(),
                true)
                .equals(true);
        if (!subZero) return false;
        subSample = new Next(((ForAll) ((And) ((Impl) e).getLeft()).getRight()).getVariable());
        return subsCorrectlyInAx(
                ((Impl) e).getRight(),
                ((Impl) ((ForAll) ((And) ((Impl) e).getLeft()).getRight()).getExpression()).getRight(),
                ((ForAll) ((And) ((Impl) e).getLeft()).getRight()).getVariable(),
                true)
                .equals(true);
    }

    private static Object subsCorrectlyInAx(Expression e, Expression sub, Variable subbedVar, boolean isSubbedVarFree) {
        if (e.equals(subbedVar) && isSubbedVarFree) {
            if (subSample == null) subSample = sub;
            if (!subSample.equals(sub)) {
                return false;
            }
            if (sub.getUsedVars().stream().anyMatch(var -> sub.getBoundVars().contains(var))
                    && sub.getUsedVars().stream().anyMatch(var -> e.getBoundVars().contains(var))) {
                return sub;
            }
            return true;
        }
        if (!e.getClass().equals(sub.getClass())) return false;
        if (e instanceof Variable) {
            return e.equals(sub);
        }
        if (e instanceof AbstractUnaryLogicalExpression) {
            if (e instanceof AbstractQuantifier) {
                if (!((AbstractQuantifier) e).getVariable()
                        .equals(((AbstractQuantifier) sub).getVariable())) {
                    return false;
                };
                return subsCorrectlyInAx(
                        ((AbstractQuantifier) e).getExpression(),
                        ((AbstractQuantifier) sub).getExpression(),
                        subbedVar,
                        !((AbstractQuantifier) e).getVariable().equals(subbedVar) && isSubbedVarFree
                );
            }
            return subsCorrectlyInAx(
                    ((AbstractUnaryLogicalExpression) e).getExpression(),
                    ((AbstractUnaryLogicalExpression) sub).getExpression(),
                    subbedVar,
                    isSubbedVarFree);
        } else {
            AbstractBinaryLogicalExpression binE = (AbstractBinaryLogicalExpression) e;
            AbstractBinaryLogicalExpression binSub = (AbstractBinaryLogicalExpression) sub;
            Object leftRes = subsCorrectlyInAx(
                    binE.getLeft(),
                    binSub.getLeft(),
                    subbedVar,
                    isSubbedVarFree
            );
            Object rightRes = subsCorrectlyInAx(
                    binE.getRight(),
                    binSub.getRight(),
                    subbedVar,
                    isSubbedVarFree
            );
            if (leftRes.equals(false) || rightRes.equals(false)) {
                return false;
            } else if (leftRes instanceof Expression) {
                return leftRes;
            } else if (rightRes instanceof Expression) {
                return rightRes;
            } else {
                return true;
            }
        }
    }
}
