package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class SkipInstruction<Word extends Number> extends ConditionalInstruction<Word>{
    public SkipInstruction(Processor<Word> processor,
                           Word address,
                           Word OPCode,
                           Word rdest,
                           Word option,
                           Word rargs1,
                           Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
    }

    @Override
    public void execute() {
        //TODO : Not Implemented

        throw new UnsupportedOperationException("Skip Instruction not implemented");
    }
}