package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class ExitInstruction<Word extends Number> extends BaseInstruction<Word> {
    public ExitInstruction(Processor<Word> processor, Word address, Word OPCode) {
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
        if (getProcessor().getMemoryDevice().isAddressInCAR(getAddress()))
            return;

        getProcessor().requestTermination();
    }
}
