package compiler.AST;

import java.util.Map;

public abstract class VarDeclaration extends ASTNode {
    /**
     * When implemented in subclasses, adds a node's declared variable to the symbol table.
     * Given the next available address, it returns the new next available index, taking into account the size
     * of the variable that was added -- 1 for ints, N for an array of length N.
     * @param currentAddress The next available address in memory for a variable to be placed.
     * @param symbolTable The symbol table structure that will be updated with the declared variable.
     * @return The new next available address in memory.
     */
    public abstract int addToSymbolTable(int currentAddress, Map<String, Integer> symbolTable);
}
