package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class SkipInstruction<Word extends Number> extends ConditionalInstruction<Word>{
    public SkipInstruction(Processor<Word> processor,
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
        boolean result = checkCondition();
        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();


        Word pc = proc.getProgramCounter();

        if (result) {
            pc = ar.add(pc, (byte) 1); //Skipping the next instruction
            proc.setProgramCounter(pc);
        }

    }
}
