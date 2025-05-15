package de.johndee.maple.assembler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Assembler<Word extends Number> {

    /**
     * Preprocesses a given file by reading its raw content and performing necessary transformations
     * or operations to prepare it for further processing.
     *
     * @param path The path to the file to be preprocessed.
     * @return The file contents which could be further parsed using the regular assembler
     * @throws IOException If an I/O error occurs while reading the file.
     */
    List<String> preprocessFile(Path path) throws IOException;

    /**
     * Assemble a preprocessed file. This means that the file is already preprocessed by the Pre-Assembler.
     * @param path The path to the .pmasm file.
     * @return The assembled file.
     */
    List<Word> assemblePreProcessedFile(Path path) throws IOException;


    /**
     * Assemble a file from its raw source. This will invoke the preprocessor as well.
     *
     * @param path The path to the source file. The file is expected to contain assembly code.
     * @return The assembled file as a list of binary-encoded instructions.
     */
    List<Word> assembleFile(Path path) throws IOException;
}
