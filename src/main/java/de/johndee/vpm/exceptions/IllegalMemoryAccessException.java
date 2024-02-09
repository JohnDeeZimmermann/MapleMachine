package de.johndee.vpm.exceptions;

public class IllegalMemoryAccessException extends Exception {

    public IllegalMemoryAccessException(long address, long length, long codeAddress) {
        super("Illegal memory access at address " + address + " with length " + length + " from code address " + codeAddress + "!");
    }
}
