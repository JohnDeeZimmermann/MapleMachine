package de.johndee.tests.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import de.johndee.maple.instructions.MoveInstruction;
import de.johndee.maple.instructions.OPCodes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveTest {

    private Processor<Long> processor;
    @Before
    public void init() {
        processor = new Maple64();
    }

    @Test
    public void testMoveDirect() {
        MoveInstruction<Long> instruction = new MoveInstruction<Long>(
                processor,
                0b0L,
                OPCodes.MOV_MVN,
                0b1L,
                MoveInstruction.OPTION_MOV,
                0b10000L //8
        );

        instruction.execute();

        var result = processor.getRegisterValue(1);

        assertEquals(8L, (long) result);
    }

    @Test
    public void testMoveFromRegister() {
        processor.setRegisterValue(2, 10L);

        MoveInstruction<Long> instruction = new MoveInstruction<Long>(
                processor,
                0b0L,
                OPCodes.MOV_MVN,
                0b1L,
                MoveInstruction.OPTION_MOV,
                0b101L //register 2
        );

        instruction.execute();

        var result = processor.getRegisterValue(1);

        assertEquals(10L, (long) result);
    }

    @Test
    public void testMoveNot() {
        long value = 0x001100000000000000L;
        processor.setRegisterValue(2,
                value
        );

        MoveInstruction<Long> instruction = new MoveInstruction<Long>(
                processor,
                0b0L,
                OPCodes.MOV_MVN,
                0b1L,
                MoveInstruction.OPTION_MVN,
                0b101L //register 2
        );

        instruction.execute();

        var result = processor.getRegisterValue(1);
        long expected = ~value;

        assertEquals(expected, (long) result);
    }
}
