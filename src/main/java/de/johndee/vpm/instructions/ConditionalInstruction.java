package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public abstract class ConditionalInstruction<Word extends Number> extends BaseInstruction<Word> {

    private Condition condition;

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

    public Condition getCondition() {
        return condition;
    }

    public enum Condition {
        EQ, NQ, LT, LE, GT, GE
    }
}
