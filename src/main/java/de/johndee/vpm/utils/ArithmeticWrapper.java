package de.johndee.vpm.utils;

import de.johndee.vpm.core.Processor;
import de.johndee.vpm.instructions.Instruction;

/**
 * Wrapper for arithmetic operations.
 * Used to help deal with different implementations of number types.
 *
 */
public interface ArithmeticWrapper<Word extends Number> {

    Word add(Word a, Word b);
    Word sub(Word a, Word b);
    Word mul(Word a, Word b);
    Word div(Word a, Word b);

    Word and(Word a, Word b);
    Word or(Word a, Word b);
    Word xor(Word a, Word b);
    Word not(Word a);
    Word lshift(Word a, Word b);
    Word rshift(Word a, Word b);

    boolean gt(Word a, Word b);
    boolean gt(Word a, byte b);

    boolean lt(Word a, Word b);
    boolean lt(Word a, byte b);

    boolean eq(Word a, Word b);
    boolean eq(Word a, byte b);

    boolean nq(Word a, Word b);
    boolean nq(Word a, byte b);

    boolean ge(Word a, Word b);
    boolean ge(Word a, byte b);

    boolean le(Word a, Word b);
    boolean le(Word a, byte b);

    /**
     * Check if a bit is set in a word. Counting from the right.
     * @param a The word to check against
     * @param bit The bit to check, counting from the right.
     * @return True if the bit is set, false otherwise.
     */
    boolean isRBitSet(Word a, long bit);

    /**
     * Returns the value of a register or a value depending on the layout of the word.
     * @param word The word to check
     * @param processor The processor to get the value from if read from register
     * @return The value of the register or the value of the word.
     */
    Word getValueOrRegisterValue(Word word, Processor<Word> processor);

    int getRegisterID(Word word);


    Word binaryInstructionFormat(Instruction<Word> instruction);

}
