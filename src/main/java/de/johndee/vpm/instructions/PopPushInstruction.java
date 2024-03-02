package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class PopPushInstruction<Word extends Number> extends BaseInstruction<Word> {
    private final Type type;
    public PopPushInstruction(Processor<Word> processor,
                              Word address,
                              Word OPCode,
                              Word rdest,
                              Word option,
                              Word rargs1,
                              Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);

        if (option.intValue() == 0) {
            type = Type.POP;
        } else if (option.intValue() == 1) {
            type = Type.PUSH;
        } else {
            throw new IllegalArgumentException("Invalid options for PopPushInstruction");
        }
    }

    @Override
    public void execute() {

    }

    public enum Type {
        POP, PUSH
    }
}
