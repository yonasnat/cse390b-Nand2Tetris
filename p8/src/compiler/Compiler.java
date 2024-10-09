package compiler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import compiler.AST.JackProgram;
import compiler.AST.VarDeclaration;

public class Compiler {
    public static final int VARIABLE_REGION_START = 256;
    public static final int SCREEN_REGION_START = 16384;
    public static final int KEYBOARD_REGION_START = 24576;

    // Not the most elegant solution, but a simple way to get
    // these two "global" structures available to all the
    // AST nodes so they all have access to variable lookups
    // and printing to the output file.
    private static Map<String, Integer> symbolTable = null;
    private static PrintStream outputStream = null;

    public static void main (String[] args) {
        if (args.length != 2) {
            usage();
            return;
        }

        JackScanner scan;

        try {
            scan = new JackScanner(args[1]);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file " + args[1] + ".");
            System.err.println("Check that the file exists and try running again.");
            usage();
            return;
        }

        if (args[0].equalsIgnoreCase("scan")) {
            while (scan.hasMoreTokens()) {
                scan.advance();
                System.out.println(scan.getTokenString());
            }
            return;
        }

        scan.advance(); // advance to the first token of the program
        JackParser parse = new JackParser(scan);
        JackProgram program = parse.parseProgram();

        if (args[0].equalsIgnoreCase("parse")) {
            program.printDebug(0);
            return;
        }

        if (args[0].equalsIgnoreCase("compile")) {

            // Build the symbol table. This is a mapping from variable names to
            // their corresponding addresses in memory. We add two "global" variables
            // to every program: the screen and the keyboard memory maps.
            symbolTable = new HashMap<>();
            symbolTable.put("screen", SCREEN_REGION_START);
            symbolTable.put("keyboard", KEYBOARD_REGION_START);
            int currentIndex = VARIABLE_REGION_START;
            for (VarDeclaration varDeclaration : program.variables) {
                currentIndex = varDeclaration.addToSymbolTable(currentIndex, symbolTable);
            }

            // Open the output file for writing.
            if (!args[1].endsWith(".jack")) {
                System.err.println("The MicroJack file must end with the .jack extension.");
                usage();
                return;
            }
            String outputFileName = args[1].substring(0, args[1].length() - 5) + ".asm";
            try {
                outputStream = new PrintStream(new File(outputFileName));
            } catch (FileNotFoundException e) {
                System.err.println("Could not open " + outputFileName + " for writing.");
                System.err.println("Check the permissions on your directory, or contact the course staff for debugging help.");
                usage();
                return;
            }

            // Compile!
            // Prints the ASM to the specified output file, for convenience.
            program.printASM();

            outputStream.close();
            return;
        }

        // If we reach this point, it was not a recognized mode.
        System.err.println("Unrecognized mode: " + args[0] + ".");
        usage();
    }

    /**
     * @return The symbol table generated for the current program.
     */
    public static Map<String, Integer> getSymbolTable() {
        if (symbolTable == null) {
            throw new IllegalStateException("The symbol table cannot be accessed before it is initialized in main.");
        }
        return symbolTable;
    }

    /**
     * @return The open output stream for printing generated assembly to.
     */
    public static PrintStream getOutputStream() {
        if (symbolTable == null) {
            throw new IllegalStateException("The output file cannot be accessed before it is initialized in main.");
        }
        return outputStream;
    }

    /**
     * Prints a usage message to standard error.
     */
    private static void usage() {
        System.err.println("Usage: java compiler/Compiler <MODE> <PATH TO MICROJACK FILE>.jack");
        System.err.println("       Modes: scan    -- prints token stream to STDOUT");
        System.err.println("              parse   -- prints AST representation to STDOUT");
        System.err.println("              compile -- prints generated Hack Assembly to <PATH TO MICROJACK FILE>.asm");
    }
}