package de.johndee.vpm.impl;

import de.johndee.vpm.core.IODevice;
import de.johndee.vpm.core.MemoryDevice;
import de.johndee.vpm.core.MemoryRegion;
import de.johndee.vpm.core.Processor;
import de.johndee.vpm.exceptions.IllegalMemoryAccessException;
import de.johndee.vpm.instructions.VPM64InstructionParser;
import de.johndee.vpm.utils.ArithmeticWrapper;

import java.util.ArrayList;
import java.util.List;

public class VPM64 implements Processor<Long> {

    public final static int REGISTER_RET = 0b0000;
    public final static int REGISTER_SP = 0b0110;
    public final static int REGISTER_PC = 0b0111;
    public final static int REGISTER_DL = 0b1000;
    public final static int REGISTER_CR = 0b1001;
    public final static int REGISTER_IOP = 0b1010;
    public final static int REGISTER_PS = 0b1011;
    public final static int REGISTER_PL = 0b1100;
    public final static int REGISTER_FP = 0b1101;
    public final static int REGISTER_HR0 = 0b1110;
    public final static int REGISTER_HR1 = 0b1111;

    private final Long[] registers;
    private final List<IODevice<Long>> ioDevices;
    private final MemoryDevice<Long> memory;
    private final ArithmeticWrapper<Long> arithmeticWrapper = new ArithmeticWrapper64();

    public VPM64() {
        registers = new Long[16]; // 16 registers
        ioDevices = new ArrayList<>(2);

        memory = new Memory64(5000, true, true, MemoryRegion.of(4500L, 5000L));
        setStackPointer(5000L);
    }

    @Override
    public void setRegisterValue(int registerID, Long value) {
        registers[registerID] = value;
    }

    @Override
    public void registerIODevice(IODevice<Long> ioDevice) {
        ioDevices.add(ioDevice);
    }

    @Override
    public void setProgramCounter(Long value) {
        registers[REGISTER_PC] = value;
    }

    @Override
    public void setStackPointer(Long value) {
        registers[REGISTER_SP] = value;
    }

    @Override
    public void setDynamicLink(Long value) {
        registers[REGISTER_DL] = value;
    }

    @Override
    public void setCompareResultRegister(Long value) {
        registers[REGISTER_CR] = value;
    }

    @Override
    public void setFramePointer(Long value) {
        registers[REGISTER_FP] = value;
    }

    @Override
    public void setProgramStart(Long value) {
        registers[REGISTER_PS] = value;
    }

    @Override
    public void setProgramLength(Long value) {
        registers[REGISTER_PL] = value;
    }

    @Override
    public void setIOPointer(Long value) {
        registers[REGISTER_IOP] = value;
    }

    @Override
    public void setHardwareRegister(int index, Long value) {
        if (index == 0) {
            registers[REGISTER_HR0] = value;
        } else if (index == 1) {
            registers[REGISTER_HR1] = value;
        } else {
            throw new UnsupportedOperationException("Invalid hardware register index: " + index);
        }
    }

    @Override
    public void setReturnRegister(Long value) {
        registers[REGISTER_RET] = value;
    }

    @Override
    public Long step() {
        try {
            var pc = getProgramCounter();
            var raw = memory.read(pc, -1L);
            var instruction = VPM64InstructionParser.fromBinaryFormat(this, raw, pc);
            instruction.execute();

            setProgramCounter(pc + 1);

        } catch (IllegalMemoryAccessException e) {
            System.err.println("Illegal memory access at " + getProgramCounter());
            e.printStackTrace();
        }

        return getProgramCounter();
    }

    @Override
    public Long run(Long startAddress) {
        return null;
    }

    @Override
    public Long getRegisterValue(int registerID) {
        return registers[registerID];
    }

    @Override
    public Long getProgramCounter() {
        return registers[REGISTER_PC];
    }

    @Override
    public Long getStackPointer() {
        return registers[REGISTER_SP];
    }

    @Override
    public Long getDynamicLink() {
        return registers[REGISTER_DL];
    }

    @Override
    public Long getCompareResultRegister() {
        return registers[REGISTER_CR];
    }

    @Override
    public Long getFramePointer() {
        return registers[REGISTER_FP];
    }

    @Override
    public Long getProgramStart() {
        return registers[REGISTER_PS];
    }

    @Override
    public Long getProgramLength() {
        return registers[REGISTER_PL];
    }

    @Override
    public Long getIOPointer() {
        return registers[REGISTER_IOP];
    }

    @Override
    public Long getHardwareRegister(int index) {
        if (index == 0) {
            return registers[REGISTER_HR0];
        } else if (index == 1) {
            return registers[REGISTER_HR1];
        } else {
            throw new UnsupportedOperationException("Invalid hardware register index: " + index);
        }
    }

    @Override
    public Long getReturnRegister() {
        return registers[REGISTER_RET];
    }

    @Override
    public MemoryDevice<Long> getMemoryDevice() {
        return memory;
    }

    @Override
    public List<IODevice<Long>> getIODevice() {
        return null;
    }

    @Override
    public ArithmeticWrapper<Long> getArithmeticWrapper() {
        return this.arithmeticWrapper;
    }
}
