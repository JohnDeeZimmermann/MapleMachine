package de.johndee.tests.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import de.johndee.maple.instructions.ArithmeticFloatInstruction;
import de.johndee.maple.instructions.ArithmeticInstruction;
import de.johndee.maple.instructions.ArithmeticIntegerInstruction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArithmeticTest {

    private Processor<Long> processor;

    @Before
    public void init() {
        processor = new Maple64();
    }


    @Test
    public void testIntAdd() {

        processor.setRegisterValue(3, 8L);

        ArithmeticIntegerInstruction<Long> instruction = new ArithmeticIntegerInstruction<Long>(
                processor,
                0L,
                0b10L,
                0b0L, // Destination register 0
                0L,
                0b111L, //Register 3
                0b10000L, // Direct Value 8
                ArithmeticInstruction.Operator.ADD
        );

        instruction.execute();

        long result = processor.getRegisterValue(0);

        assertEquals(16, result);

    }

    @Test
    public void testFloatAdd() {
        var ar = processor.getArithmeticWrapper();
        processor.setRegisterValue(3, ar.reinterpretAsWord(5.0f));
        processor.setRegisterValue(4, ar.reinterpretAsWord(3.0f));

        ArithmeticFloatInstruction<Long> instruction = new ArithmeticFloatInstruction<Long>(
                processor,
                0L,
                0b00000110L,
                0b0L,
                0b0L,
                0b111L,
                0b1001L,
                ArithmeticInstruction.Operator.ADD
        );

        instruction.execute();

        long lres = processor.getRegisterValue(0);
        float fres = ar.reinterpretAsFloat(lres);
        float expected = 8.0f;

        assertEquals(expected, fres, 0.1f);
    }

}
