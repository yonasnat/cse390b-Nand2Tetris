package compiler.AST;

import java.util.List;

public class While extends Statement {
    public Expression condition;
    public List<Statement> statements;

    public While(Expression condition, List<Statement> statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[WHILE]");
        printIndentation(indent); System.out.println(" -Condition:");
        condition.printDebug(indent + 1);
        printIndentation(indent); System.out.println(" -Body:");
        for (Statement statement : statements) {
            statement.printDebug(indent + 1);
        }
    }

    @Override
    public void printASM() {
        comment("Start While");

        // PROJECT 7 TODO
        // (Step 7)

        // Implement the assembly generation for While. This is the final and most difficult part
        // of this project. We recommend looking back at the loops you've written in the past,
        // especially in Project 4, for inspiration about the "general" skeleton of a While loop.
        // This node is fairly unique among all the AST nodes, but you may be able to draw some
        // inspiration from If. Remember that a while loop should evaluate its condition,
        // execute the statements in its body if the condition is true, and then at the end of
        // the body re-evaluate the condition, only exiting the loop once the condition is false.

        // Hint: you will likely want to consider the following things while designing your
        // implementation:
        // - Do you need to generate labels? If so, how will you make sure those labels are unique?
        // - When will you cause the expression and statements to be evaluated at runtime?
        // - Would there be any interference if you had nested While loops?

        // Estimated lines of code in solution: ~14 lines (yours may vary a bit)



        comment("End While");
    }
}