package compiler.AST;

import java.util.List;

public class If extends Statement {
    public Expression condition;
    public List<Statement> statements;

    public If(Expression condition, List<Statement> statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[IF]");
        printIndentation(indent); System.out.println(" -Condition:");
        condition.printDebug(indent + 1);
        printIndentation(indent); System.out.println(" -Body:");
        for (Statement statement : statements) {
            statement.printDebug(indent + 1);
        }
    }

    @Override
    public void printASM() {
        comment("Start If");

        // PROJECT 7 TODO
        // (Step 6)

        // This is a BUGGY implementation of the ASM generation for the If
        // statement node. Debug the implementation and fix the TWO bugs it
        // contains. Recall that the If node should evaluate its expression, then
        // evaluate its statements if the expression evaluated to true (in our
        // simplified world, true is represented as a non-zero int). Note that
        // we cannot know until runtime whether the expression will evaluate to true,
        // so at compile time we must generate code that will make the jump
        // appropriately at runtime.



        // generate the assembly code for the condition
        condition.printASM();
        // grab the result from R0
        instr("@R0");
        instr("D=M");
        // boolean false is represented as 0, boolean true is represented as any non-zero value
        instr("@IFEND" + getUniqueID());
        instr("D;JNE");
        // otherwise, continue and run the assembly generated for each statement in turn
        for (Statement statement : statements) {
            statement.printASM();
        }
        // we generate a label so the assembly can jump here if needed
        label("IFEND" + getUniqueID());

        comment("End If");
    }
}