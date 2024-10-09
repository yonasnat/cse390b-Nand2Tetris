package compiler.AST;

import java.util.Map;

public class ArrayVarDeclaration extends VarDeclaration {
    public Identifier name;
    public NumberLiteral length;

    public ArrayVarDeclaration(Identifier name, NumberLiteral length) {
        this.name = name;
        this.length = length;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println("[ARRAY DECLARATION] " + name.toString() + "[" + length.toString() + "]");
    }

    @Override
    public int addToSymbolTable(int currentIndex, Map<String, Integer> symbolTable) {
        symbolTable.put(name.toString(), currentIndex);
        return currentIndex + length.value;
    }
}
