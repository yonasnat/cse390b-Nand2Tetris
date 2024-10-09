package compiler.AST;

public abstract class Statement extends ASTNode {
    /**
     * When implemented in subclasses, prints the assembly corresponding
     * to running the current node. Expected to recursively call printASM on children of the node.
     */
    abstract void printASM();
}