package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public abstract class LogicalInstruction<Word extends Number> extends BaseInstruction<Word> {
    private Operator operator;
    public LogicalInstruction(Processor<Word> processor,
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
        //TODO : Not Implemented

        throw new UnsupportedOperationException("Logical Instruction not implemented");
    }

    public enum Operator {
        AND, OR, XOR, LSL, LSR
    }
}
