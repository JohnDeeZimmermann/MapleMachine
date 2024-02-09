package de.johndee.vpm.core;

import java.util.List;

/**
 * Base interface for Processors. Maybe we'll want to add different derived interfaces for different types of processors.
 */
public interface Processor<Word extends Number> {
    void setRegisterValue(int registerID, Word value);
    void registerIODevice(IODevice<Word> ioDevice);

    Word run(Word startAddress);
    Word getRegisterValue(int registerID);
    MemoryDevice<Word> getMemoryDevice();
    List<IODevice<Word>> getIODevice();

}
