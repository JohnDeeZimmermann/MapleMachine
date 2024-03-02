package de.johndee.vpm.core;

import de.johndee.vpm.utils.ArithmeticWrapper;

import java.util.List;

/**
 * Base interface for Processors. Maybe we'll want to add different derived interfaces for different types of processors.
 */
public interface Processor<Word extends Number> {
    void setRegisterValue(int registerID, Word value);
    void registerIODevice(IODevice<Word> ioDevice);
    void setProgramCounter(Word value);

    /**
     * Make a single step from the current program counter.
     * @return The value of the program counter after the processor has stopped.
     */
    Word step();
    Word run(Word startAddress);
    Word getRegisterValue(int registerID);
    Word getProgramCounter();
    MemoryDevice<Word> getMemoryDevice();
    List<IODevice<Word>> getIODevice();

    ArithmeticWrapper<Word> getArithmeticWrapper();

}
