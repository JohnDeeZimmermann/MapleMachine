package de.johndee.maple.assembler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Assembler<Word extends Number> {

    /**
     * Assemble a preprocessed file. This means that the file is already preprocessed by the Pre-Assembler.
     * @param path The path to the .pmasm file.
     * @return The assembled file.
     */
    List<Word> assemblePreProcessedFile(Path path) throws IOException;
    List<Word> assembleFile(Path path);


}
