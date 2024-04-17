package de.johndee.maple.exceptions;

import de.johndee.maple.core.IODevice;
import de.johndee.maple.instructions.Instruction;

public class IllegalIOAccessException extends RuntimeException {

    public IllegalIOAccessException(Instruction instruction, IODevice device) {
        super("Illegal IO access to device " + device.getName() + "/" + device.getID()+ " mit Code Adresse " + instruction.getAddress().longValue() + "!");
    }

}
