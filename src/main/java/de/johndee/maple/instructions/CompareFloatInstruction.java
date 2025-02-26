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

        Word argReg = proc.getRegisterValue(ar.getRegisterID(getDestinationRegister()));
        float argRegF = ar.reinterpretAsFloat(argReg);
        Word argCmp = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        float argCmpF = ar.reinterpretAsFloat(argCmp);

        float result = argRegF - argCmpF;
        ar.handleCompareRegisterOperationResult(argRegF, argCmpF, result, proc);
    }
}
