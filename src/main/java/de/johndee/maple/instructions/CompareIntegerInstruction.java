package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class CompareIntegerInstruction<Word extends Number> extends CompareInstruction<Word>{
    public CompareIntegerInstruction(Processor<Word> processor,
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

        Word argReg = proc.getRegisterValue(ar.getRegisterID(getDestinationRegister()));
        Word argCmp = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word result = ar.sub(argReg, argCmp);

        ar.handleCompareRegisterOperationResult(argReg, argCmp, result, proc);
    }
}
