package de.johndee.tests.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import de.johndee.maple.instructions.ArithmeticIntegerInstruction;
import de.johndee.maple.interpreter.MapleInstructionParser;
import de.johndee.maple.instructions.MoveInstruction;
import de.johndee.maple.instructions.MapleBinaryCodes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleInstructionTest {

    private Processor<Long> processor;

    @Test
    public void testBaseBinaryFormat() {
        ArithmeticIntegerInstruction<Long> baseInstruction = new ArithmeticIntegerInstruction<>(processor,
                0L,
                0b00000010L,
                0b0001L,
                0b0000L,
                0b000000000000000000000011L,
                0b000000000000000000100000L,
                ArithmeticIntegerInstruction.Operator.ADD);

        assertEquals(
                (long) baseInstruction.getBinaryFormat(),
                0b0000001000000001000000000000000000000011000000000000000000100000L);

        assertNotEquals(
                (long) baseInstruction.getBinaryFormat(),
                0b0000001000000001000000000000000000000101000000000000000000100001L
        );
    }


    @Test
    public void testBaseDecode() {
        long rawInstruction = 0b0000001000000001000000000000000000000101000000000000000000100000L; // ADDI r1, r2, #16
        var instruction = MapleInstructionParser.fromBinaryFormat(processor, rawInstruction, 0L);

        assertTrue(instruction instanceof ArithmeticIntegerInstruction);
        assertEquals((long) instruction.getOPCode(), 0b00000010L);

        var ari = (ArithmeticIntegerInstruction<Long>) instruction;
        assertEquals((long) ari.getDestinationRegister(), 0b0001L);
        assertEquals((long) ari.getOption(), 0b0000L);
        assertEquals((long) ari.getFirstArgument(), 0b101);
        assertEquals((long) ari.getSecondArgument(), 0b100000L);
    }

    @Test
    public void testMoveDecode() {
        long rawInstruction = 0b0000000100010000000000000000000000000000000000000000000100000000L; // MOV r2, #128
        var instruction = MapleInstructionParser.fromBinaryFormat(processor, rawInstruction, 0L);

        assertTrue(instruction instanceof MoveInstruction);
        var move = (MoveInstruction<Long>) instruction;
        Assert.assertEquals((long) instruction.getOPCode(), MapleBinaryCodes.MOV_MVN);
        assertEquals((long) move.getDestinationRegister(), 0b0010L);
        assertEquals((long) move.getOptions(), MoveInstruction.OPTION_MOV);
        assertEquals((long) move.getArgument(), 128L << 1);
    }

    @Test
    public void testMoveBinaryFormat() {
        MoveInstruction<Long> moveInstruction = new MoveInstruction<>(processor,
                0L,
                MapleBinaryCodes.MOV_MVN,
                0b0010L,
                MoveInstruction.OPTION_MOV,
                128L << 1);
        assertEquals(
                (long) moveInstruction.getBinaryFormat(),
                0b0000000100010000000000000000000000000000000000000000000100000000L);

        assertNotEquals(
                (long) moveInstruction.getBinaryFormat(),
                0b0000000000010000000000000000000000000000000000000000000100000001L
        );
    }


    @Before
    public void setUp() {
        this.processor = new Maple64();
    }

}
