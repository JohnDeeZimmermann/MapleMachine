package de.johndee.maple.instructions;

public class OPCodes {

    public static final byte MOV = 0b00000000;
    public static final byte MVN = 0b00000001;
    public static final byte ADDI = 0b00000010;
    public static final byte SUBI = 0b00000011;
    public static final byte MULI = 0b00000100;
    public static final byte DIVI = 0b00000101;
    public static final byte ADDF = 0b00000110;
    public static final byte SUBF = 0b00000111;
    public static final byte MULF = 0b00001000;
    public static final byte DIVF = 0b00001001;
    public static final byte MVF = 0b00001010;
    public static final byte CMPI = 0b00001011;
    public static final byte CMPF = 0b00001100;
    public static final byte COMP_STORE_RESULT = 0b00001101;
    public static final byte COND_BRANCH = 0b00001110;
    public static final byte BRANCH = 0b00001111;
    public static final byte BRANCH_LINK = 0b00001111;
    public static final byte SHIFT = 0b00010000;
    public static final byte AND_OR_XOR = 0b00010001;
    public static final byte LDR = 0b00010010;
    public static final byte STR = 0b00010011;

    public static final byte CAR_DEFINE = 0b1000000;
    public static final byte CAR_REMOVE = 0b1000001;
    public static final byte CAR_STACK_REGION_DEFINE = 0b1000010;
}
