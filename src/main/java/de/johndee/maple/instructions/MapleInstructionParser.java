package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class MapleInstructionParser {

    public static Instruction<Long> fromBinaryFormat(Processor<Long> processor, Long binaryFormat, Long address) {
        Instruction<Long> instruction = null;

        long opCode = getBaseOPCode(binaryFormat);

        // Move and Move Not are following a different format
        if (opCode == 0 || opCode == 1) {
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
            case 0b00000010:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.ADD);
            case 0b00000011:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.SUB);
            case 0b00000100:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.MUL);
            case 0b00000101:
                return new ArithmeticIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.DIV);
            case 0b00000110:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.ADD);
            case 0b00000111:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticIntegerInstruction.Operator.SUB);
            case 0b00001000:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticInstruction.Operator.MUL);
            case 0b00001001:
                return new ArithmeticFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2,
                        ArithmeticInstruction.Operator.DIV);
            case 0b00001010:
                return new SkipInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00001011:
                return new CompareIntegerInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00001100:
                return new CompareFloatInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00001101:
                return new GetCompareResultInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00001110:
                return new ConditionalBranchInstruction<Long>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00001111:
                return new BranchInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00010000:
                return new BranchLinkInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00010001:
                return new LogicalShiftInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00010010:
                return new LogicalBinaryInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00010011:
                return new LoadToRegisterInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00010100:
                return new StoreFromRegisterInstruction<>(processor, address, opCode, rd, opt, a1, a2);
            case 0b00010101:
                return new PopPushInstruction<>(processor, address, opCode, rd, opt, a1, a2);

            default:
                throw new IllegalArgumentException("Unknown opcode: " + opCode);
        }
    }

    private static Instruction<Long> moveFromBinaryFormat(Processor<Long> processor, Long binaryFormat, Long address, long opCode) {

        long opt = getMoveOptions(binaryFormat);
        long rd = getMoveDestinationRegister(binaryFormat);
        long a1 = getMoveArgument(binaryFormat);

        switch ((byte) opCode) {
            case 0b00000000:
                return new MoveInstruction<Long>(processor, address, opCode, rd, opt, a1, false);
            case 0b00000001:
                return new MoveInstruction<Long>(processor, address, opCode, rd, opt, a1, true);
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
