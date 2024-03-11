package de.johndee.maple.instructions;

import de.johndee.maple.core.CRHandler;
import de.johndee.maple.core.Processor;
import de.johndee.maple.impl.Maple64;
import de.johndee.maple.utils.CRHandler64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CRHandlerTest {

    private Processor<Long> processor;
    private CRHandler<Long> crHandler;

    private static final long BASE_VALUE = 0xAE78FFBC67BF4F98L;

    @Before
    public void init() {
        processor = new Maple64();
        crHandler = new CRHandler64(processor);

        processor.setCompareResultRegister(BASE_VALUE);
    }

    /*
     * The goal is to change each value and to check whether all the other values are unaffected
     * and if the value can be correctly retrieved.
     * (So we'd have created a way to store information)
     */


    @Test
    public void testZeroSet() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeZero = crHandler.getZero();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setZero(0b1L);

        assertEquals(0b1L, (long) crHandler.getZero());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }


    @Test
    public void testEvenSet() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setEven(0b1L);

        assertEquals(0b1L, (long) crHandler.getEven());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testParitySet() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeNegative = crHandler.getNegative();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setParity(0b1L);

        assertEquals(0b1L, (long) crHandler.getParity());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testNegativeSet() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setNegative(0b1L);

        assertEquals(0b1L, (long) crHandler.getNegative());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testOverflowSet() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();

        crHandler.setOverflow(0b1L);

        assertEquals(0b1L, (long) crHandler.getOverflow());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
    }

    @Test
    public void testStorageDeviceLocation() {
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setStorageDeviceLocation(0b101L);

        assertEquals(0b101L, (long) crHandler.getStorageDeviceLocation());

        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testGPUDeviceLocation() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setGPUDeviceLocation(0b101L);

        assertEquals(0b101L, (long) crHandler.getGPUDeviceLocation());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testIODeviceCount() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setIODeviceCount(0b101L);

        assertEquals(0b101L, (long) crHandler.getIODeviceCount());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testInterruptCode() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setInterruptCode(0b101L);

        assertEquals(0b101L, (long) crHandler.getInterruptCode());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testInterruptResult() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeSysStatus = crHandler.getSysStatus();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setInterruptResult(0b101L);

        assertEquals(0b101L, (long) crHandler.getInterruptResult());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeSysStatus, (long) crHandler.getSysStatus());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }

    @Test
    public void testSysStatus() {
        long beforeStorage = crHandler.getStorageDeviceLocation();
        long beforeGPU = crHandler.getGPUDeviceLocation();
        long beforeIO = crHandler.getIODeviceCount();
        long beforeInterruptCode = crHandler.getInterruptCode();
        long beforeInterruptResult = crHandler.getInterruptResult();
        long beforeParity = crHandler.getParity();
        long beforeZero = crHandler.getZero();
        long beforeEven = crHandler.getEven();
        long beforeNegative = crHandler.getNegative();
        long beforeOverflow = crHandler.getOverflow();

        crHandler.setSysStatus(0b1L);

        assertEquals(0b1L, (long) crHandler.getSysStatus());

        assertEquals(beforeStorage, (long) crHandler.getStorageDeviceLocation());
        assertEquals(beforeGPU, (long) crHandler.getGPUDeviceLocation());
        assertEquals(beforeIO, (long) crHandler.getIODeviceCount());
        assertEquals(beforeInterruptCode, (long) crHandler.getInterruptCode());
        assertEquals(beforeInterruptResult, (long) crHandler.getInterruptResult());
        assertEquals(beforeParity, (long) crHandler.getParity());
        assertEquals(beforeZero, (long) crHandler.getZero());
        assertEquals(beforeEven, (long) crHandler.getEven());
        assertEquals(beforeNegative, (long) crHandler.getNegative());
        assertEquals(beforeOverflow, (long) crHandler.getOverflow());
    }



}
