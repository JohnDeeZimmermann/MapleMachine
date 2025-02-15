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

}
