package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.exceptions.IllegalMemoryAccessException;

public class StoreFromRegisterInstruction<Word extends Number> extends BaseInstruction<Word> {
    public StoreFromRegisterInstruction(Processor<Word> processor,
                                        Word address,
                                        Word OPCode,
                                        Word rdest,
                                        Word option,
                                        Word rargs1,
                                        Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);

        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        if (!ar.isRBitSet(rargs1, 0)) {
            proc.error("First argument has to be a register.", this);
        }

    }

    @Override
    public void execute() {
        var proc = getProcessor();
        var mem = proc.getMemoryDevice();
        var ar = proc.getArithmeticWrapper();

        int source = ar.getRegisterID(getDestinationRegister());
        var targetAddress = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        var offset = ar.getValueOrRegisterValue(getSecondArgument(), proc);

        targetAddress = ar.add(targetAddress, offset);

        Word value = proc.getRegisterValue(source);

        try {
            mem.write(targetAddress, value, getAddress());
        } catch (IllegalMemoryAccessException e) {
            proc.error(e.getMessage(), this);
        }
    }
}
