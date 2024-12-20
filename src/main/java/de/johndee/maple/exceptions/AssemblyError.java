package de.johndee.maple.exceptions;

public class AssemblyError extends RuntimeException {

    public AssemblyError(int lineNumber, String line, String error) {
        super("An error occurred at line " + lineNumber + ": '" + line + "' - " + error);
    }

}
