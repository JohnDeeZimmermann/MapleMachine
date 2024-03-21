package de.johndee.tests.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import de.johndee.maple.instructions.CompareFloatInstruction;
import de.johndee.maple.instructions.CompareIntegerInstruction;
import de.johndee.maple.instructions.OPCodes;
import de.johndee.maple.utils.CRHandler64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConditionTests {

    private Processor<Long> processor;

    @Before
    public void init() {
        processor = new Maple64();
    }

    @Test
    public void testIntCompare() {

        processor.setRegisterValue(1, 15L);
        processor.setRegisterValue(2, 20L);

        // Lower than
        var instruction = new CompareIntegerInstruction<Long>(
                this.processor,
                0L,
                OPCodes.CMPI,
                0L,
                0L,
                0b11L,
                0b101L
        );

        instruction.execute();

        var cr = new CRHandler64(processor);

        assertEquals(1L, (long) cr.getNegative());
    }

    @Test
    public void testFloatCompare() {

        var ar = processor.getArithmeticWrapper();

        processor.setRegisterValue(1, ar.reinterpretAsWord(14.5f));
        processor.setRegisterValue(2, ar.reinterpretAsWord(15.5f));

        var instruction = new CompareFloatInstruction<Long>(
                this.processor,
                0L,
                OPCodes.CMPF,
                0L,
                0L,
                0b11L,
                0b101L
        );

        instruction.execute();

        var cr = new CRHandler64(processor);

        System.out.println(ar.reinterpretAsFloat(processor.getRegisterValue(1)));
        System.out.println(ar.reinterpretAsFloat(processor.getRegisterValue(2)));

        assertEquals(1L, (long) cr.getNegative());

    }
}
