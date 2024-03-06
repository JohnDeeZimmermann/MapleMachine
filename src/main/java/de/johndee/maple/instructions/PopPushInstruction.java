package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.utils.StackUtils;

import java.util.Stack;

public class PopPushInstruction<Word extends Number> extends BaseInstruction<Word> {

    public static final long OPTION_POP = 0L;
    public static final long OPTION_PUSH = 1L;

    private final Type type;
    public PopPushInstruction(Processor<Word> processor,
                              Word address,
                              Word OPCode,
                              Word rdest,
                              Word option) {

        super(processor, address, OPCode, rdest, option,
                processor.getArithmeticWrapper().fromInt(0),
                processor.getArithmeticWrapper().fromInt(0)
        );

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

        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        var register = ar.getRegisterID(getDestinationRegister());

        if (type == Type.POP) {
            Word value = StackUtils.pop(proc, this);
            proc.setRegisterValue(register, value);
        }
        else if (type == Type.PUSH) {
            Word value = proc.getRegisterValue(register);
            StackUtils.push(proc, value, this);
        }
    }

    public enum Type {
        POP, PUSH
    }
}
