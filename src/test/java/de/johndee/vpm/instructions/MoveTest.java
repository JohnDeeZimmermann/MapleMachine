package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;
import de.johndee.vpm.impl.VPM64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveTest {

    private Processor<Long> processor;
    @Before
    public void init() {
        processor = new VPM64();
    }

    @Test
    public void testMoveDirect() {
        MoveInstruction<Long> instruction = new MoveInstruction<Long>(
                processor,
                0b0L,
                0b0L,
                0b1L,
                0b0L,
                0b10000L, //8
                false
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
                0b0L,
                0b1L,
                0b0L,
                0b101L, //register 2
                false
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
                0b0L,
                0b1L,
                0b0L,
                0b101L, //register 2
                true
        );

        instruction.execute();

        var result = processor.getRegisterValue(1);
        long expected = ~value;

        assertEquals(expected, (long) result);
    }
}