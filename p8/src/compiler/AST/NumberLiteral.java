package compiler.AST;

public class NumberLiteral extends Expression {
    public int value;

    public NumberLiteral(String value) {
        this.value = Integer.parseInt(value);
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println(value);
    }

    @Override
    public void printASM() {
        // Prints a comment in the ASM output to make debugging easier.
        // Check out ASTNode.java for more convenience functions like this.
        comment("Start Number Literal");
        instr("@" + toString());
        instr("D=A");
        instr("@R0");
        instr("M=D");
        

        // PROJECT 7 TODO
        // (Step 1)

        // Implement the assembly generation for NumberLiteral. Remember that when this Java
        // method runs at compile time, it should "generate" (i.e. print using the instr() convenience
        // functions found in ASTNode.java) assembly instructions corresponding to this node. Later,
        // when those assembly instructions are executed at runtime, they should place the value of
        // this number literal into R0 (the register where ALL expressions place their result, per
        // convention).

        // It's okay (and in fact necessary) to "build up" assembly instructions in this method using
        // string concatenation. As a hint, you'll definitely want to incorporate the `value` field.
        // How can you encode the value as an assembly instruction that will cause that constant
        // value to be usable in the code at runtime?

        // Estimated lines of code in solution: ~4 lines.


        comment("End Number Literal");
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}