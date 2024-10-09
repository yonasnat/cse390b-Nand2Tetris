package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JackScanner {
    /**
     * An enum expressing every possible Token type. Note that each of these is a
     * different "lexical class" -- that is, each has a unique meaning to the program.
     * For example, BECOMES represents the assignment operator (a single = sign), while
     * EQUALS represents the equality operator (two == signs). Even though these two tokens
     * have very similar representations, when found in the source code they _mean_ very
     * different things, and thus are two completely different tokens.
     */
    public enum Token {
        INT,
        VAR,
        LET,
        WHILE,
        IF,
        IDENTIFIER,
        NUMBER,
        LEFTPAREN,
        RIGHTPAREN,
        LEFTCURLY,
        RIGHTCURLY,
        LEFTSQUARE,
        RIGHTSQUARE,
        COMMA,
        SEMICOLON,
        BECOMES,
        EQUALS,
        NOTEQUALS,
        PLUS,
        MINUS
    }

    private static final String DIGIT = "[0-9]";
    private static final String SYMBOL_START = "[a-zA-Z_]";
    private static final String SYMBOL = "[a-zA-Z0-9_]";
    private static final String WHITESPACE = "\\s";

    private String input;
    private int nextIndex;
    private Token currToken;
    private String currTokenValue;

    public JackScanner(String filename) throws FileNotFoundException {
        // Read in the entire input as a string so we can do our custom scanning algorithm
        // on it. Here, java.util.Scanner is used only to get the entire file contents.
        Scanner inputReader = new Scanner(new File(filename));
        StringBuilder inputBuilder = new StringBuilder();
        while (inputReader.hasNextLine()) {
            String nextLine = inputReader.nextLine();
            if (nextLine.contains("//")) {
                // If this line contains a comment, only keep the line up until that comment
                nextLine = nextLine.substring(0, nextLine.indexOf("//"));
            }
            inputBuilder.append(nextLine);
        }
        input = inputBuilder.toString();

        nextIndex = 0;
        currToken = null;
        currTokenValue = null;

        // Skip any whitespace at the beginning of the string.
        skipWhitespace();
    }

    /**
     * @return True if more tokens can be read from the source file, false otherwise.
     */
    public boolean hasMoreTokens() {
        return nextIndex < input.length();
    }

    /**
     * Advance the state of the scanner by one token, skipping whitespace and properly
     * accumulating multi-character tokens (like identifiers or ==). After calling this method,
     * the current token can be accessed via the get____ methods below.
     */
    public void advance() {
        String currChar = input.substring(nextIndex, nextIndex + 1);

        if (currChar.equals("(")) {
            currToken = Token.LEFTPAREN;
            nextIndex++;
        } else if (currChar.equals(")")) {
            currToken = Token.RIGHTPAREN;
            nextIndex++;
        } else if (currChar.equals("{")) {
            currToken = Token.LEFTCURLY;
            nextIndex++;
        } else if (currChar.equals("}")) {
            currToken = Token.RIGHTCURLY;
            nextIndex++;
        } else if (currChar.equals("[")) {
            currToken = Token.LEFTSQUARE;
            nextIndex++;
        } else if (currChar.equals("]")) {
            currToken = Token.RIGHTSQUARE;
            nextIndex++;
        } else if (currChar.equals(",")) {
            currToken = Token.COMMA;
            nextIndex++;
        } else if (currChar.equals(";")) {
            currToken = Token.SEMICOLON;
            nextIndex++;
        } else if (currChar.equals("+")) {
            currToken = Token.PLUS;
            nextIndex++;
        } else if (currChar.equals("-")) {
            currToken = Token.MINUS;
            nextIndex++;
        } else if (currChar.equals("=")) {
            // This could be either a single = (BECOMES), or a double == (EQUALS).
            nextIndex++;
            if (input.substring(nextIndex, nextIndex + 1).equals("=")) {
                currToken = Token.EQUALS;
                nextIndex++;
            } else {
                currToken = Token.BECOMES;
            }
        } else if (currChar.equals("!")) {
            nextIndex++;
            if (input.substring(nextIndex, nextIndex + 1).equals("=")) {
                currToken = Token.NOTEQUALS;
                nextIndex++;
            }
        } else if (currChar.matches(DIGIT)) {
            // Continue advancing to read all of the digits that we can
            StringBuilder number = new StringBuilder();
            do {
                nextIndex++;
                number.append(currChar);
                currChar = input.substring(nextIndex, nextIndex + 1);
            } while (currChar.matches(DIGIT));
            currToken = Token.NUMBER;
            currTokenValue = number.toString();

        } else if (currChar.matches(SYMBOL_START)) {
            // Continue advancing to read all of the symbol characters that we can
            StringBuilder symbol = new StringBuilder();
            do {
                nextIndex++;
                symbol.append(currChar);
                currChar = input.substring(nextIndex, nextIndex + 1);
            } while (currChar.matches(SYMBOL)); // Note that after the first character we allow digits

            String symbolValue = symbol.toString();
            if (symbolValue.equalsIgnoreCase("int")) {
                currToken = Token.INT;
            } else if (symbolValue.equalsIgnoreCase("var")) {
                currToken = Token.VAR;
            } else if (symbolValue.equalsIgnoreCase("let")) {
                currToken = Token.LET;
            } else if (symbolValue.equalsIgnoreCase("while")) {
                currToken = Token.WHILE;
            } else if (symbolValue.equalsIgnoreCase("if")) {
                currToken = Token.IF;
            } else {
                currToken = Token.IDENTIFIER;
                currTokenValue = symbolValue;
            }
        } else {
            indicateScanError(currChar);
        }

        skipWhitespace();
    }

    /**
     * @return The token type, chosen from the members of the Token enum.
     */
    public Token getToken() {
        return currToken;
    }

    /**
     * If the current token is Token.IDENTIFIER or Token.NUMBER, it also
     * comes with a value (the string containing the identifier itself or the
     * string representation of the number).
     * @return The token value of the current token. If the current token is not
     * IDENTIFIER or NUMBER, this method should not be called -- in that case,
     * its behavior is undefined.
     */
    public String getTokenValue() {
        return currTokenValue;
    }

    /**
     * @return A user-friendly string representation of the token, including its value if it has one.
     * Intended for use in debug printouts.
     */
    public String getTokenString() {
        if (currToken == Token.IDENTIFIER || currToken == Token.NUMBER) {
            return currToken.toString() + "(" + currTokenValue + ")";
        } else {
            return currToken.toString();
        }
    }

    /**
     * Advance the scanner past any amount of whitespace to get to the next token.
     */
    private void skipWhitespace() {
        while (nextIndex < input.length() && input.substring(nextIndex, nextIndex + 1).matches(WHITESPACE)) {
            nextIndex++;
        }
    }

    /**
     * Indicate that the scanner is unable to continue after finding a character
     * in the input that is not part of MicroJack.
     */
    private void indicateScanError(String invalidCharacter) {
        System.err.println("There was a scanning error!");
        System.err.println("The scanner saw the invalid character: " + invalidCharacter);
        System.err.println("Check the source file to make sure that it's valid Micro-Jack as specified in the Project 7 spec.");
        System.err.println("When the error occurred, the scanner was at index " + nextIndex + " of the input program string.");
        System.exit(1);
    }


}