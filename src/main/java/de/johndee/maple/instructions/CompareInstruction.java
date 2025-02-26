package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public abstract class CompareInstruction<Word extends Number> extends BaseInstruction<Word> {
    public CompareInstruction(Processor<Word> processor,
                              Word address,
                              Word OPCode,
                              Word rdest,
                              Word option,
                              Word rargs1,
                              Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);

        if (processor.getArithmeticWrapper().nq(rargs2, (byte) 0)) {
            throw new IllegalArgumentException("The second argument cannot be used");
        }

    }
}
