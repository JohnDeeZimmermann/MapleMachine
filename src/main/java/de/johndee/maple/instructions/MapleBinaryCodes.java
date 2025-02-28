package de.johndee.maple.instructions;

import java.util.HashMap;
import java.util.Map;

public class MapleBinaryCodes {

    public static final long NOP = 0L;
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
    public static final long BRANCH_LINK = 0b00010000L;
    public static final long SHIFT = 0b00010001L;
    public static final long AND_OR_XOR = 0b00010010L;
    public static final long LDR = 0b00010011L;
    public static final long STR = 0b00010100L;
    public static final long POP_PUSH = 0b00010101L;
    public static final long COND_SKIP = 0b00001010L;
    public static final long EXIT = 0b000010110L;
    public static final long IO_READ_WRITE = 0b000010111L;

    public static final long CAR_DEFINE = 0b1000000L;
    public static final long CAR_REMOVE = 0b1000001L;
    public static final long CAR_STACK_REGION_DEFINE = 0b1000010L;

    public static Map<String, Long> codeMap = new HashMap<>();
    static {
        codeMap.put("NOP", NOP);
        codeMap.put("MOV", MOV_MVN);
        codeMap.put("MVN", MOV_MVN);
        codeMap.put("ADDI", ADDI);
        codeMap.put("SUBI", SUBI);
        codeMap.put("MULI", MULI);
        codeMap.put("DIVI", DIVI);
        codeMap.put("ADDF", ADDF);
        codeMap.put("SUBF", SUBF);
        codeMap.put("MULF", MULF);
        codeMap.put("DIVF", DIVF);
        codeMap.put("MVF", MVF);
        codeMap.put("CMPI", CMPI);
        codeMap.put("CMPF", CMPF);
        codeMap.put("RGE", COMP_STORE_RESULT);
        codeMap.put("RLE", COMP_STORE_RESULT);
        codeMap.put("RGT", COMP_STORE_RESULT);
        codeMap.put("RLT", COMP_STORE_RESULT);
        codeMap.put("REQ", COMP_STORE_RESULT);
        codeMap.put("RNQ", COMP_STORE_RESULT);
        codeMap.put("BGE", COND_BRANCH);
        codeMap.put("BLE", COND_BRANCH);
        codeMap.put("BGT", COND_BRANCH);
        codeMap.put("BLT", COND_BRANCH);
        codeMap.put("BEQ", COND_BRANCH);
        codeMap.put("BNQ", COND_BRANCH);
        codeMap.put("SEQ", COND_SKIP);
        codeMap.put("SNQ", COND_SKIP);
        codeMap.put("SLE", COND_SKIP);
        codeMap.put("SGE", COND_SKIP);
        codeMap.put("SGT", COND_SKIP);
        codeMap.put("SLT", COND_SKIP);
        codeMap.put("B", BRANCH);
        codeMap.put("BL", BRANCH_LINK);
        codeMap.put("LSL", SHIFT);
        codeMap.put("LSR", SHIFT);
        codeMap.put("AND", AND_OR_XOR);
        codeMap.put("OR", AND_OR_XOR);
        codeMap.put("XOR", AND_OR_XOR);
        codeMap.put("LDR", LDR);
        codeMap.put("STR", STR);
        codeMap.put("POP", POP_PUSH);
        codeMap.put("PUSH", POP_PUSH);
        codeMap.put("EXIT", EXIT);
        codeMap.put("IOR", IO_READ_WRITE);
        codeMap.put("IOW", IO_READ_WRITE);
        codeMap.put("CARD", CAR_DEFINE);
        codeMap.put("CARR", CAR_REMOVE);
        codeMap.put("CASD", CAR_STACK_REGION_DEFINE);

    }

    public static Map<String, Long> optionsMap = new HashMap<>();
    static {
        optionsMap.put("MOV", 0L);
        optionsMap.put("MVN", 1L);

        // Shifting everything below by one to allow for future extensions
        optionsMap.put("BEQ", 0L);
        optionsMap.put("BNQ", 1L << 1);
        optionsMap.put("BLT", 2L << 1);
        optionsMap.put("BLE", 3L << 1);
        optionsMap.put("BGT", 4L << 1);
        optionsMap.put("BGE", 5L << 1);

        optionsMap.put("REQ", 0L);
        optionsMap.put("RNQ", 1L << 1);
        optionsMap.put("RLT", 2L << 1);
        optionsMap.put("RLE", 3L << 1);
        optionsMap.put("RGT", 4L << 1);
        optionsMap.put("RGE", 5L << 1);

        optionsMap.put("SEQ", 0L);
        optionsMap.put("SNQ", 1L << 1);
        optionsMap.put("SLE", 2L << 1);
        optionsMap.put("SGE", 3L << 1);
        optionsMap.put("SGT", 4L << 1);
        optionsMap.put("SLT", 5L << 1);

        optionsMap.put("POP", 0L);
        optionsMap.put("PUSH", 1L);

        optionsMap.put("AND", 0L);
        optionsMap.put("ORR", 1L);
        optionsMap.put("XOR", 2L);

        optionsMap.put("LSL", 0L);
        optionsMap.put("LSR", 1L);
    }

    public static Map<String, Long> registerMap = new HashMap<>();
    static {
        registerMap.put("R0", 0L);
        registerMap.put("R1", 1L);
        registerMap.put("R2", 2L);
        registerMap.put("R3", 3L);
        registerMap.put("R4", 4L);
        registerMap.put("R5", 5L);
        registerMap.put("SP", 6L);
        registerMap.put("PC", 7L);
        registerMap.put("DL", 8L);
        registerMap.put("CR", 9L);
        registerMap.put("MR", 9L);
        registerMap.put("IOP", 10L);
        registerMap.put("PS", 11L);
        registerMap.put("PL", 12L);
        registerMap.put("FP", 13L);
        registerMap.put("H0", 14L);
        registerMap.put("H1", 15L);

    }
}
