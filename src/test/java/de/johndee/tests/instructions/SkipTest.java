package de.johndee.tests.instructions;


import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import de.johndee.maple.instructions.ConditionalSkipInstruction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SkipTest {

    private Processor<Long> processor;
    private ConditionalSkipInstruction<Long> instruction;

    @Before
    public void init() {
        processor = new Maple64();
        instruction = new ConditionalSkipInstruction<Long>(
                processor,
                0L,
                0b10L,
                0b0L, // Destination register 0
                0L,
                0b111L, //Register 3
                0b10000L // Direct Value 8
        );
    }

    @Test
    public void conditionMetShouldSkipNextInstruction() {
        processor.setRegisterValue(3, 8L);
        processor.setProgramCounter(0L);

        instruction.execute();

        long result = processor.getProgramCounter();

        assertEquals(1L, result);
    }

    @Test
    public void conditionNotMetShouldNotSkipNextInstruction() {
        processor.setRegisterValue(3, 0L);
        processor.setProgramCounter(0L);

        instruction.execute();

        long result = processor.getProgramCounter();

        assertEquals(0L, result);
    }
}
