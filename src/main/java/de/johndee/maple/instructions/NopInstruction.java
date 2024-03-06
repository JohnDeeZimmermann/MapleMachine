package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class NopInstruction<Word extends Number> extends Instruction<Word>{
    public NopInstruction(Processor<Word> processor, Word address, Word opcode) {
        super(processor, address, opcode);
    }

    @Override
    public void execute() {

    }
}
