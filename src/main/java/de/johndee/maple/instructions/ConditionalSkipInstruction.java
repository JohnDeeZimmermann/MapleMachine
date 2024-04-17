package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class ConditionalSkipInstruction<Word extends Number> extends ConditionalInstruction<Word>{
    public ConditionalSkipInstruction(Processor<Word> processor, Word address, Word OPCode) {
        super(processor,
                address,
                OPCode,
                processor.getArithmeticWrapper().fromInt(0),
                processor.getArithmeticWrapper().fromInt(0),
                processor.getArithmeticWrapper().fromInt(0),
                processor.getArithmeticWrapper().fromInt(0));
    }

    @Override
    public void execute() {

        var ar = getProcessor().getArithmeticWrapper();
        boolean cond = this.checkCondition();

        if (!cond) return;

        Word pc = getProcessor().getProgramCounter();
        pc = ar.add(pc, (byte) 1); // Skip the next instruction

        getProcessor().setProgramCounter(pc);
    }
}
