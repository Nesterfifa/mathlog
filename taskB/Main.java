package taskB;

import taskB.operators.*;
import taskB.parser.LogicalParser;
import taskB.parser.StringSource;

import java.util.*;

public class Main {
    public final static Variable FALSE = new Variable("_|_");
    private final static Map<Expression, Integer> hypothesis = new LinkedHashMap<>();
    private final static Set<Expression> proven = new HashSet<>();
    private final static Map<Expression, Set<Expression>> provenImplRightToLeft = new HashMap<>();
    private final static Map<Expression, Expression> modusPonensRightToLeft = new HashMap<>();
    private final static List<List<Vertex>> naturalProofTree
            = new ArrayList<>(List.of(new ArrayList<>()));

    private static boolean isAxiom(Expression expression) {
        return Util.isAxiomScheme(expression);
    }

    private static boolean isHypothesis(Expression expression) {
        return hypothesis.containsKey(expression);
    }

    private static Expression findModusPonens(Expression expression) {
        if (provenImplRightToLeft.containsKey(expression)) {
            for (Expression e : provenImplRightToLeft.get(expression)) {
                if (proven.contains(e)) {
                    return e;
                }
            }
        }
        return null;
    }

    private static List<Vertex> calcGetTreeLevel(int level) {
        while (level >= naturalProofTree.size()) {
            naturalProofTree.add(new ArrayList<>());
        }
        return naturalProofTree.get(level);
    }

    private static void deduct(Vertex start, int level) {
        start.setSymbol("[I->]");

        Vertex cur = new Vertex(start);
        Map<Expression, Integer> deductedHypo = new HashMap<>(cur.getHypothesis());
        while (cur.getExpression() instanceof Impl) {
            Expression expression = cur.getExpression();
            deductedHypo.putIfAbsent(((Impl) expression).getLeft(), 0);
            deductedHypo.computeIfPresent(((Impl) expression).getLeft(), (k, v) -> v + 1);
            Expression right = ((Impl) expression).getRight();
            cur = new Vertex(deductedHypo, right, "[I->]");
            calcGetTreeLevel(++level).add(cur);
        }

        if (cur.getHypothesis().containsKey(cur.getExpression())) {
            cur.setSymbol("[Ax]");
        }
    }

    private static void addSimpleNaturalMP(int level, Expression left, Expression right, Map<Expression, Integer> hypo) {
        calcGetTreeLevel(level).add(new Vertex(hypo, right, "[E->]"));
        calcGetTreeLevel(level + 1).add(new Vertex(hypo, left, "[Ax]"));
        calcGetTreeLevel(level + 1).add(new Vertex(hypo, new Impl(left, right), "[Ax]"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] header = scanner.nextLine().split("[|]-");
        if (!header[0].isEmpty()) {
            String[] hypothesisStrings = header[0].split(",");
            for (String e : hypothesisStrings) {
                Expression h = new LogicalParser('\0', new StringSource(e)).parse();
                hypothesis.putIfAbsent(h, 0);
                hypothesis.computeIfPresent(h, (k, v) -> v + 1);
            }
        }
        final Expression finalExpression = new LogicalParser('\0', new StringSource(header[1])).parse();

        int line = 2;
        while (scanner.hasNextLine()) {
            Expression proofStage = new LogicalParser('\0', new StringSource(scanner.nextLine())).parse();
            if (isAxiom(proofStage) || isHypothesis(proofStage)) {
                proven.add(proofStage);
            } else {
                Expression left = findModusPonens(proofStage);
                if (left != null) {
                    proven.add(proofStage);
                    modusPonensRightToLeft.put(proofStage, left);
                } else {
                    System.out.println("Proof is incorrect at line " + line);
                    return;
                }
            }
            if (proofStage instanceof Impl) {
                provenImplRightToLeft.computeIfAbsent(
                        ((Impl) proofStage).getRight(),
                        x -> new HashSet<>(List.of(((Impl) proofStage).getLeft())));
                provenImplRightToLeft.get(((Impl) proofStage).getRight())
                        .add(((Impl) proofStage).getLeft());
            }
            if (!scanner.hasNextLine() && !proofStage.equals(finalExpression)) {
                System.out.println("The proof does not prove the required expression");
                return;
            }
            line++;
        }
        if (line == 2) {
            System.out.println("The proof does not prove the required expression");
            return;
        }

        naturalProofTree.get(0).add(new Vertex(hypothesis, finalExpression, null));
        for (int i = 0; i < naturalProofTree.size(); i++) {
            for (Vertex vertex : naturalProofTree.get(i)) {
                if (vertex.getSymbol() == null) {
                    Expression expression = vertex.getExpression();
                    if (vertex.getHypothesis().containsKey(expression)) {
                        vertex.setSymbol("[Ax]");
                    } else if (modusPonensRightToLeft.containsKey(expression)) {
                        Expression left = modusPonensRightToLeft.get(expression);
                        calcGetTreeLevel(i + 1);
                        naturalProofTree.get(i + 1).addAll(List.of(
                                new Vertex(hypothesis, left, null),
                                new Vertex(hypothesis, new Impl(left, expression), null)));
                        vertex.setSymbol("[E->]");
                    } else if (Util.isAxiomScheme1(expression)) {
                        deduct(vertex, i);
                    } else if (Util.isAxiomScheme2(expression)) {
                        deduct(vertex, i);

                        Vertex deducted = calcGetTreeLevel(i + 3).get(calcGetTreeLevel(i + 3).size() - 1);
                        deducted.setSymbol("[E->]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        Expression alpha = ((Impl) ((Impl) expression).getLeft()).getLeft();
                        Expression beta = ((Impl) ((Impl) expression).getLeft()).getRight();
                        Expression gamma = ((Impl) ((Impl) ((Impl) expression).getRight()).getRight()).getRight();
                        addSimpleNaturalMP(i + 4, alpha, beta, deductedHypo);
                        addSimpleNaturalMP(i + 4, alpha, new Impl(beta, gamma), deductedHypo);
                    } else if (Util.isAxiomScheme3(expression)) {
                        deduct(vertex, i);

                        Vertex deducted = calcGetTreeLevel(i + 2).get(calcGetTreeLevel(i + 3).size() - 1);
                        deducted.setSymbol("[I&]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        Expression alpha = ((Impl) expression).getLeft();
                        Expression beta = ((Impl) ((Impl) expression).getRight()).getLeft();
                        calcGetTreeLevel(i + 3).add(new Vertex(deductedHypo, alpha, "[Ax]"));
                        calcGetTreeLevel(i + 3).add(new Vertex(deductedHypo, beta, "[Ax]"));
                    } else if (Util.isAxiomScheme45(expression)) {
                        deduct(vertex, i);

                        Expression alpha = ((And) ((Impl) expression).getLeft()).getLeft();
                        Expression beta = ((And) ((Impl) expression).getLeft()).getRight();
                        Vertex deducted = calcGetTreeLevel(i + 1).get(calcGetTreeLevel(i + 1).size() - 1);
                        deducted.setSymbol(alpha.equals(((Impl) expression).getRight()) ? "[El&]" : "[Er&]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        calcGetTreeLevel(i + 2).add(new Vertex(deductedHypo, new And(alpha, beta), "[Ax]"));
                    } else if (Util.isAxiomScheme67(expression)) {
                        deduct(vertex, i);

                        Expression alpha = ((Or) ((Impl) expression).getRight()).getLeft();
                        Expression left = ((Impl) expression).getLeft();
                        Vertex deducted = calcGetTreeLevel(i + 1).get(calcGetTreeLevel(i + 1).size() - 1);
                        deducted.setSymbol(alpha.equals(left) ? "[Il|]" : "[Ir|]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        calcGetTreeLevel(i + 2).add(new Vertex(deductedHypo, left, "[Ax]"));
                    } else if (Util.isAxiomScheme8(expression)) {
                        deduct(vertex, i);

                        Expression alpha = ((Impl) ((Impl) expression).getLeft()).getLeft();
                        Expression gamma = ((Impl) ((Impl) expression).getLeft()).getRight();
                        Expression beta = ((Impl) ((Impl) ((Impl) expression).getRight()).getLeft()).getLeft();
                        Vertex deducted = calcGetTreeLevel(i + 3).get(calcGetTreeLevel(i + 3).size() - 1);
                        deducted.setSymbol("[E|]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        calcGetTreeLevel(i + 4).add(new Vertex(deductedHypo, new Or(alpha, beta), "[Ax]"));
                        deductedHypo.compute(alpha, (k, v) -> v == null ? 0 : v + 1);
                        addSimpleNaturalMP(i + 4, alpha, gamma, deductedHypo);
                        deductedHypo.remove(alpha);
                        deductedHypo.compute(beta, (k, v) -> v == null ? 0 : v + 1);
                        addSimpleNaturalMP(i + 4, beta, gamma, deductedHypo);
                    } else if (Util.isAxiomScheme9(expression)) {
                        Expression alpha = ((Impl) ((Impl) expression).getLeft()).getLeft();
                        Expression beta = ((Impl) ((Impl) expression).getLeft()).getRight();

                        deduct(vertex, i);

                        Vertex deducted = calcGetTreeLevel(i + 3).get(calcGetTreeLevel(i + 3).size() - 1);
                        deducted.setSymbol("[E->]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        addSimpleNaturalMP(i + 4, alpha, beta, deductedHypo);
                        addSimpleNaturalMP(i + 4, alpha, new Impl(beta, FALSE), deductedHypo);
                    } else if (Util.isAxiomScheme10(expression)) {
                        Expression alpha = ((Impl) expression).getLeft();

                        deduct(vertex, i);

                        Vertex deducted = calcGetTreeLevel(i + 2).get(calcGetTreeLevel(i + 2).size() - 1);
                        deducted.setSymbol("[E_|_]");
                        Map<Expression, Integer> deductedHypo = new LinkedHashMap<>(deducted.getHypothesis());
                        addSimpleNaturalMP(i + 3, alpha, FALSE, deductedHypo);
                    }
                }
            }
        }

        for (int i = naturalProofTree.size() - 1; i >= 0; i--) {
            for (Vertex vertex : naturalProofTree.get(i)) {
                System.out.println("[" + i + "] " + vertex);
            }
        }
    }
}
