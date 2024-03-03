package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class CompareFloatInstruction<Word extends Number> extends CompareInstruction<Word>{
    public CompareFloatInstruction(Processor<Word> processor,
                                   Word address,
                                   Word OPCode,
                                   Word rdest,
                                   Word option,
                                   Word rargs1,
                                   Word rargs2) {

        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
    }

    @Override
    public void execute() {
        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        Word arg1 = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        float arg1f = ar.reinterpretAsFloat(arg1);
        Word arg2 = ar.getValueOrRegisterValue(getSecondArgument(), proc);
        float arg2f = ar.reinterpretAsFloat(arg2);
        float result = arg1f - arg2f;
        ar.handleCompareRegisterOperationResult(arg1f, arg2f, result, proc);
    }
}
