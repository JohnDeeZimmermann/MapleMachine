package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class ArithmeticFloatInstruction<Word extends Number> extends ArithmeticInstruction<Word> {
    public ArithmeticFloatInstruction(Processor<Word> processor,
                                      Word address,
                                      Word OPCode,
                                      Word rdest,
                                      Word option,
                                      Word rargs1,
                                      Word rargs2,
                                      Operator op) {

        super(processor, address, OPCode, rdest, option, rargs1, rargs2, op);

        var ar = processor.getArithmeticWrapper();
        if (!ar.isRBitSet(rargs1, 0)) {
            throw new IllegalArgumentException("First argument is not a register");
        }
        if (!ar.isRBitSet(rargs2, 0)) {
            throw new IllegalArgumentException("Second argument is not a register");
        }
    }

    @Override
    public void execute() {
        var processor = getProcessor();
        var ar = processor.getArithmeticWrapper();

        var dest = getDestinationRegister();
        var args1 = getFirstArgument();
        var args2 = getSecondArgument();

        Word v1 = ar.getValueOrRegisterValue(args1, processor);
        Word v2 = ar.getValueOrRegisterValue(args2, processor);

        float f1 = ar.reinterpretAsFloat(v1);
        float f2 = ar.reinterpretAsFloat(v2);
        float result = switch (operator) {
            case ADD -> f1 + f2;
            case SUB -> f1 - f2;
            case MUL -> f1 * f2;
            case DIV -> f1 / f2;
            default -> {
                throw new UnsupportedOperationException("Wrong operator " + operator.name());
            }
        };

        Word asWord = ar.reinterpretAsWord(result);

        processor.setRegisterValue(ar.getRegisterID(dest), asWord);
    }
}
