package compiler.AST;

import java.util.Map;
import compiler.Compiler;

public abstract class ASTNode {
    private static int nextUniqueID = 0;

    /**
     * Convenience method for assembly generation.
     * Every time this method is called, returns a new ID that is guaranteed to be unique.
     * Hint: you may find this useful for generating labels.
     */
    protected static int getUniqueID() {
        return nextUniqueID++;
    }

    /**
     * Convenience method for assembly generation.
     * @return The symbol table generated for this program by the main method (using the VarDeclarations section).
     */
    protected static Map<String, Integer> getSymbolTable() {
        return Compiler.getSymbolTable();
    }

    /**
     * Convenience method for assembly generation.
     * Prints the specified message with indentation and // syntax to the ASM file being built up.
     */
    protected static void comment(String comment) {
        Compiler.getOutputStream().println("  // " + comment);
    }

    /**
     * Convenience method for assembly generation.
     * Prints the specified label to the ASM file being built up.
     */
    protected static void label(String label) {
        Compiler.getOutputStream().println("(" + label + ")");
    }

    /**
     * Convenience method for assembly generation.
     * Prints the specified label to the ASM file being built up, with the specified comment after.
     */
    protected static void label(String label, String comment) {
        Compiler.getOutputStream().println("(" + label + ") // " + comment);
    }

    /**
     * Convenience method for assembly generation.
     * Prints the specified instruction with indentation to the ASM file being built up.
     */
    protected static void instr(String instr) {
        Compiler.getOutputStream().println("  " + instr);
    }

    /**
     * Convenience method for assembly generation.
     * Prints the specified instruction with indentation to the ASM file being built up, with the specified comment after.
     */
    protected static void instr(String instr, String comment) {
        Compiler.getOutputStream().println("  " + instr + " // " + comment);
    }

    /**
     * Convenience method for assembly generation.
     * This method generates assembly code that pushes the value currently in
     * D to the stack (and handles incrementing the stack pointer). The value
     * remains in D after the generated assembly code executes.
     *
     * After the generated code executes, the assembly program should use
     * R1 to reference the value.
     */
    protected static void push() {
        comment("Start Push");
        // increment R1 (the stack pointer) to create space for a new value
        instr("@R1");
        instr("M=M+1");
        // "dereference" R1 to get the newly created space
        instr("A=M");
        // store the result in the newly created space
        instr("M=D");
        comment("End Push");
    }

    /**
     * Convenience method for assembly generation.
     * This method generates assembly code that pops the most recently pushed value from
     * the stack into D (and handles decrementing the stack pointer).
     */
    protected static void pop() {
        // decrement R1 (the stack pointer) to "remove" the most recently pushed value
        // (note that the value technically still exists in memory off the end of the stack --
        // but it doesn't matter as long as we only access the stack through R1, because
        // the only way R1 can reach that invalid value sitting around is if we push something else,
        // which overwrites the value anyway!)
        comment("Start Pop");
        instr("@R1");
        instr("M=M-1");
        comment("End Pop");
    }

    /**
     * Convenience method for debug output.
     * @param indent
     */
    protected static void printIndentation(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("    ");
        }
    }

    /**
     * All ASTNodes must implement printDebug so they can be printed easily.
     * This method should print out the ASTNode in a human readable format,
     * and the indent parameter is used to make the tree easy to read
     * by progressively indenting child nodes.
     */
    abstract void printDebug(int indent);
}
