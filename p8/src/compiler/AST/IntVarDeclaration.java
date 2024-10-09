package compiler.AST;

import java.util.Map;

public class IntVarDeclaration extends VarDeclaration {
    public Identifier name;

    public IntVarDeclaration(Identifier name) {
        this.name = name;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[INT DECLARATION] " + name.toString());
    }

    @Override
    public int addToSymbolTable(int currentIndex, Map<String, Integer> symbolTable) {
        symbolTable.put(name.toString(), currentIndex);
        return currentIndex + 1;
    }
}
