package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

/**
 * Instruction that uses the base scheme defined in the BinaryLayout documentation.
 */
public abstract class BaseInstruction<Word extends Number> extends Instruction<Word> {

    private Word rdest;
    private Word option;
    private Word rargs1;
    private Word rargs2;

    public BaseInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2) {
        super(processor, address, OPCode);
        this.rdest = rdest;
        this.option = option;
        this.rargs1 = rargs1;
        this.rargs2 = rargs2;
    }

    public Word getDestinationRegister() {
        return rdest;
    }

    public Word getOption() {
        return option;
    }

    public Word getFirstArgument() {
        return rargs1;
    }

    public Word getSecondArgument() {
        return rargs2;
    }
}
