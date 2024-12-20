package de.johndee.maple.interpreter;

import de.johndee.maple.core.Processor;
import de.johndee.maple.instructions.*;

public class MapleInstructionParser {

    public static Instruction<Long> fromBinaryFormat(Processor<Long> processor, Long binaryFormat, Long address) {
        Instruction<Long> instruction = null;

        long opCode = getBaseOPCode(binaryFormat);

        if (opCode == 0) {
            return new NopInstruction<>(processor, address, opCode);
        }

        // Move and Move Not are following a different format
        if (opCode == 1) {
            return moveFromBinaryFormat(processor, binaryFormat, address, opCode);
        }

        //Currently most instructions are following the base format
        return baseFromBinaryFormat(processor, binaryFormat, address, opCode);
    }

    private static Instruction<Long> baseFromBinaryFormat(Processor<Long> processor, Long binaryFormat, Long address, long opCode) {

        // (Shorter names for readability)
        long opt = getBaseOptions(binaryFormat);
        long rd = getBaseDestinationRegister(binaryFormat);
        long a1 = getBaseArgument1(binaryFormat);
        long a2 = getBaseArgument2(binaryFormat);


        switch ((byte) opCode) {
            case (byte) MapleBinaryCodes.ADDI:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.ADD);
            case (byte) MapleBinaryCodes.SUBI:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.SUB);
            case (byte) MapleBinaryCodes.MULI:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.MUL);
            case (byte) MapleBinaryCodes.DIVI:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.DIV);
            case (byte) MapleBinaryCodes.ADDF:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.ADD);
            case (byte) MapleBinaryCodes.SUBF:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.SUB);
            case (byte) MapleBinaryCodes.MULF:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticInstruction.Operator.MUL);
            case (byte) MapleBinaryCodes.DIVF:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticInstruction.Operator.DIV);
            case (byte) MapleBinaryCodes.COND_SKIP:
                return new ConditionalSkipInstruction<>(processor, address, opCode);
            case (byte) MapleBinaryCodes.CMPI:
                return new CompareIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.CMPF:
                return new CompareFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.COMP_STORE_RESULT:
                return new GetCompareResultInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.COND_BRANCH:
                return new ConditionalBranchInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.BRANCH:
                return new BranchInstruction<>(processor, address, opCode, rd, opt, a1);
            case (byte) MapleBinaryCodes.BRANCH_LINK:
                return new BranchLinkInstruction<>(processor, address, opCode, rd, opt, a1);
            case (byte) MapleBinaryCodes.SHIFT:
                return new BitwiseShiftInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.AND_OR_XOR:
                return new BitwiseBinaryInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.LDR:
                return new LoadToRegisterInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.STR:
                return new StoreFromRegisterInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case (byte) MapleBinaryCodes.POP_PUSH:
                return new PopPushInstruction<>(processor, address, opCode, rd, opt);
            case (byte) MapleBinaryCodes.EXIT:
                return new ExitInstruction<>(processor, address, opCode);
            case (byte) MapleBinaryCodes.IO_READ_WRITE:
                return new IOInstruction<>(processor, address, opCode, rd, opt, a1, a2);

            default:
                throw new IllegalArgumentException("Unknown opcode: " + opCode);
        }
    }

    private static Instruction<Long> moveFromBinaryFormat(Processor<Long> processor, Long binaryFormat, Long address, long opCode) {

        long opt = getMoveOptions(binaryFormat);
        long rd = getMoveDestinationRegister(binaryFormat);
        long a1 = getMoveArgument(binaryFormat);

        switch ((byte) opCode) {
            case 0b00000001:
                return new MoveInstruction<Long>(processor, address, opCode, rd, opt, a1);
            default:
                throw new IllegalArgumentException("Unknown opcode: " + opCode);
        }
    }

    private static long getBaseOPCode(long binaryFormat) {
        long opMask = 0xFF00000000000000L;
        return (binaryFormat & opMask) >> 56;
    }

    private static long getBaseOptions(long binaryFormat) {
        long opMask = 0x00F0000000000000L;
        return (binaryFormat & opMask) >> 52;
    }

    private static long getMoveOptions(long binaryFormat) {
        long opMask = 0b0000000010000000000000000000000000000000000000000000000000000000L;
        return (binaryFormat & opMask) >> 55;
    }

    private static long getBaseDestinationRegister(long binaryFormat) {
        long opMask = 0x000F000000000000L;
        return (binaryFormat & opMask) >> 48;
    }

    private static long getMoveDestinationRegister(long binaryFormat) {
        long opMask = 0b0000000001111000000000000000000000000000000000000000000000000000L;
        return (binaryFormat & opMask) >> 51;
    }

    private static long getBaseArgument1(long binaryFormat) {
        long opMask = 0x0000FFFFFF000000L;
        return (binaryFormat & opMask) >> 24;
    }

    private static long getMoveArgument(long binaryFormat) {
        long opMask = 0b0000000000000111111111111111111111111111111111111111111111111111L;
        return (binaryFormat & opMask);
    }

    private static long getBaseArgument2(long binaryFormat) {
        long opMask = 0x0000000000FFFFFFL;
        return (binaryFormat & opMask);
    }



}
