package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.exceptions.IllegalIOAccessException;
import de.johndee.maple.exceptions.IllegalMemoryAccessException;

public class IOInstruction<Word extends Number> extends BaseInstruction<Word>{

    private IOMode mode;

    public IOInstruction(Processor<Word> processor, Word address, Word OPCode, Word rdest, Word option, Word rargs1, Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2);

        var ar = processor.getArithmeticWrapper();

        if (ar.eq(option, ar.fromInt(0))) {
            mode = IOMode.READ;
        } else if (ar.eq(option, ar.fromInt(1))) {
            mode = IOMode.WRITE;
        } else {
            mode = IOMode.UNDEFINED;
        }
    }


    @Override
    public void execute() {
        if (mode == IOMode.UNDEFINED)
            throw new UnsupportedOperationException("IO Instruction not set correctly.");

        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();
        var device = proc.getSelectedIODevice();
        var memory = proc.getMemoryDevice();

        if (device.isProtected() && memory.isAddressInCAR(getAddress())) {
            throw new IllegalIOAccessException(this, device);
        }

        Word destAddr = getProcessor().getRegisterValue(
                ar.getRegisterID(getDestinationRegister())
        );
        Word srcAddr = ar.getValueOrRegisterValue(getFirstArgument(), proc);
        Word length = getSecondArgument();

        //      Reading
        Word[] data;

        try {
            data = (mode == IOMode.READ)
                    ? device.read(srcAddr, length)
                    : memory.read(srcAddr, length, getAddress());
        } catch (IllegalMemoryAccessException e) {
            throw new RuntimeException(e);
        }

        //      Writing

        // From IO to memory
        if (mode == IOMode.READ) {
            try {
                memory.write(destAddr, data, getAddress());
            } catch (IllegalMemoryAccessException e) {
                throw new RuntimeException(e);
            }
        }
        //From memory to IO
        else if (mode == IOMode.WRITE) {
            device.write(destAddr, data);
        }
    }


    private enum IOMode {
        READ,
        WRITE,
        UNDEFINED;
    }

}
