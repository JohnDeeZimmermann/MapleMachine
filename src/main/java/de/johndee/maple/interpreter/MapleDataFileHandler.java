package de.johndee.maple.interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Meant to load .maple and .mapletext files
 */
public class MapleDataFileHandler {

    public static Long[] getDataFromMapleFile(Path filePath) throws IOException {

        if (!Files.isRegularFile(filePath, LinkOption.NOFOLLOW_LINKS)) {
            throw new UnsupportedOperationException("The provided path is no file: " + filePath);
        }

        if (!filePath.getFileName().toString().contains(".maple")) {
            throw new UnsupportedOperationException("The provided file is not a .maple file: " + filePath);
        }

        byte[] bytes = Files.readAllBytes(filePath);

        if (bytes.length % 8 != 0) {
            System.err.println("WARNING: Data in file " + filePath + " is incomplete. Trailing data is stripped.");
        }

        Long[] data = new Long[bytes.length / 8];

        for (int i = 0; i < bytes.length / 8; i++) {
            long entry = 0L;

            for (int j = 0; j<8; j++) {
                entry = entry | (data[i * 8 + j] << 8);
            }

            data[i] = entry;
        }

        return data;
    }

    public static Long[] getDataFromMapleTextFile(Path filePath) throws IOException {

        if (!Files.isRegularFile(filePath, LinkOption.NOFOLLOW_LINKS)) {
            throw new UnsupportedOperationException("The provided path is no file: " + filePath);
        }

        if (!filePath.getFileName().toString().contains(".maple")) {
            throw new UnsupportedOperationException("The provided file is not a .maple file: " + filePath);
        }

        var lines = Files.readAllLines(filePath);


        Long[] data = new Long[lines.size()];

        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            line = line.replace(" ", "")
                    .replace("\t", "").
                    replace("\n", "");

            long entry = 0L;

            try {
                entry = Long.parseLong(line, 2);
            } catch (NumberFormatException e) {
                System.err.println("There is a formatting error in line " + (i+1) + ": " + line + "\nLine is being interpreted as 0000.");
                e.printStackTrace();
            }

            data[i] = entry;
        }

        return data;
    }

    public static void writeMapleFile(Path path, Long[] data) throws IOException {
        byte[] bytes = new byte[data.length * 8];

        for (int i = 0; i<data.length; i++) {
            long value = data[i];
            for (int j = 0; j<8; j++) {
                bytes[i * 8 + j] = (byte) (value >> (8 * j));
            }
        }

        Files.write(path, bytes);
    }

}
