package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class ArithmeticIntegerInstruction<Word extends Number> extends ArithmeticInstruction<Word> {
    public ArithmeticIntegerInstruction(Processor<Word> processor,
                                        Word address,
                                        Word OPCode,
                                        Word rdest,
                                        Word option,
                                        Word rargs1,
                                        Word rargs2,
                                        Operator op) {

        super(processor, address, OPCode, rdest, option, rargs1, rargs2, op);
    }

    @Override
    public void execute() {
        var processor = getProcessor();
        var ar = processor.getArithmeticWrapper();

        Word result;
        Word arg1 = ar.getValueOrRegisterValue(getFirstArgument(), processor);
        Word arg2 = ar.getValueOrRegisterValue(getSecondArgument(), processor);

        int registerID = ar.getRegisterID(getDestinationRegister());

        switch (operator) {
            case ADD:
                result = ar.add(arg1, arg2);
                break;
            case SUB:
                result = ar.sub(arg1, arg2);
                break;
            case MUL:
                result = ar.mul(arg1, arg2);
                break;
            case DIV:
                result = ar.div(arg1, arg2);
                break;
            default:
                throw new UnsupportedOperationException("Operator not supported: " + operator);
        }

        processor.setRegisterValue(registerID, result);
        ar.handleCompareRegisterOperationResult(arg1, arg2, result, processor);
    }
}
