package de.johndee.maple.core;

import de.johndee.maple.instructions.Instruction;
import de.johndee.maple.utils.ArithmeticWrapper;

import java.util.List;

/**
 * Base interface for Processors. Maybe we'll want to add different derived interfaces for different types of processors.
 */
public interface Processor<Word extends Number> {
    void setRegisterValue(int registerID, Word value);
    void registerIODevice(IODevice<Word> ioDevice);
    void setProgramCounter(Word value);
    void setStackPointer(Word value);
    void setDynamicLink(Word value);
    void setCompareResultRegister(Word value);
    void setFramePointer(Word value);
    void setProgramStart(Word value);
    void setProgramLength(Word value);
    void setIOPointer(Word value);
    void setHardwareRegister(int index, Word value);
    void setReturnRegister(Word value);

    /**
     * Asks the processor to terminate (stop)
     */
    void requestTermination();

    void addOutputLogger(OutputLogger<Word> logger);
    void addErrorLogger(OutputLogger<Word> logger);

    void log(String message, Instruction<Word> source);
    void error(String errorMessage, Instruction<Word> source);


    /**
     * Make a single step from the current program counter.
     * @return The value of the program counter after the processor has stopped.
     */
    Word step();

    /**
     * Runs the processors until a termination flag is set.
     * @param startAddress The address of the first address. From there, the processor will continue sequentially
     * @return The last program counter
     */
    Word run(Word startAddress);
    Word getRegisterValue(int registerID);
    Word getProgramCounter();
    Word getStackPointer();

    /**
     * The value of our dynamic link register.
     * This usually gets set to the memory location after the function call
     * @return
     */
    Word getDynamicLink();
    Word getCompareResultRegister();

    /**
     * The frame pointer register.
     * This usually points to the start of the usable memory block.
     * @return
     */
    Word getFramePointer();
    Word getProgramStart();
    Word getProgramLength();
    Word getIOPointer();
    Word getHardwareRegister(int index);

    /**
     * The register, we store our return values in.
     * Usually r0.
     * @return The value of the register, we store our return values in.
     */
    Word getReturnRegister();


    MemoryDevice<Word> getMemoryDevice();
    List<IODevice<Word>> getIODevice();

    ArithmeticWrapper<Word> getArithmeticWrapper();

}
