package de.johndee.maple.utils;

import de.johndee.maple.core.CRHandler;
import de.johndee.maple.core.Processor;

/**
 * Handles the CR-Register of the processor
 */
public class CRHandler64 implements CRHandler<Long> {

    public final static long MASK_STORAGE_DVC =
            0b0000001111000000000000000000000000000000000000000000000000000000L;
    public final static long MASK_GPU_DVC =
            0b0000000000111100000000000000000000000000000000000000000000000000L;
    public final static long MASK_IO_DEVICE_COUNT =
            0b0000000000000011110000000000000000000000000000000000000000000000L;
    public final static long MASK_INTERRUPT_CODE =
            0b0000000000000000001111111100000000000000000000000000000000000000L;
    public final static long MASK_INTERRUPT_RESULT =
            0b0000000000000000000000000011111111111111111111111111111111000000L;
    public final static long MASK_SYS_STATUS =
            0b0000000000000000000000000000000000000000000000000000000000100000L;
    public final static long MASK_PARITY =
            0b0000000000000000000000000000000000000000000000000000000000010000L;
    public final static long MASK_EVEN =
            0b0000000000000000000000000000000000000000000000000000000000001000L;
    public final static long MASK_NEGATIVE =
            0b0000000000000000000000000000000000000000000000000000000000000100L;
    public final static long MASK_ZERO =
            0b0000000000000000000000000000000000000000000000000000000000000010L;
    public final static long MASK_OVERFLOW =
            0b0000000000000000000000000000000000000000000000000000000000000001L;

    public final static long SHIFT_STORAGE_DVC = 54;
    public final static long SHIFT_GPU_DVC = 50;
    public final static long SHIFT_IO_DEVICE_COUNT = 46;
    public final static long SHIFT_INTERRUPT_CODE = 38;
    public final static long SHIFT_INTERRUPT_RESULT = 6;
    public final static long SHIFT_SYS_STATUS = 5;
    public final static long SHIFT_PARITY = 4;
    public final static long SHIFT_EVEN = 3;
    public final static long SHIFT_NEGATIVE = 2;
    public final static long SHIFT_ZERO = 1;
    public final static long SHIFT_OVERFLOW = 0;

    private Processor<Long> processor;

    public CRHandler64(Processor<Long> processor) {
        this.processor = processor;
    }



    public void setStorageDeviceLocation(Long location) {
        if (location > 0b1111) {
            throw new IllegalArgumentException("Invalid length for storage device location");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = location << SHIFT_STORAGE_DVC | (currentCR & ~MASK_STORAGE_DVC);
        processor.setCompareResultRegister(currentCR);
    }

    public void setGPUDeviceLocation(Long location) {
        if (location > 0b1111) {
            throw new IllegalArgumentException("Invalid length for GPU device location");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = location << SHIFT_GPU_DVC | (currentCR & ~MASK_GPU_DVC);
        processor.setCompareResultRegister(currentCR);
    }

    public void setIODeviceCount(Long count) {
        if (count > 0b1111) {
            throw new IllegalArgumentException("Invalid length for IO device count");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = count << SHIFT_IO_DEVICE_COUNT | (currentCR & ~MASK_IO_DEVICE_COUNT);
        processor.setCompareResultRegister(currentCR);
    }

    public void setInterruptCode(Long code) {
        if (code > 0b111111111) {
            throw new IllegalArgumentException("Invalid length for interrupt code");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = code << SHIFT_INTERRUPT_CODE | (currentCR & ~MASK_INTERRUPT_CODE);
        processor.setCompareResultRegister(currentCR);
    }

    public void setInterruptResult(Long result) {
        if (result > 0b1111111111111111111111111) {
            throw new IllegalArgumentException("Invalid length for interrupt result");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = result << SHIFT_INTERRUPT_RESULT | (currentCR & ~MASK_INTERRUPT_RESULT);
        processor.setCompareResultRegister(currentCR);
    }

    public void setSysStatus(Long status) {
        if (status > 0b1) {
            throw new IllegalArgumentException("Invalid length for system status");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = status << SHIFT_SYS_STATUS | (currentCR & ~MASK_SYS_STATUS);
        processor.setCompareResultRegister(currentCR);
    }

    public void setParity(Long parity) {
        if (parity > 0b1) {
            throw new IllegalArgumentException("Invalid length for parity");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = parity << SHIFT_PARITY | (currentCR & ~MASK_PARITY);
        processor.setCompareResultRegister(currentCR);
    }

    public void setEven(Long even) {
        if (even > 0b1) {
            throw new IllegalArgumentException("Invalid length for even");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = even << SHIFT_EVEN | (currentCR & ~MASK_EVEN);
        processor.setCompareResultRegister(currentCR);
    }

    public void setNegative(Long negative) {
        if (negative > 0b1) {
            throw new IllegalArgumentException("Invalid length for negative");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = negative << SHIFT_NEGATIVE | (currentCR & ~MASK_NEGATIVE);
        processor.setCompareResultRegister(currentCR);
    }

    public void setZero(Long zero) {
        if (zero > 0b1) {
            throw new IllegalArgumentException("Invalid length for zero");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = (zero << SHIFT_ZERO) | (currentCR & ~MASK_ZERO);
        processor.setCompareResultRegister(currentCR);
    }

    public void setOverflow(Long overflow) {
        if (overflow > 0b1) {
            throw new IllegalArgumentException("Invalid length for overflow");
        }
        long currentCR = processor.getCompareResultRegister();
        currentCR = overflow << SHIFT_OVERFLOW | (currentCR & ~MASK_OVERFLOW);
        processor.setCompareResultRegister(currentCR);
    }

    public Long getStorageDeviceLocation() {
        long cr = processor.getCompareResultRegister();
        long masked = processor.getCompareResultRegister() & MASK_STORAGE_DVC;
        long shifted = masked >> SHIFT_STORAGE_DVC;

        return shifted;
    }

    public Long getGPUDeviceLocation() {
        return (processor.getCompareResultRegister() & MASK_GPU_DVC) >> SHIFT_GPU_DVC;
    }

    public Long getIODeviceCount() {
        return (processor.getCompareResultRegister() & MASK_IO_DEVICE_COUNT) >> SHIFT_IO_DEVICE_COUNT;
    }

    public Long getInterruptCode() {
        return (processor.getCompareResultRegister() & MASK_INTERRUPT_CODE) >> SHIFT_INTERRUPT_CODE;
    }

    public Long getInterruptResult() {
        return (processor.getCompareResultRegister() & MASK_INTERRUPT_RESULT) >> SHIFT_INTERRUPT_RESULT;
    }

    public Long getSysStatus() {
        return (processor.getCompareResultRegister() & MASK_SYS_STATUS) >> SHIFT_SYS_STATUS;
    }

    public Long getParity() {
        return (processor.getCompareResultRegister() & MASK_PARITY) >> SHIFT_PARITY;
    }

    public Long getEven() {
        return (processor.getCompareResultRegister() & MASK_EVEN) >> SHIFT_EVEN;
    }

    public Long getNegative() {
        return (processor.getCompareResultRegister() & MASK_NEGATIVE) >> SHIFT_NEGATIVE;
    }

    public Long getZero() {
        return (processor.getCompareResultRegister() & MASK_ZERO) >> SHIFT_ZERO;
    }

    public Long getOverflow() {
        return (processor.getCompareResultRegister() & MASK_OVERFLOW) >> SHIFT_OVERFLOW;
    }

    public void clear() {
        processor.setCompareResultRegister(0L);
    }



}
