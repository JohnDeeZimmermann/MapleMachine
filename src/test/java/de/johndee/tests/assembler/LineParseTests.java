package de.johndee.tests.assembler;

import de.johndee.maple.assembler.MapleAssembler;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class LineParseTests {

    @Test
    public void testDefaultParseLine() {
        var labelMap = new HashMap<String, Integer>();
        MapleAssembler assembler = new MapleAssembler();

        String instr1 = "ADDI r1, r2, #16";

        long expected1 = 0b0000001000000001000000000000000000000101000000000000000000100000L;
        long result1 = assembler.parseLine(instr1, labelMap, 0);

        Assert.assertEquals(expected1, result1);
    }

    @Test
    public void parseNumber() {
        var labelMap = new HashMap<String, Integer>();
        MapleAssembler assembler = new MapleAssembler();

        String instr1 = "#16";
        String instr2 = "#0";
        String instr3 = "#0b1000";

        long expected1 = 16L;
        long expected2 = 0L;
        long expected3 = 8L;

        long result1 = assembler.parseLine(instr1, labelMap, 0);
        long result2 = assembler.parseLine(instr2, labelMap, 0);
        long result3 = assembler.parseLine(instr3, labelMap, 0);

        Assert.assertEquals(expected1, result1);
        Assert.assertEquals(expected2, result2);
        Assert.assertEquals(expected3, result3);
    }


    @Test
    public void testMoveParseLine() {
        var labelMap = new HashMap<String, Integer>();
        MapleAssembler assembler = new MapleAssembler();

        String instr1 = "MOV r2, #128";

        long expected1 = 0b0000000100010000000000000000000000000000000000000000000100000000L;

        long result1 = assembler.parseLine(instr1, labelMap, 0);

        Assert.assertEquals(expected1, result1);
    }


}
