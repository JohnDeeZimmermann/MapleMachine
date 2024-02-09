package de.johndee.vpm.core;

public class MemoryRegion<Word extends Number> {

    Word start;
    Word end;

    public MemoryRegion(Word start, Word end) {
        this.start = start;
        this.end = end;
    }

    public Word getStart() {
        return start;
    }

    public Word getEnd() {
        return end;
    }

}
