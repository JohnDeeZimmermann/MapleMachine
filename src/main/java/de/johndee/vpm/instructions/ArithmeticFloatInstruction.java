package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class ArithmeticFloatInstruction<Word extends Number> extends ArithmeticInstruction<Word> {
    public ArithmeticFloatInstruction(Processor<Word> processor,
                                      Word address,
                                      Word OPCode,
                                      Word rdest,
                                      Word option,
                                      Word rargs1,
                                      Word rargs2,
                                      Operator op) {

        super(processor, address, OPCode, rdest, option, rargs1, rargs2, op);
    }

    @Override
    public void execute() {
        //TODO : Not Implemented

        throw new UnsupportedOperationException("Arithmetic Float Instruction not implemented");
    }
}
