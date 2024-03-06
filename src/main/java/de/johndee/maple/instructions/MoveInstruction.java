package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class MoveInstruction<Word extends Number> extends Instruction<Word>{

    public final static long OPTION_MOV = 0L;
    public final static long OPTION_MVN = 1L;

    private boolean not = false;

    private final Word rdest;
    private final Word option;
    private final Word rargs1;

    public MoveInstruction(Processor<Word> processor,
                           Word address,
                           Word OPCode,
                           Word rdest,
                           Word option,
                           Word rargs1) {
        super(processor, address, OPCode);

        this.rdest = rdest;
        this.option = option;
        this.rargs1 = rargs1;

        var ar = processor.getArithmeticWrapper();

        this.not = ar.eq(option, ar.fromInt(1));
    }

    @Override
    public void execute() {
        var proc = getProcessor();
        var mem = proc.getMemoryDevice();
        var ar = proc.getArithmeticWrapper();

        Word dest = getDestinationRegister();
        Word value = ar.getValueOrRegisterValue(getArgument(), proc);
        if (not) value = ar.not(value);

        proc.setRegisterValue(ar.getRegisterID(dest), value);
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
