package compiler.AST;

import java.util.List;

public class JackProgram extends ASTNode {
    public List<VarDeclaration> variables;
    public List<Statement> statements;

    public JackProgram(List<VarDeclaration> variables, List<Statement> statements) {
        this.variables = variables;
        this.statements = statements;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[PROGRAM]");
        printIndentation(indent); System.out.println(" -Variable Declarations:");
        for (VarDeclaration variable : variables) {
            variable.printDebug(indent + 1);
        }
        printIndentation(indent); System.out.println(" -Statements:");
        for (Statement statement : statements) {
            statement.printDebug(indent + 1);
        }
    }

    public void printASM() {
        // When the program starts executing, it should have 1023 loaded into
        // R1 (the stack pointer) to indicate the start of the stack (so the first
        // pushed value will go in 1024).
        comment("Start Program Preamble (set up the stack)");
        instr("@1023");
        instr("D=A");
        instr("@R1");
        instr("M=D");
        comment("End Program Preamble");
        // Then, it should execute each statement in turn. Each statement node in the AST knows how to
        // generate its own code, so this is simple! We just call printASM on each child node
        // (passing down the symbol table).
        // Hint: this pattern will be very common among the rest of the AST nodes!
        comment("Start Program Body");
        for (Statement statement : statements) {
            statement.printASM();
        }
        comment("End Program Body");

        // Finally, the program should enter an infinite loop to "terminate" and
        // prevent running forever through the instruction memory.
        comment("Infinite loop to \"terminate\" program");
        label("END");
        instr("@END");
        instr("0;JMP");
    }
}