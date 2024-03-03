package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class BranchLinkInstruction<Word extends Number> extends BranchInstruction<Word>{
    public BranchLinkInstruction(Processor<Word> processor,
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

        throw new UnsupportedOperationException("Branch Link Instruction not implemented");

        // First remember the return address
        // Then jump
        // Then link to return address
    }
}
