package de.johndee.vpm.impl;

import de.johndee.vpm.core.Processor;
import de.johndee.vpm.instructions.BaseInstruction;
import de.johndee.vpm.instructions.Instruction;
import de.johndee.vpm.instructions.MoveInstruction;
import de.johndee.vpm.utils.ArithmeticWrapper;

public class ArithmeticWrapper64 implements ArithmeticWrapper<Long> {
    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long add(Long a, byte b) {
        return a + b;
    }

    @Override
    public Long sub(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long sub(Long a, byte b) {
        return a - b;
    }

    @Override
    public Long mul(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long mul(Long a, byte b) {
        return a * b;
    }

    @Override
    public Long div(Long a, Long b) {
        return a / b;
    }

    @Override
    public Long div(Long a, byte b) {
        return a / b;
    }

    @Override
    public Long and(Long a, Long b) {
        return a & b;
    }

    @Override
    public Long and(Long a, byte b) {
        return a & b;
    }

    @Override
    public Long or(Long a, Long b) {
        return a | b;
    }

    @Override
    public Long or(Long a, byte b) {
        return a | b;
    }

    @Override
    public Long xor(Long a, Long b) {
        return a ^ b;
    }

    @Override
    public Long xor(Long a, byte b) {
        return a ^ b;
    }

    @Override
    public Long not(Long a) {
        return ~a;
    }



    @Override
    public Long lshift(Long a, Long b) {
        return a << b;
    }

    @Override
    public Long lshift(Long a, byte b) {
        return a << b;
    }

    @Override
    public Long rshift(Long a, Long b) {
        return a >> b;
    }

    @Override
    public Long rshift(Long a, byte b) {
        return a >> b;
    }

    @Override
    public boolean gt(Long a, Long b) {
        return a > b;
    }

    @Override
    public boolean gt(Long a, byte b) {
        return a > b;
    }

    @Override
    public boolean lt(Long a, Long b) {
        return a < b;
    }

    @Override
    public boolean lt(Long a, byte b) {
        return a < b;
    }

    @Override
    public boolean eq(Long a, Long b) {
        return a.equals(b) ;
    }

    @Override
    public boolean eq(Long a, byte b) {
        return a == b;
    }

    @Override
    public boolean nq(Long a, Long b) {
        return !eq(a,b);
    }

    @Override
    public boolean nq(Long a, byte b) {
        return !eq(a,b);
    }

    @Override
    public boolean ge(Long a, Long b) {
        return a >= b;
    }

    @Override
    public boolean ge(Long a, byte b) {
        return a >= b;
    }

    @Override
    public boolean le(Long a, Long b) {
        return a <= b;
    }

    @Override
    public boolean le(Long a, byte b) {
        return a <= b;
    }

    @Override
    public float reinterpretAsFloat(Long a) {
        if (a > Integer.MAX_VALUE) {
            throw new UnsupportedOperationException("Value too large to convert to float: " + a);
        }

        return Float.intBitsToFloat((int) (long) a);
    }

    @Override
    public Long reinterpretAsWord(float a) {
        return (long) Float.floatToRawIntBits(a);
    }

    @Override
    public boolean isRBitSet(Long a, long bit) {
        long mask = 0b1;

        return ((a >> bit) & mask) == 1;
    }

    @Override
    public Long getValueOrRegisterValue(Long word, Processor<Long> processor) {

        // The actual value does not contain the bit which determines the source.
        Long value = rshift(word, 1L);

        //Check whether first bit is set.
        if (isRBitSet(word, 0)) {
            return processor.getRegisterValue(getRegisterID(value));
        } else {
            return value;
        }
    }

    @Override
    public int getRegisterID(Long word) {
        return word.intValue();
    }

    /**
     * We have 64-bit available for each instruction. The general layout we look for is:
     * ```
     * OPCODE (8) | OPTIONS (4) | RDEST (4) | ARG1 (23 + 1) | ARG2(23 + 1)
     * ```
     * @param instruction The instruction to convert to binary format.
     * @return The binary format of the instruction.
     */
    @Override
    public Long binaryInstructionFormat(Instruction<Long> instruction) {
        if (instruction instanceof BaseInstruction<Long>) {
            BaseInstruction<Long> baseInstruction = (BaseInstruction<Long>) instruction;
            Long opcode = baseInstruction.getOPCode();
            Long rdest = baseInstruction.getDestinationRegister();
            Long option = baseInstruction.getOption();
            Long rargs1 = baseInstruction.getFirstArgument();
            Long rargs2 = baseInstruction.getSecondArgument();

           Long result = 0L;
              result = result | (opcode << 56);
                result = result | (option << 52);
                result = result | (rdest << 48);
                result = result | (rargs1 << 24);
                result = result | (rargs2);

            return result;
        } else if (instruction instanceof MoveInstruction<Long>) {
            MoveInstruction<Long> moveInstruction = (MoveInstruction<Long>) instruction;
            Long opcode = moveInstruction.getOPCode();
            Long rdest = moveInstruction.getDestinationRegister();
            Long option = moveInstruction.getOptions();
            Long args = moveInstruction.getArgument();

            Long result = 0L;
            result = result | (opcode << 56);
            result = result | (option << 55);
            result = result | (rdest << 51);
            result = result | (args);

            return result;
        }
        
        
        else {
            throw new UnsupportedOperationException("Instruction not supported: " + instruction);
        }
    }
}
