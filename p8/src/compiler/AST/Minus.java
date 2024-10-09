package compiler.AST;

public class Minus extends Expression {
    public Expression left;
    public Expression right;

    public Minus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[MINUS]");
        printIndentation(indent); System.out.println(" -Left:");
        left.printDebug(indent + 1);
        printIndentation(indent); System.out.println(" -Right:");
        right.printDebug(indent + 1);
    }

    @Override
    public void printASM() {
        comment("Start Minus");

        // PROJECT 7 TODO
        // (Step 3)

        // After debugging the starter code in Plus, implement the assembly generation for
        // Minus. You may copy and paste some or all of Plus and modify as you see fit.

        // Estimated lines of code in solution: ~13 lines.



        comment("End Minus");
    }
}