package de.johndee.maple.core;

public interface CRHandler<Word extends Number> {

    void setStorageDeviceLocation(Word location);
    void setGPUDeviceLocation(Word location);
    void setIODeviceCount(Word count);
    void setInterruptCode(Word code);
    void setInterruptResult(Word result);
    void setSysStatus(Word status);
    void setParity(Word parity);

    void setEven(Word even);
    void setNegative(Word negative);
    void setZero(Word zero);
    void setOverflow(Word overflow);
    Word getStorageDeviceLocation();
    Word getGPUDeviceLocation();
    Word getIODeviceCount();
    Word getInterruptCode();
    Word getInterruptResult();
    Word getSysStatus();
    Word getParity();
    Word getEven();
    Word getNegative();
    Word getZero();
    Word getOverflow();

    void clear();
}
