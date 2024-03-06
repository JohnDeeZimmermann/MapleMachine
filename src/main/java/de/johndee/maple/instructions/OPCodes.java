package de.johndee.maple.instructions;

public class OPCodes {

    public static final long MOV_MVN = 0b00000001L;
    public static final long ADDI = 0b00000010L;
    public static final long SUBI = 0b00000011L;
    public static final long MULI = 0b00000100L;
    public static final long DIVI = 0b00000101L;
    public static final long ADDF = 0b00000110L;
    public static final long SUBF = 0b00000111L;
    public static final long MULF = 0b00001000L;
    public static final long DIVF = 0b00001001L;
    public static final long MVF = 0b00001010L;
    public static final long CMPI = 0b00001011L;
    public static final long CMPF = 0b00001100L;
    public static final long COMP_STORE_RESULT = 0b00001101L;
    public static final long COND_BRANCH = 0b00001110L;
    public static final long BRANCH = 0b00001111L;
    public static final long BRANCH_LINK = 0b00001111L;
    public static final long SHIFT = 0b00010000L;
    public static final long AND_OR_XOR = 0b00010001L;
    public static final long LDR = 0b00010010L;
    public static final long STR = 0b00010011L;
    public static final long POP_PUSH = 0b00010101L;

    public static final long CAR_DEFINE = 0b1000000L;
    public static final long CAR_REMOVE = 0b1000001L;
    public static final long CAR_STACK_REGION_DEFINE = 0b1000010L;
}
