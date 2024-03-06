package de.johndee.maple.utils;

import de.johndee.maple.core.Processor;
import de.johndee.maple.exceptions.IllegalMemoryAccessException;
import de.johndee.maple.instructions.Instruction;

public class StackUtils {

    public static <Word extends Number> void push(Processor<Word> processor, Word value, Instruction<Word> source) {
        var ar = processor.getArithmeticWrapper();
        var sp = processor.getStackPointer();

        var memory = processor.getMemoryDevice();
        try {
            memory.write(sp, value, source.getAddress());
            processor.setStackPointer(ar.sub(sp, ar.fromInt(1)));
        } catch (IllegalMemoryAccessException e) {
            processor.error(e.getMessage(), source);
        }
    }

    public static <Word extends Number> Word pop(Processor<Word> processor, Instruction<Word> source) {
        var ar = processor.getArithmeticWrapper();
        var sp = processor.getStackPointer();

        var memory = processor.getMemoryDevice();
        try {
            processor.setStackPointer(ar.add(sp, ar.fromInt(1)));
            return memory.read(processor.getStackPointer(), source.getAddress());
        } catch (IllegalMemoryAccessException e) {
            processor.error(e.getMessage(), source);
        }
        return ar.fromInt(0);
    }

}
