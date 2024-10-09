package compiler.AST;

public abstract class Expression extends ASTNode {
    /**
     * When implemented in subclasses, prints the assembly corresponding
     * to evaluating the current node. Expected to recursively call printASM on children of the node.
     */
    abstract void printASM();
}