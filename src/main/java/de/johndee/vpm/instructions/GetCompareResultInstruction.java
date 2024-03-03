package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class GetCompareResultInstruction<Word extends Number> extends ConditionalInstruction<Word>{
    public GetCompareResultInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
    }

    @Override
    public void execute() {
        var result = checkCondition();

        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();
        var register = getDestinationRegister();

        if (result) {
            proc.setRegisterValue(ar.getRegisterID(register), ar.fromInt(1));
        } else {
            proc.setRegisterValue(ar.getRegisterID(register), ar.fromInt(0));
        }
    }
}
