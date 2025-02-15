package de.johndee.tests.assembler;

import de.johndee.maple.assembler.MapleAssembler;
import org.junit.Assert;
import org.junit.Test;

public class ArgumentTests {

    @Test
    public void testDefaultArguments() {
        var assembler = new MapleAssembler();

        String arg1 = "R1";
        String arg2 = "#16";
        String arg3 = "R2";

        long expected1 = 0b0001L;
        long expected2 = 0b10_0000L;
        long expected3 = 0b00101L;

        long result = assembler.getDefaultInstructionArgsRepresentation(
                arg1, 0, 0, arg1
        );
        long result2 = assembler.getDefaultInstructionArgsRepresentation(
                arg2, 1, 0, arg2
        );
        long result3 = assembler.getDefaultInstructionArgsRepresentation(
                arg3, 1, 1, arg3
        );

        Assert.assertEquals(expected1, result);
        Assert.assertEquals(expected2, result2);
        Assert.assertEquals(expected3, result3);
    }

}
