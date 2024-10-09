package compiler.AST;

public class Assignment extends Statement {
    public VarAccess variable;
    public Expression value;

    public Assignment(VarAccess variable, Expression value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[ASSIGNMENT]");
        printIndentation(indent); System.out.println(" -Variable:");
        variable.printDebug(indent + 1);
        printIndentation(indent); System.out.println(" -Value:");
        value.printDebug(indent + 1);
    }

    @Override
    public void printASM() {
        comment("Start Assignment");
        // generate the code that gets the address of the VarAccess being assigned to
        variable.printASMToGetAddress();
        instr("@R0");
        instr("D=M");
        // push the address to be assigned to while we evaluate the expression
        // (see ASTNode.java)
        push();
        // generate the code for the expression, which will place the result in R0
        value.printASM();
        // grab the expression result
        instr("@R0");
        instr("D=M");
        // grab the previously stored address by "dereferencing" R1 twice (once to go to the address stored in the
        // stack, and then once more to go to that actual address in memory)
        instr("@R1");
        instr("A=M");
        instr("A=M");
        // store the expression result in the address, which stores it in the variable!
        instr("M=D");
        // pop the address to restore the stack
        // (see ASTNode.java)
        pop();
        comment("End of Assignment");
    }

}