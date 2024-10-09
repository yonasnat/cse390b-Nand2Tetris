package compiler.AST;

public class Identifier extends ASTNode {
    public String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void printDebug(int indent) {
        printIndentation(indent); System.out.println(name);
    }
}