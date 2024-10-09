package compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import compiler.AST.*;
import compiler.JackScanner.Token;

public class JackParser {
    private JackScanner scanner;

    public JackParser(JackScanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Parses a complete MicroJack program (which should be the entirety of a single file).
     *
     * When this method is called, it is assumed that the scanner is currently
     * *ON* the first token of the program. If this program has var declarations, that token is VAR. Otherwise, it is the
     * starting token of a different kind of statement.
     *
     * This method advances through every token in the parsed program, exhausting the scanner.
     *
     * If the MicroJack program is invalid (does not adhere to the rules of the language), it is
     *
     * @return The parsed AST Node, which must be a complete Jack program.
     */
    public JackProgram parseProgram() {

        List<VarDeclaration> vars = new ArrayList<>();
        while (scanner.hasMoreTokens() && scanner.getToken() == Token.VAR) {
            vars.addAll(parseVarDeclaration()); // will advance to start of next var declaration or statement or end of program
        }

        List<Statement> statements = new ArrayList<>();
        while (scanner.hasMoreTokens()) {
            statements.add(parseStatement()); // will advance to start of next statement or end of program
        }

        return new JackProgram(vars, statements);
    }

    /**
     * Parse a single var declaration line, which may contain multiple var declarations separated by commas
     * (and can contain a mix of int and int[] declarations).
     *
     * When this method is called, it is assumed that the scanner is currently
     * *ON* the token that starts the variable declaration (VAR).
     *
     *
     * This method advances through every token in the parsed var declaration line, ending up with the scanner
     * on the token *AFTER* the semicolon ending the line.
     *
     * @return The list of parsed AST Nodes, which must be subclasses of VarDeclaration.
     */
    public List<VarDeclaration> parseVarDeclaration() {

        advanceAndVerify(new Token[]{Token.INT}); // advance to INT
        advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.SEMICOLON}); // advance to first identifier or SEMICOLON

        List<VarDeclaration> vars = new ArrayList<>();
        while (scanner.getToken() != Token.SEMICOLON) {
            Identifier varName = new Identifier(scanner.getTokenValue());

            advanceAndVerify(new Token[]{Token.LEFTSQUARE, Token.COMMA, Token.SEMICOLON}); // advance to LEFTSQUARE (if array), COMMA (if more declarations), or SEMICOLON (if no more)

            if (scanner.getToken() == Token.LEFTSQUARE) {
                // This is an array var declaration
                advanceAndVerify(new Token[]{Token.NUMBER}); // advance to start of expression indicating length
                NumberLiteral arrayLength = new NumberLiteral(scanner.getTokenValue());
                vars.add(new ArrayVarDeclaration(varName, arrayLength));
                advanceAndVerify(new Token[]{Token.RIGHTSQUARE}); // advance to RIGHTSQUARE
                advanceAndVerify(new Token[]{Token.COMMA, Token.SEMICOLON}); // advance to COMMA (if more declarations), or SEMICOLON (if no more)
            } else {
                vars.add(new IntVarDeclaration(varName));
            }

            if (scanner.getToken() == Token.COMMA) {
                advanceAndVerify(new Token[]{Token.IDENTIFIER}); // advance to next identifier
            }
        }
        if (scanner.hasMoreTokens()) {
            scanner.advance(); // advance past this VAR statement
        }

        return vars;
    }

    /**
     * Parse a single statement, which is either Assignment, While, or If.
     * Note that statements may contain expressions and/or recursively contain statements,
     * in which case the appropriate method is called (potentially recursively).
     *
     * When this method is called, it is assumed that the scanner is currently
     * *ON* the token that starts the statement -- either LET, WHILE, or IF
     *
     * This method advances through every token in the parsed statement, ending up with the scanner
     * on the token *AFTER* a complete instance of Statement.
     *
     * @return The parsed AST Node, which must be a subclass of Statement.
     */
    public Statement parseStatement() {
        verify(new Token[]{Token.LET, Token.WHILE, Token.IF});
        if (scanner.getToken() == Token.LET) {
            advanceAndVerify(new Token[]{Token.IDENTIFIER}); // advance to IDENTIFIER (variable being assigned to)
            Identifier name = new Identifier(scanner.getTokenValue());
            advanceAndVerify(new Token[]{Token.BECOMES, Token.LEFTSQUARE}); // advance to BECOMES or LEFTSQUARE

            VarAccess varAccess;
            if (scanner.getToken() == Token.LEFTSQUARE) {
                advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of expression (array index)
                Expression index = parseExpression(); // will advance to RIGHTSQUARE
                verify(new Token[]{Token.RIGHTSQUARE});
                advanceAndVerify(new Token[]{Token.BECOMES}); // advance to BECOMES
                varAccess = new ArrayVarAccess(name, index);
            } else {
                varAccess = new IntVarAccess(name);
            }

            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of expression (value to be assigned)
            Expression value = parseExpression();
            verify(new Token[]{Token.SEMICOLON});
            if (scanner.hasMoreTokens()) {
                scanner.advance(); // advance past SEMICOLON to start of next statement, if there are more
            }

            return new Assignment(varAccess, value);
        } else if (scanner.getToken() == Token.WHILE) {
            advanceAndVerify(new Token[]{Token.LEFTPAREN}); // advance to LEFTPAREN
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of expression
            Expression condition = parseExpression(); // advances to RIGHTPAREN
            verify(new Token[]{Token.RIGHTPAREN});
            advanceAndVerify(new Token[]{Token.LEFTCURLY}); // advance to LEFTCURLY
            advanceAndVerify(new Token[]{Token.LET, Token.WHILE, Token.IF, Token.RIGHTCURLY}); // advance to start of statements or RIGHTCURLY (ending loop)
            List<Statement> statements = new ArrayList<>();
            while (scanner.getToken() != Token.RIGHTCURLY) {
                statements.add(parseStatement()); // will advance to start of next statement or RIGHTCURLY
                verify(new Token[]{Token.LET, Token.WHILE, Token.IF, Token.RIGHTCURLY});
            }
            if (scanner.hasMoreTokens()) {
                scanner.advance(); // advance past SEMICOLON to start of next statement, if there are more
            }

            return new While(condition, statements);
        } else { // scanner.getToken() == Token.IF
            advanceAndVerify(new Token[]{Token.LEFTPAREN}); // advance to LEFTPAREN
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of expression
            Expression condition = parseExpression(); // advances to RIGHTPAREN
            verify(new Token[]{Token.RIGHTPAREN});
            advanceAndVerify(new Token[]{Token.LEFTCURLY}); // advance to LEFTCURLY
            advanceAndVerify(new Token[]{Token.LET, Token.WHILE, Token.IF, Token.RIGHTCURLY}); // advance to start of statements or RIGHTCURLY (ending loop)
            List<Statement> statements = new ArrayList<>();
            while (scanner.getToken() != Token.RIGHTCURLY) {
                statements.add(parseStatement()); // will advance to start of next statement or RIGHTCURLY
                verify(new Token[]{Token.LET, Token.WHILE, Token.IF, Token.RIGHTCURLY});
            }
            if (scanner.hasMoreTokens()) {
                scanner.advance(); // advance past SEMICOLON to start of next statement, if there are more
            }
            return new If(condition, statements);
        }
    }

    /**
     * Parse a single expression, which may contain any number of recursively nested expressions.
     * In MicroJack, expressions do not have a natural order of operations, so no guarantee
     * is made about the order in which multiple expressions are nested.
     *
     *
     * When this method is called, it is assumed that the scanner is currently
     * *ON* the token that starts this expression -- either IDENTIFIER, NUMBER, or LEFTPAREN
     *
     * This method advances through every token in the parsed expression, ending up with the scanner
     * on the token *AFTER* a complete instance of Expression.
     *
     * @return The parsed AST Node, which must be a subclass of Expression.
     */
    public Expression parseExpression() {
        verify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN});

        Expression left;
        if (scanner.getToken() == Token.LEFTPAREN) {
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of inner expression
            left = parseExpression(); // advances past inner expression to RIGHTPAREN
            verify(new Token[]{Token.RIGHTPAREN});
            scanner.advance(); // advance to after this expression
        } else if (scanner.getToken() == Token.IDENTIFIER) {
            Identifier name = new Identifier(scanner.getTokenValue());
            scanner.advance(); // advance to LEFTSQUARE (if array access), or rest of expression, or after it

            if (scanner.getToken() == Token.LEFTSQUARE) {
                // This identifier belongs to an array access
                advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of index expression
                Expression index = parseExpression(); // advances to RIGHTSQUARE
                verify(new Token[]{Token.RIGHTSQUARE});
                left = new ArrayVarAccess(name, index);
                scanner.advance(); // advance to rest of expression or after it
            } else {
                // This is just a simple int identifier
                left = new IntVarAccess(name);
            }
        } else { // scanner.getToken() == Token.NUMBER
            left = new NumberLiteral(scanner.getTokenValue());
            scanner.advance(); // advance to rest of expression, or after it
        }

        if (scanner.getToken() == Token.PLUS) {
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of right expression
            Expression right = parseExpression(); // advances past expression
            return new Plus(left, right);
        } else if (scanner.getToken() == Token.MINUS) {
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of right expression
            Expression right = parseExpression(); // advances past expression
            return new Minus(left, right);
        } else if (scanner.getToken() == Token.EQUALS) {
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of right expression
            Expression right = parseExpression(); // advances past expression
            return new Equals(left, right);
        } else if (scanner.getToken() == Token.NOTEQUALS) {
            advanceAndVerify(new Token[]{Token.IDENTIFIER, Token.NUMBER, Token.LEFTPAREN}); // advance to start of right expression
            Expression right = parseExpression(); // advances past expression
            return new NotEquals(left, right);
        } else {
            return left;
        }
    }

    /**
     * Convenience function. Advances the scanner, then verifies that the new
     * token is one of the given set of expected tokens. If not, indicates an error and
     * stops the parse.
     */
    private void advanceAndVerify(Token[] expected) {
        scanner.advance();
        verify(expected);
    }

    /**
     * Verifies that the current token is one of the given set of expected tokens. If not,
     * indicates an error and stops the parse.
     */
    private void verify(Token[] expected) {
        for (Token token : expected) {
            if (token == scanner.getToken()) {
                return;
            }
        }
        indicateParseError(expected);
    }

    /**
     * Indicate an error with the parse and crash with useful debugging information to help isolate
     * the issue.
     */
    private void indicateParseError(Token[] expected) {
        System.err.println("There was a parse error!");
        System.err.println("The parser expected to get one of: " + Arrays.toString(expected));
        System.err.println("But instead it got: " + scanner.getTokenString());
        System.err.println("Check the source file to make sure that it's valid Micro-Jack as specified in the Project 7 spec.");
        System.err.println("When the error occurred, the scanner contained the following remaining tokens:");
        while (scanner.hasMoreTokens()) {
            scanner.advance();
            System.err.print(scanner.getTokenString() + " ");
        }
        System.err.println();
        System.exit(1);
    }
}