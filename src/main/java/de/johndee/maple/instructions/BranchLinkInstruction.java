package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;
import de.johndee.maple.utils.StackUtils;

public class BranchLinkInstruction<Word extends Number> extends BranchInstruction<Word>{
    public BranchLinkInstruction(Processor<Word> processor,
                                 Word address,
                                 Word OPCode,
                                 Word rdest,
                                 Word option,
                                 Word rargs1) {
        super(processor, address, OPCode, rdest, option, rargs1);
    }

    @Override
    public void execute() {
        var proc = getProcessor();
        var ar = proc.getArithmeticWrapper();

        super.execute();

        StackUtils.push(proc, proc.getDynamicLink(), this);
        proc.setDynamicLink(ar.add(getAddress(), (byte) 1));
    }
}
