package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class ConditionalSkipInstruction<Word extends Number> extends ConditionalInstruction<Word>{
    public ConditionalSkipInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
    }

    @Override
    public void execute() {
        //TODO : Not Implemented

        throw new UnsupportedOperationException("Conditional Skip Instruction not implemented");
    }
}
