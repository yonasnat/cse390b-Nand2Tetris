package compiler.AST;

public class IntVarAccess extends VarAccess {
    public Identifier name;

    public IntVarAccess(Identifier name) {
        this.name = name;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println(name.toString());
    }

    @Override
    public void printASM() {
        if (!getSymbolTable().containsKey(name.toString())) {
            indicateUndeclaredVariableError(name.toString());
        }
        int variableAddress = getSymbolTable().get(name.toString());
        comment("Start Int Var Access");
        // use an A-instruction to load the offset of the array variable into A
        instr("@" + variableAddress, "(address of var: " + name.toString() + ")");
        instr("D=M");
        // put the value in R0
        instr("@R0");
        instr("M=D");
        comment("End Int Var Access");
    }

    @Override
    public void printASMToGetAddress() {
        if (!getSymbolTable().containsKey(name.toString())) {
            indicateUndeclaredVariableError(name.toString());
        }
        int variableAddress = getSymbolTable().get(name.toString());
        comment("Start Int Var ADDRESS (for assignment)");
        // use an A-instruction to load the offset of the array variable into A
        instr("@" + variableAddress, "(address of var: " + name.toString() + ")");
        // unlike the above method, this method should evaluate to the ADDRESS of
        // the variable, which is what makes Assignment work when an int variable name
        // is on the left side of the assignment.
        instr("D=A");
        // put the value in R0
        instr("@R0");
        instr("M=D");
        comment("End Int Var ADDRESS (for assignment)");
    }

    private void indicateUndeclaredVariableError(String variable) {
        System.err.println("There was a code generation error!");
        System.err.println("While looking in the symbol table, the code generator couldn't find the int symbol: " + variable);
        System.err.println("Check the source file to make sure that the symbol is declared as a variable at the top.");
        System.exit(1);
    }
}
