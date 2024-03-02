package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class MoveInstruction<Word extends Number> extends Instruction<Word>{
    private boolean not = false;

    private final Word rdest;
    private final Word option;
    private final Word rargs1;

    public MoveInstruction(Processor<Word> processor,
                           Word address,
                           Word OPCode,
                           Word rdest,
                           Word option,
                           Word rargs1,
                           boolean not) {
        super(processor, address, OPCode);

        this.rdest = rdest;
        this.option = option;
        this.rargs1 = rargs1;

        this.not = not;
    }

    @Override
    public void execute() {
        //TODO : Not Implemented

        throw new UnsupportedOperationException("Move Instruction not implemented");
    }

    public Word getDestinationRegister() {
        return rdest;
    }

    public Word getOptions() {
        return option;
    }

    public Word getArgument() {
        return rargs1;
    }
}
