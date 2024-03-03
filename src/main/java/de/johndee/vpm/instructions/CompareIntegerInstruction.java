package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

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

        Word arg1 = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word arg2 = ar.getValueOrRegisterValue(getSecondArgument(), proc);
        Word result = ar.sub(arg1, arg2);

        ar.handleCompareRegisterOperationResult(arg1, arg2, result, proc);
    }
}
