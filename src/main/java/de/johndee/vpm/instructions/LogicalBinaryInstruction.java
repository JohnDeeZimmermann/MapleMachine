package de.johndee.vpm.instructions;

import de.johndee.vpm.core.Processor;

public class LogicalBinaryInstruction<Word extends Number> extends LogicalInstruction<Word>{
    public LogicalBinaryInstruction(Processor<Word> processor,
                                    Word address,
                                    Word OPCode,
                                    Word rdest,
                                    Word option,
                                    Word rargs1,
                                    Word rargs2) {
        super(processor, address, OPCode, rdest, option, rargs1, rargs2, getOperator(option));
    }

    private static <Word extends Number> Operator getOperator(Word option) {
       switch (option.intValue()) {
           case 0:
               return Operator.AND;
           case 1:
               return Operator.OR;
           case 2:
               return Operator.XOR;
           default:
               throw new IllegalArgumentException("Invalid option for LogicalBinaryInstruction");
       }
    }
}
