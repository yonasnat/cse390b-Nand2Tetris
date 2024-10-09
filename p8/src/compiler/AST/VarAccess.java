package compiler.AST;

public abstract class VarAccess extends Expression {
    /**
     * When implemented in subclasses, prints the assembly corresponding
     * to accessing the ADDRESS of the current expression. May call printASM on children of the
     * node if that is necessary to obtain the correct address.
     */
    abstract void printASMToGetAddress();
}
