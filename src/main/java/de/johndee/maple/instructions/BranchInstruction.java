package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class BranchInstruction<Word extends Number> extends BaseInstruction<Word>{
    public BranchInstruction(Processor<Word> processor,
                             Word address,
                             Word OPCode,
                             Word rdest,
                             Word option,
                             Word roffset) {
        super(processor, address, OPCode, rdest, option, roffset, processor.getArithmeticWrapper().fromInt(0));
    }

    @Override
    public void execute() {

        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        Word offset = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word rdest = getDestinationRegister(); //The register that holds the address to jump to

        Word value = proc.getRegisterValue(rdest.intValue());

        Word pc = ar.add(value, offset);
        pc = ar.sub(pc, (byte) 1); // -1 as the PC is going to increase by 1 after this instruction
        proc.setProgramCounter(pc);
    }
}
