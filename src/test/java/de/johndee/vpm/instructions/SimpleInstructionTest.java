package de.johndee.vpm.instructions;

import de.johndee.vpm.core.IODevice;
import de.johndee.vpm.core.MemoryDevice;
import de.johndee.vpm.core.Processor;
import de.johndee.vpm.impl.ArithmeticWrapper64;
import de.johndee.vpm.utils.ArithmeticWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        var instruction = VPM64InstructionParser.fromBinaryFormat(processor, rawInstruction, 0L);

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
        long rawInstruction = 0b0000000000010000000000000000000000000000000000000000000100000000L; // MOV r2, #128
        var instruction = VPM64InstructionParser.fromBinaryFormat(processor, rawInstruction, 0L);

        assertTrue(instruction instanceof MoveInstruction);
        var move = (MoveInstruction<Long>) instruction;
        assertEquals((long) instruction.getOPCode(), 0L);
        assertEquals((long) move.getDestinationRegister(), 0b0010L);
        assertEquals((long) move.getOptions(), 0b0L);
        assertEquals((long) move.getArgument(), 128L << 1);
    }

    @Test
    public void testMoveBinaryFormat() {
        MoveInstruction<Long> moveInstruction = new MoveInstruction<>(processor,
                0L,
                0b00000000L,
                0b0010L,
                0b0L,
                128L << 1,
                false);
        assertEquals(
                (long) moveInstruction.getBinaryFormat(),
                0b0000000000010000000000000000000000000000000000000000000100000000L);

        assertNotEquals(
                (long) moveInstruction.getBinaryFormat(),
                0b0000000000010000000000000000000000000000000000000000000100000001L
        );
    }


    @Before
    public void setUp() {
        processor = new Processor<Long>() {
            @Override
            public void setRegisterValue(int registerID, Long value) {

            }

            @Override
            public void registerIODevice(IODevice<Long> ioDevice) {

            }

            @Override
            public void setProgramCounter(Long value) {

            }

            @Override
            public void setStackPointer(Long value) {

            }

            @Override
            public void setDynamicLink(Long value) {

            }

            @Override
            public void setCompareResultRegister(Long value) {

            }

            @Override
            public void setFramePointer(Long value) {

            }

            @Override
            public void setProgramStart(Long value) {

            }

            @Override
            public void setProgramLength(Long value) {

            }

            @Override
            public void setIOPointer(Long value) {

            }

            @Override
            public void setHardwareRegister(int index, Long value) {

            }

            @Override
            public void setReturnRegister(Long value) {

            }

            @Override
            public Long step() {
                return null;
            }

            @Override
            public Long run(Long startAddress) {
                return null;
            }

            @Override
            public Long getRegisterValue(int registerID) {
                return null;
            }

            @Override
            public Long getProgramCounter() {
                return null;
            }

            @Override
            public Long getStackPointer() {
                return null;
            }

            @Override
            public Long getDynamicLink() {
                return null;
            }

            @Override
            public Long getCompareResultRegister() {
                return null;
            }

            @Override
            public Long getFramePointer() {
                return null;
            }

            @Override
            public Long getProgramStart() {
                return null;
            }

            @Override
            public Long getProgramLength() {
                return null;
            }

            @Override
            public Long getIOPointer() {
                return null;
            }

            @Override
            public Long getHardwareRegister(int index) {
                return null;
            }

            @Override
            public Long getReturnRegister() {
                return null;
            }

            @Override
            public MemoryDevice<Long> getMemoryDevice() {
                return null;
            }

            @Override
            public List<IODevice<Long>> getIODevice() {
                return null;
            }

            @Override
            public ArithmeticWrapper<Long> getArithmeticWrapper() {
                return new ArithmeticWrapper64();
            }
        };
    }

}
