package de.johndee.vpm.impl;

import de.johndee.vpm.core.IODevice;
import de.johndee.vpm.core.MemoryDevice;
import de.johndee.vpm.core.Processor;
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

    private Long[] registers;
    private List<IODevice<Long>> ioDevices;

    public VPM64() {
        registers = new Long[16]; // 16 registers
        ioDevices = new ArrayList<>(2);
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
        return null;
    }
}
