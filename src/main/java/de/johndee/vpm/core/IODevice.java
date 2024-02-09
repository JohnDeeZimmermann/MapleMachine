package de.johndee.vpm.core;

public interface IODevice<Word extends Number> {

    /**
     * Read a single word from the memory device.
     * @param address The address to read from.
     * @return The word read from the memory device.
     */
    Word read(Word address);

    /**
     * Read a range of words from the memory device.
     * @param address The address to read from.
     * @param length The number of words to read.
     * @return The words read from the memory device.
     */
    Word[] read(Word address, Word length);

    /**
     * Write a single word to the I/O device.
     * @param address The address to write to.
     * @param value The value to write.
     */
    void write(Word address, Word value);

    /**
     * Write a range of words to the I/O device.
     * @param address The address to write to.
     * @param values The values to write.
     */
    void write(Word address, Word[] values);
}
