package de.johndee.vpm.impl;

import de.johndee.vpm.core.MemoryDevice;
import de.johndee.vpm.core.MemoryRegion;
import de.johndee.vpm.exceptions.IllegalMemoryAccessException;

import java.util.ArrayList;
import java.util.List;

public class Memory64 implements MemoryDevice<Long> {

    private final static byte NOT_CACHED = 0;
    private final static byte CACHED_IN_CAR = 1;
    private final static byte CACHED_NOT_IN_CAR = 2;

    private List<MemoryRegion<Long>> controlledAccessRegions;
    private MemoryRegion<Long> stackRegion;

    private final boolean isControlled;
    private final boolean cacheCAR;

    private final long[] memory;
    private byte[] CARCache;
    private int size;

    public Memory64(int size,
                    boolean isControlled,
                    boolean cacheCAR,
                    MemoryRegion<Long> defaultStackRegion) {

        memory = new long[size];
        this.isControlled = isControlled;

        controlledAccessRegions = new ArrayList<>();

        this.stackRegion = defaultStackRegion;
        this.cacheCAR = cacheCAR;
        this.size = size;

        if (cacheCAR) {
            CARCache = new byte[size];
        }
    }

    @Override
    public Long read(Long address, Long rf) throws IllegalMemoryAccessException {
        if (isInsideCAR(rf) && !isInsideCAR(address)) {
            throw new IllegalMemoryAccessException(address, 1, rf);
        }

        return memory[(int) (long) address];
    }

    @Override
    public Long[] read(Long address, Long length, Long rf) throws IllegalMemoryAccessException {
        Long[] ret = new Long[(int) (long) length];
        for (int i = 0; i < length; i++) {
            if (isInsideCAR(rf) && !isInsideCAR(address + i)) {
                throw new IllegalMemoryAccessException(address, length, rf);
            }

            ret[i] = memory[(int) (long) address + i];
        }

        return ret;
    }

    @Override
    public void write(Long address, Long value, Long rf) throws IllegalMemoryAccessException {
        if (isInsideCAR(rf) && !isInsideCAR(address)) {
            throw new IllegalMemoryAccessException(address, 1, rf);
        }

        memory[(int) (long) address] = value;
    }

    @Override
    public void write(Long address, Long[] values, Long rf) throws IllegalMemoryAccessException {
        for (int i = 0; i < values.length; i++) {
            if (isInsideCAR(rf) && !isInsideCAR(address + i)) {
                throw new IllegalMemoryAccessException(address, values.length, rf);
            }

            memory[(int) (long) address + i] = values[i];
        }
    }

    @Override
    public List<MemoryRegion<Long>> getCARs() {
        return controlledAccessRegions;
    }

    @Override
    public MemoryRegion<Long> getStackRegion() {
        return stackRegion;
    }

    @Override
    public void registerCAR(MemoryRegion<Long> region, Long rf) throws IllegalMemoryAccessException {
        if (isInsideCAR(rf)) {
            throw new IllegalMemoryAccessException(region.getStart(), region.getEnd(), rf);
        }

        controlledAccessRegions.add(region);
    }

    @Override
    public void unregisterCAR(MemoryRegion<Long> region, Long rf) throws IllegalMemoryAccessException {
        if (isInsideCAR(rf)) {
            throw new IllegalMemoryAccessException(region.getStart(), region.getEnd(), rf);
        }

        controlledAccessRegions.remove(region);
    }

    @Override
    public void setStackRegion(MemoryRegion<Long> region, Long rf) throws IllegalMemoryAccessException {
        if (isInsideCAR(rf)) {
            throw new IllegalMemoryAccessException(region.getStart(), region.getEnd(), rf);
        }

        stackRegion = region;
    }

    public boolean isInsideCAR(Long address) {
        if (!isControlled) {
            return false;
        }

        if (cacheCAR) {
            if (CARCache[(int) (long) address] == CACHED_IN_CAR) {
                return true;
            } else if (CARCache[(int) (long) address] == CACHED_NOT_IN_CAR) {
                return false;
            }
        }


        boolean value = false;
        for (MemoryRegion<Long> car : controlledAccessRegions) {
            if (address >= car.getStart() && address <= car.getEnd()) {
                value = true;
                break;
            }
        }

        if (address >= stackRegion.getStart() && address <= stackRegion.getEnd()) {
            value = true;
        }

        if (cacheCAR) {
            CARCache[(int) (long) address] = value ? CACHED_IN_CAR : CACHED_NOT_IN_CAR;
        }

        return value;
    }
}
