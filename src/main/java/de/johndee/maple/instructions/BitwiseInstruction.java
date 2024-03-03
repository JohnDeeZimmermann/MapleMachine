package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public abstract class BitwiseInstruction<Word extends Number> extends BaseInstruction<Word> {
    private Operator operator;
    public BitwiseInstruction(Processor<Word> processor,
                              Word address,
                              Word OPCode,
                              Word rdest,
                              Word option,
                              Word rargs1,
                              Word rargs2,
                              Operator operator) {

        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
        this.operator = operator;
    }

    @Override
    public void execute() {
        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        Word destination = getDestinationRegister();
        Word a = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word b = ar.getValueOrRegisterValue(getSecondArgument(), proc);
        Word result =
        switch (operator) {
            case OR -> ar.or(a,b);
            case AND -> ar.and(a,b);
            case XOR -> ar.xor(a,b);
            case LSL -> ar.lshift(a,b);
            case LSR -> ar.rshift(a,b);
            default -> {
                throw new UnsupportedOperationException("Operator " + operator + " not supported");
            }
        };

        proc.setRegisterValue(ar.getRegisterID(destination), result);

    }

    public enum Operator {
        AND, OR, XOR, LSL, LSR
    }
}
