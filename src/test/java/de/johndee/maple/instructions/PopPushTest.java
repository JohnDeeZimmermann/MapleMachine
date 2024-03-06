package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.exceptions.IllegalMemoryAccessException;
import de.johndee.maple.impl.Maple64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PopPushTest {

    Processor<Long> processor;

    @Before
    public void init() {
        processor = new Maple64();
    }


    @Test
    public void testPushThenPop() {
        long sp = processor.getStackPointer();

        processor.setRegisterValue(1, 100L);

        var mem = processor.getMemoryDevice();

        //Push
        PopPushInstruction<Long> push = new PopPushInstruction<>(
                processor,
                0L,
                OPCodes.POP_PUSH,
                1L,
                PopPushInstruction.OPTION_PUSH
        );

        push.execute();

        try {
            long stackValue = mem.read(sp, 0L);
            assertEquals(stackValue, 100);

            long newSP = processor.getStackPointer();

            assertEquals(sp-1, newSP);

        } catch (IllegalMemoryAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

        processor.setRegisterValue(1, 0L);
        sp = processor.getStackPointer();

        //POP
        PopPushInstruction<Long> pop = new PopPushInstruction<>(
                processor,
                0L,
                OPCodes.POP_PUSH,
                1L,
                PopPushInstruction.OPTION_POP
        );

        pop.execute();

        long newSP = processor.getStackPointer();
        assertEquals(sp+1, newSP);

        long value = processor.getRegisterValue(1);
        assertEquals(100L, value);

    }

}
