package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class BranchInstruction<Word extends Number> extends BaseInstruction<Word>{
    public BranchInstruction(Processor<Word> processor,
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

        Word offset = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word dest = getDestinationRegister(); //The register that holds the address to jump to
        Word pc = proc.getProgramCounter();

        Word newPC = ar.sub(ar.add(pc, offset), (byte) 1); // -1 as the PC is going to increase by 1 after this instruction
        proc.setProgramCounter(newPC);

    }
}
