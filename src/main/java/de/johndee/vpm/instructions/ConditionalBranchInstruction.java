package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class ConditionalBranchInstruction<Word extends Number> extends ConditionalInstruction<Word> {
    public ConditionalBranchInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
    }

    @Override
    public void execute() {
        //TODO : Not Implemented

        throw new UnsupportedOperationException("Conditional Branch Instruction not implemented");
    }
}
