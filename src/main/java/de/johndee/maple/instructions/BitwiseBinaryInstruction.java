package de.johndee.maple.instructions;

import de.johndee.maple.core.Processor;

public class BitwiseBinaryInstruction<Word extends Number> extends BitwiseInstruction<Word> {
    public BitwiseBinaryInstruction(Processor<Word> processor,
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
