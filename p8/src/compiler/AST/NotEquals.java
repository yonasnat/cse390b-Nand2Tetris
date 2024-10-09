package compiler.AST;

public class NotEquals extends Expression {
    public Expression left;
    public Expression right;

    public NotEquals(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[NOT EQUALS]");
        printIndentation(indent); System.out.println(" -Left:");
        left.printDebug(indent + 1);
        printIndentation(indent); System.out.println(" -Right:");
        right.printDebug(indent + 1);
    }

    @Override
    public void printASM() {
        int uniqueID = getUniqueID();
        comment("Start NotEquals");

        // PROJECT 7 TODO
        // (Step 4)

        // After reading through Equals, implement NotEquals.
        // You may copy and paste some or all of Equals and modify as you see fit.

        // Estimated lines of code in solution: ~21




        comment("End NotEquals");
    }
}