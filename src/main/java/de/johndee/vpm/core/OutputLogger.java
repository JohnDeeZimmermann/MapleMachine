package de.johndee.vpm.core;

import de.johndee.vpm.instructions.Instruction;

public interface OutputLogger<Word extends Number> {

    void notify(String message, Instruction<Word> source);

}
