package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public abstract class ArithmeticInstruction<Word extends Number> extends BaseInstruction<Word> {

    protected final Operator operator;

    public ArithmeticInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2, Operator op) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
        this.operator = op;
    }

    public enum Operator {
        ADD, SUB, MUL, DIV
    }
}
