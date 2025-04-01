package de.johndee.tests.assembler;

import de.johndee.maple.interpreter.MapleDataFileHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

public class FileWriteTest {

    @Test
    public void testWriteRead() throws IOException {
        Long[] data = new Long[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (long) i;
        }

        MapleDataFileHandler.writeMapleFile(Path.of("src/test/resources/written.maple"), data);
        // Read file again
        var onDisk = MapleDataFileHandler.getDataFromMapleFile(Path.of("src/test/resources/written.maple"));

        Assert.assertEquals(data, onDisk);
    }

}
