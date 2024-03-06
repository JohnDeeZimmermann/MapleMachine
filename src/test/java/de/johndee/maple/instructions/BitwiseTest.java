package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitwiseTest {

    private Processor<Long> processor;
    @Before
    public void init() {
        processor = new Maple64();
    }

    @Test
    public void testAnd() {
        long a = 0b1110;
        long b = 0b1101;

        processor.setRegisterValue(1, a);
        processor.setRegisterValue(2, b);

        BitwiseBinaryInstruction<Long> instruction = new BitwiseBinaryInstruction<>(
                processor,
                0L,
                OPCodes.AND_OR_XOR,
                0L,
                BitwiseBinaryInstruction.OPTION_AND,
                0b11L,
                0b101L
        );

        instruction.execute();
        long result = processor.getRegisterValue(0);

        assertEquals(a & b, result);
    }

    @Test
    public void testOr() {
        long a = 0b1110;
        long b = 0b1101;

        processor.setRegisterValue(1, a);
        processor.setRegisterValue(2, b);

        BitwiseBinaryInstruction<Long> instruction = new BitwiseBinaryInstruction<>(
                processor,
                0L,
                OPCodes.AND_OR_XOR,
                0L,
                BitwiseBinaryInstruction.OPTION_OR,
                0b11L,
                0b101L
        );

        instruction.execute();
        long result = processor.getRegisterValue(0);

        assertEquals(a | b, result);
    }


    @Test
    public void testXor() {
        long a = 0b1110;
        long b = 0b1101;

        processor.setRegisterValue(1, a);
        processor.setRegisterValue(2, b);

        BitwiseBinaryInstruction<Long> instruction = new BitwiseBinaryInstruction<>(
                processor,
                0L,
                OPCodes.AND_OR_XOR,
                0L,
                BitwiseBinaryInstruction.OPTION_XOR,
                0b11L,
                0b101L
        );

        instruction.execute();
        long result = processor.getRegisterValue(0);

        assertEquals(a ^ b, result);
    }

    @Test
    public void testLShift() {
        long a = 0b1110;
        long b = 4L;

        processor.setRegisterValue(1, a);
        processor.setRegisterValue(2, b);

        Instruction<Long> instruction = new BitwiseShiftInstruction<>(
                processor,
                0L,
                OPCodes.SHIFT,
                0L,
                BitwiseShiftInstruction.OPTION_LSL,
                0b11L,
                0b101L
        );

        instruction.execute();
        long result = processor.getRegisterValue(0);

        assertEquals(a << b, result);
    }

    @Test
    public void testRShift() {
        long a = 0b1110;
        long b = 1L;

        processor.setRegisterValue(1, a);
        processor.setRegisterValue(2, b);

        Instruction<Long> instruction = new BitwiseShiftInstruction<>(
                processor,
                0L,
                OPCodes.SHIFT,
                0L,
                BitwiseShiftInstruction.OPTION_LSR,
                0b11L,
                0b101L
        );

        instruction.execute();
        long result = processor.getRegisterValue(0);

        assertEquals(a >> b, result);
    }

}
