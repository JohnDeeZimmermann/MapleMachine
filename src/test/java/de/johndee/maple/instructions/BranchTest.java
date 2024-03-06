package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BranchTest {

    private Processor<Long> processor;

    @Before
    public void init() {
        processor = new Maple64();
    }

    @Test
    public void testBranch() {
        long destinationAddress = 10;
        processor.setRegisterValue(1, destinationAddress);

        Instruction<Long> instruction = new BranchInstruction<>(
            processor,
            0b0L,
                OPCodes.BRANCH,
                1L,
                0L,
                0L
        );

        instruction.execute();
        var pc = processor.getProgramCounter();

        assertEquals(destinationAddress - 1, (long) pc); // dest - 1 as branch should point to the address before
    }

    @Test
    public void testBranchWithOffset() {
        long destinationAddress = 10;
        processor.setRegisterValue(1, destinationAddress);

        Instruction<Long> instruction = new BranchInstruction<>(
                processor,
                0b0L,
                OPCodes.BRANCH,
                1L,
                0L,
                0b10000L // Offset of 8
        );

        instruction.execute();
        var pc = processor.getProgramCounter();

        assertEquals(destinationAddress + 8 - 1, (long) pc); // dest - 1 as branch should point to the address before
    }


}
