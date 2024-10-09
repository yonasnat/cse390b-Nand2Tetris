package compiler.AST;

public class ArrayVarAccess extends VarAccess {
    public Identifier name;
    public Expression index;

    public ArrayVarAccess(Identifier name, Expression index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[ARRAY ACCESS]");
        printIndentation(indent); System.out.println(" -Variable:");
        name.printDebug(indent + 1);
        printIndentation(indent); System.out.println(" -Index:");
        index.printDebug(indent + 1);
    }

    @Override
    public void printASM() {
        if (!getSymbolTable().containsKey(name.toString())) {
            indicateUndeclaredVariableError(name.toString());
        }
        int variableAddress = getSymbolTable().get(name.toString());
        comment("Start Array Access");

        // PROJECT 7 TODO
        // (Step 5)

        // First, read through IntVarAccess to understand how that code works. This class is
        // very similar, but instead of being a "simple" int variable (e.g. "a"), this class
        // is for int array variables (e.g. "a[20]" or "a[b]").

        // For an array access, we need to add together two values: the address in memory
        // where the variable resides (accomplished with a symbol table lookup just like
        // in IntVarAccess), and an OFFSET from that address produced by evaluating the expression
        // within the square brackets.  Here, that expression is stored as the field `index`.

        // The adding code is completed for you below. To complete the array access code,
        // insert code here (ABOVE the adding code) that will evaluate the field `index` at runtime,
        // then load the result into the D register. The provided code then takes care of adding
        // the variable address to the offset. Remember that by convention, evaluating the field `index`
        // will leave its result in R0 because `index` is an Expression.

        // Estimated lines of code in solution: ~3 lines.





        // use an A-instruction to load the offset of the array variable into A
        instr("@" + variableAddress, "(address of var: " + name.toString() + ")");
        // add the offset to that address, and grab the corresponding value
        instr("A=D+A", "add offset to array address");
        instr("D=M");
        // put the value in R0
        instr("@R0");
        instr("M=D");
        comment("End Array Access");
    }

    @Override
    public void printASMToGetAddress() {
        if (!getSymbolTable().containsKey(name.toString())) {
            indicateUndeclaredVariableError(name.toString());
        }
        int variableAddress = getSymbolTable().get(name.toString());
        comment("Start Array ADDRESS (for assignment)");

        // PROJECT 7 TODO
        // (Step 5)

        // Add the same code here as you did above. The provided code in this
        // method is adapted to produce the address instead of the value, just like
        // in IntVarAccess.

        // Estimated lines of code in solution: ~3 lines.




        // use an A-instruction to load the offset of the array variable into A
        instr("@" + variableAddress, "(address of var: " + name.toString() + ")");
        // add the offset to that address and store the ADDRESS in R0
        // unlike the above method, this method should evaluate to the ADDRESS of
        // the variable, which is what makes Assignment work when an int[] variable
        // is on the left side of the assignment.
        instr("D=D+A");
        instr("@R0");
        instr("M=D");
        comment("End Array ADDRESS (for assignment)");
    }

    private void indicateUndeclaredVariableError(String variable) {
        System.err.println("There was a code generation error!");
        System.err.println("While looking in the symbol table, the code generator couldn't find the array symbol: " + variable);
        System.err.println("Check the source file to make sure that the symbol is declared as a variable at the top.");
        System.exit(1);
    }
}
