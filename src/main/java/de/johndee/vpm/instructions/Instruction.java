package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public abstract class Instruction<Word extends Number> {

    private Processor<Word> processor;
    private Word address;
    private Word opcode;


    public Instruction(Processor<Word> processor, Word address, Word opcode) {
        this.processor = processor;
        this.address = address;
        this.opcode = opcode;
    }

    public Processor<Word> getProcessor() {
        return processor;
    }

    public Word getAddress() {
        return address;
    }

    public Word getOPCode() {
        return opcode;
    }

    public Word getBinaryFormat() {
        return processor.getArithmeticWrapper().binaryInstructionFormat(this);
    }

    /**
     * Execute the instruction.
     */
    public abstract void execute();

}
