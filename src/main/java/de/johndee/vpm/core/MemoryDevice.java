package de.johndee.vpm.core;

import de.johndee.vpm.exceptions.IllegalMemoryAccessException;

public interface MemoryDevice<Word extends Number> {
    /**
     * Read a single word from the memory device.
     * @param address The address to read from.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @return The word read from the memory device.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */
    Word read(Word address, Word rf) throws IllegalMemoryAccessException;

    /**
     * Read a range of words from the memory device.
     * @param address The address to read from.
     * @param length The number of words to read.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @return The words read from the memory device.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */
    Word[] read(Word address, Word length, Word rf) throws IllegalMemoryAccessException;

    /**
     * Write a single word to the memory device.
     * @param address The address to write to.
     * @param value The value to write.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */
    void write(Word address, Word value, Word rf) throws IllegalMemoryAccessException;

    /**
     * Write a range of words to the memory device.
     * @param address The address to write to.
     * @param values The values to write.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */
    void write(Word address, Word[] values, Word rf) throws IllegalMemoryAccessException;

    /**
     * Get the memory regions marked as 'Controlled Access Regions'.
     * @return The memory regions marked as 'Controlled Access Regions'.
     */
    MemoryRegion<Word>[] getCARs();

    /**
     * Get the memory region used as the stack region.
     * @return The memory region used as the stack region.
     */
    MemoryRegion<Word> getStackRegion();

    /**
     * Register a memory region as a 'Controlled Access Region'.
     * @param region The memory region to register.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */

    void registerCAR(MemoryRegion<Word> region, Word rf) throws IllegalMemoryAccessException;

    /**
     * Unregister a memory region as a 'Controlled Access Region'.
     * @param region The memory region to unregister.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */
    void unregisterCAR(MemoryRegion<Word> region, Word rf) throws IllegalMemoryAccessException;
    /**
     * Set the memory region to be used as the stack region.
     * @param region The memory region to use as the stack region.
     * @param rf The code address of the caller. This is used to check for memory access violations.
     * @throws IllegalMemoryAccessException If the memory access is illegal.
     */
    void setStackRegion(MemoryRegion<Word> region, Word rf) throws IllegalMemoryAccessException;

}
