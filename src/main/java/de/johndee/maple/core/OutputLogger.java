package de.johndee.maple.core;

import de.johndee.maple.instructions.Instruction;

public interface OutputLogger<Word extends Number> {

    void notify(String message, Instruction<Word> source);

}
