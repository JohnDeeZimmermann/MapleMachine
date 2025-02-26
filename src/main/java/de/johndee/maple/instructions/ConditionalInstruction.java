package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public abstract class ConditionalInstruction<Word extends Number> extends BaseInstruction<Word> {

    public static final long OPTION_BASE_EQ = 0b0000L;
    public static final long OPTION_BASE_NQ = 0b0010L;
    public static final long OPTION_BASE_LT = 0b0100L;
    public static final long OPTION_BASE_LE = 0b0110L;
    public static final long OPTION_BASE_GT = 0b1000L;
    public static final long OPTION_BASE_GE = 0b1010L;

    private final Condition condition;

    public ConditionalInstruction(Processor<Word> processor,
                                  Word address,
                                  Word OPCode,
                                  Word rdest,
                                  Word option,
                                  Word rargs1,
                                  Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);

        //Using the 3 bits to the left of the first of option to determine the condition.
        var ar = processor.getArithmeticWrapper();
        Word condValue = ar.rshift(option, (byte) 1);

        if (ar.eq(condValue, (byte) 0)) {
            condition = Condition.EQ;
        } else if (ar.eq(condValue, (byte) 1)) {
            condition = Condition.NQ;
        } else if (ar.eq(condValue, (byte) 2)) {
            condition = Condition.LT;
        } else if (ar.eq(condValue, (byte) 3)) {
            condition = Condition.LE;
        } else if (ar.eq(condValue, (byte) 4)) {
            condition = Condition.GT;
        } else if (ar.eq(condValue, (byte) 5)) {
            condition = Condition.GE;
        } else {
            throw new IllegalArgumentException("Invalid condition value");
        }
    }

    public boolean checkCondition() {
        var ar = getProcessor().getArithmeticWrapper();
        var cr = ar.getCRHandler(getProcessor());

        int zero = cr.getZero().intValue();

        return switch (condition) {
            case EQ -> cr.getZero().intValue() == 1;
            case NQ -> cr.getZero().intValue() == 0;
            case LT -> cr.getNegative().intValue() == 1;
            case LE -> cr.getNegative().intValue() == 1 || cr.getZero().intValue() == 1;
            case GT -> cr.getNegative().intValue() == 0 && cr.getZero().intValue() == 0;
            case GE -> cr.getNegative().intValue() == 0;
            default -> throw new UnsupportedOperationException("Condition not supported: " + condition);
        };

    }

    public Condition getCondition() {
        return condition;
    }

    public enum Condition {
        EQ, NQ, LT, LE, GT, GE
    }
}
