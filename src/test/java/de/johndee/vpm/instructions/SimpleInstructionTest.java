package de.johndee.vpm.instructions;

import de.johndee.vpm.core.IODevice;
import de.johndee.vpm.core.MemoryDevice;
import de.johndee.vpm.core.Processor;
import de.johndee.vpm.impl.ArithmeticWrapper64;
import de.johndee.vpm.utils.ArithmeticWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SimpleInstructionTest {

    private Processor<Long> processor;

    @Test
    public void testBinaryFormat() {
        ArithmeticIntegerInstruction<Long> instruction = new ArithmeticIntegerInstruction<>(processor,
                0L,
                0b00000010L,
                0b0001L,
                0b0000L,
                0b000000000000000000000011L,
                0b000000000000000000100000L,
                ArithmeticIntegerInstruction.Operator.ADD);
        assert instruction.getBinaryFormat().equals(
                0b0000001000000001000000000000000000000011000000000000000000100000L
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
