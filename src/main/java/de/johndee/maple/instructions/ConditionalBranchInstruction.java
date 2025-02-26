package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class ConditionalBranchInstruction<Word extends Number> extends ConditionalInstruction<Word> {
    public ConditionalBranchInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);
    }

    @Override
    public void execute() {

        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        if (!this.checkCondition()) return;

        Word offset = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word rdest = getDestinationRegister(); //The register that holds the address to jump to

        Word value = proc.getRegisterValue(rdest.intValue());

        Word pc = ar.add(value, offset);
        pc = ar.sub(pc, (byte) 1); // -1 as the PC is going to increase by 1 after this instruction
        proc.setProgramCounter(pc);
    }
}
