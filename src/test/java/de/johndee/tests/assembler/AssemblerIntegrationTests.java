package de.johndee.tests.assembler;

import de.johndee.maple.assembler.MapleAssembler;
import de.johndee.maple.exceptions.IllegalMemoryAccessException;
import de.johndee.maple.impl.Maple64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblerIntegrationTests {

    private Maple64 maple;
    private MapleAssembler assembler;

    private static Stream<Arguments> provideAssemblyFiles() throws IOException {
        Path resourceDirectory = Paths.get("src", "test", "resources", "pmasm");
        return Files.list(resourceDirectory)
                .filter(file -> file.toString().endsWith(".pmasm"))
                .map(Arguments::of);
    }

    @BeforeEach
    public void setUp() {
        maple = new Maple64();
        assembler = new MapleAssembler();
    }

    @ParameterizedTest
    @MethodSource("provideAssemblyFiles")
    void testAssemblyFile(Path assemblyFile) throws IOException, IllegalMemoryAccessException {
        // Assemble the code
        List<Long> code = assembler.assemblePreProcessedFile(assemblyFile);

        // Load the code into memory
        maple.getMemoryDevice().write(0L, code.toArray(new Long[0]), 0L);

        // Execute the program
        maple.run(0L);

        // Check register 0 for the result
        assertEquals(0, maple.getRegisterValue(0),
                "Program in file " + assemblyFile.getFileName() + " should result in 0 in register 0");
    }
}