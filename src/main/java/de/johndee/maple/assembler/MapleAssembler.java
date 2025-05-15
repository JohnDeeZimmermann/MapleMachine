package de.johndee.maple.assembler;

import de.johndee.maple.exceptions.AssemblyError;
import de.johndee.maple.instructions.MapleBinaryCodes;
import de.johndee.maple.utils.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MapleAssembler implements Assembler<Long> {

    @Override
    public List<Long> assembleFile(Path path) throws IOException {
        var contents = preprocessFile(path);

        var newPath = Path.of(path.toString().replace(".masm", ".pmasm"));
        // Write file to disk
        Files.write(newPath, contents);

        return assemblePreProcessedFile(newPath);
    }

    @Override
    public List<String> preprocessFile(Path path) throws IOException {
        return loadAssemblyFile(path, "", 0);
    }

    private List<String> loadAssemblyFile(Path path, String parentLine, int lineNumber) throws IOException {
        File file = new File(path.toString());
        if (!file.exists()) {
            throw new AssemblyError(lineNumber, parentLine, "File does not exist: " + path);
        }

        var preAppendList = new ArrayList<List<String>>();

        var lines = new ArrayList<>(Files.readAllLines(path));

        for (int i = 1; i < lines.size() + 1; i++) {
            var line = lines.get(i - 1);
            if (line.strip().startsWith("include")) {
                if (line.length() < 8)
                    throw new AssemblyError(i, line, "Missing path after include");
                var pathToInclude = line.substring(7).trim();
                preAppendList.add(loadAssemblyFile(path.getParent().resolve(pathToInclude), line, i));
            }
        }

        // Now trimming all lines
        var result = lines.stream()
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .map(line -> {
                    if (line.contains("//")) {
                        return line.substring(0, line.indexOf("//")).trim();
                    } else return line;
                })
                .toList();

        return result;
    }



    public List<Long> assemblePreProcessedFile(Path path) throws IOException {
        File file = new File(path.toString());

        if (!file.exists()) {
            throw new IOException("File does not exist: " + path);
        }

        if (!file.isFile()) {
            throw new IOException("Path is not a file: " + path);
        }

        if (!file.canRead()) {
            throw new IOException("Cannot read file: " + path);
        }

        if (!file.getName().endsWith(".pmasm")) {
            throw new IOException("File is not a preprocessed maple assembly (.pmasm) file: " + path);
        }

        var lines = new ArrayList<>(Files.lines(path).toList());
        var labelMap = createLabelMapChangeLines(lines);
        var instructions = new ArrayList<Long>(lines.size());

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            try {
                instructions.add(parseLine(line, labelMap, i + 1));
            } catch (AssemblyError e) {
                e.printStackTrace();
                instructions.add(0L); //Errors will always be parsed as NOP
            }

        }

        return instructions;
    }

    private Map<String, Integer> createLabelMapChangeLines(List<String> lines) {

        var map = new HashMap<String, Integer>();

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            if (!line.startsWith(".")) {
                continue;
            }

            // This accounts for .DATA 100
            int end = line.length();
            if (line.contains(" ")) {
                end = line.indexOf(' ');
            }

            var label = line.substring(1, end);
            map.put(label, i + 1);

            var newLine = line.substring(end).trim();

            lines.set(i, newLine);
        }

        return map;
    }

    public long parseLine(String line, Map<String, Integer> labelMap, int lineNumber) {
        line = insertLabelNamesIntoLine(line, lineNumber, labelMap);
        line = replaceStringInLine(line, lineNumber);

        String instructionName = "";
        if (line.contains(" ")) {
            instructionName = line.substring(0, line.indexOf(" "));
        } else {
            instructionName = line;
        }

        if (!MapleBinaryCodes.codeMap.containsKey(instructionName)) {
            return getNumericValue(instructionName, 1, lineNumber, line, 64) >> 1;
        }

        long opCode = MapleBinaryCodes.codeMap.get(instructionName);

        if (opCode == MapleBinaryCodes.NOP)
            return 0L;
        if (opCode == MapleBinaryCodes.MOV_MVN)
            return parseMoveInstruction(line, labelMap, lineNumber, opCode);

        return parseDefaultInstruction(line, labelMap, lineNumber, opCode);
    }

    private long parseDefaultInstruction(String line,
                                         Map<String, Integer> labelMap,
                                         int lineNumber,
                                         long opCode) {

        String instructionName = "";
        String[] substrings = line.split(" ");
        instructionName = substrings[0];
        var args = substrings.length > 1 ? getArgs(instructionName, line) : new String[0];

        if (args.length > 3) {
            throw new AssemblyError(lineNumber, line, "Too many arguments for instruction.");
        }

        long options = MapleBinaryCodes.optionsMap.getOrDefault(instructionName, 0L); // Options are 4 bits in size
        long register = args.length >= 1
                ? getDefaultInstructionArgsRepresentation(args[0], 0, lineNumber, line)
                : 0; // Registers are always the first argument and 4 bits in size

        long binaryInstruction = 0;
        binaryInstruction |= opCode << (64 - 8); // Shift opcode to the correct position.
        binaryInstruction |= options << (56 - 4); // Shift options to the correct position.
        binaryInstruction |= register << (52 - 4); // Shift register to the correct position.

        for (int i = 1; i < 3 && args.length > i; i++) {
            var arg = getDefaultInstructionArgsRepresentation(args[i], i, lineNumber, line);
            binaryInstruction |= arg << (48 - 24 * (i));
        }

        return binaryInstruction;
    }

    private long parseMoveInstruction(String line,
                                      Map<String, Integer> labelMap,
                                      int lineNumber,
                                      long opCode) {

        String instructionName = "";
        String[] substrings = line.split(" ");
        instructionName = substrings[0];
        var args = substrings.length > 1 ? getArgs(instructionName, line) : new String[0];

        if (args.length != 2) {
            throw new AssemblyError(lineNumber, line, "MOV/MVN instructions require exactly two arguments.");
        }

        long options = MapleBinaryCodes.optionsMap.getOrDefault(instructionName, 0L); // Options are 4 bits in size
        long register = getMoveInstructionArgsRepresentation(args[0], 0, lineNumber, line); // Register is always the first argument and 4 bits in size

        long binaryInstruction = 0;
        binaryInstruction |= opCode << (64 - 8); // Shift opcode to the correct position.
        binaryInstruction |= options << (56 - 1); // Shift options to the correct position.
        binaryInstruction |= register << (55 - 4); // Shift register to the correct position.
        binaryInstruction |= getMoveInstructionArgsRepresentation(args[1], 1, lineNumber, line);

        return binaryInstruction;
    }

    public long getDefaultInstructionArgsRepresentation(String args, int index, int lineNumber, String line) {
        args = args.trim();

        int valueLength;

        valueLength = switch (index) {
            case 0 -> 4;
            default -> 24;
        };

        return getNumericValue(args, index, lineNumber, line, valueLength);
    }

    public long getMoveInstructionArgsRepresentation(String args, int index, int lineNumber, String line) {
        args = args.trim();

        int valueLength;

        if (index >= 2) {
            throw new AssemblyError(lineNumber, line, "Too many arguments for MOV instruction.");
        }

        valueLength = switch (index) {
            case 0 -> 4;
            case 1 -> 51;
            default -> 0;
        };

        return getNumericValue(args, index, lineNumber, line, valueLength);
    }

    private long getNumericValue(String args, int index, int lineNumber, String line, int valueLength) {
        boolean isRegister;
        long numericValue;

        if (line.isBlank()) return 0L;

        if (args.startsWith("#")) {
            isRegister = false;

            if (index == 0)
                throw new AssemblyError(lineNumber, line, "First argument must always be a register.");

            // Parsing a number
            args = args.substring(1);

            if (args.startsWith("0b")) {
                if (!NumberUtils.IsValidLong(args.substring(2), 2))
                    throw new AssemblyError(lineNumber, line, "Invalid binary number: " + args);

                numericValue = Long.parseLong(args.substring(2), 2);
            } else if (args.startsWith("0x")) {
                if (!NumberUtils.IsValidLong(args.substring(2), 16))
                    throw new AssemblyError(lineNumber, line, "Invalid hexadecimal number: " + args);

                numericValue = Long.parseLong(args.substring(2), 16);
            } else {
                if (!NumberUtils.IsValidLong(args))
                    throw new AssemblyError(lineNumber, line, "Invalid number: " + args);

                numericValue = Long.parseLong(args);
            }
        } else {
            isRegister = true;

            String name = args.toUpperCase(Locale.US);

            if (!MapleBinaryCodes.registerMap.containsKey(name))
                throw new AssemblyError(lineNumber, line, "Invalid register: " + args);

            numericValue = MapleBinaryCodes.registerMap.get(name);
        }

        if (valueLength != 64 && numericValue >= (1L << (valueLength)))
            throw new AssemblyError(lineNumber, line, "Value " + (isRegister ? "register" : "number") + ": " + args + " (" + numericValue + ") exceeds maximum possible length: " + valueLength + " bits.");

        if (index == 0)
            return numericValue;

        return (numericValue << 1L) | (isRegister ? 1L : 0L);
    }

    private String[] getArgs(String instructionName, String line) {
        var args = line.substring(instructionName.length()).split(",");

        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].trim();
        }

        return args;
    }

    private String insertLabelNamesIntoLine(String line, int lineNumber, Map<String, Integer> labelMap) {
        String result = line;

        for (Map.Entry<String, Integer> entry : labelMap.entrySet()) {
            String labelName = entry.getKey();
            String labelValue = "PS, #" + entry.getValue().toString();

            String escapedLabelName = labelName.replaceAll("[\\[\\]{}()*+?.^$|]", "\\\\$0");

            String pattern = "@" + escapedLabelName + "\\b";

            result = result.replaceAll(pattern, labelValue);
        }

        return result;
    }

    private String replaceStringInLine(String line, int lineNumber) {
        StringBuilder result = new StringBuilder();
        int currentPos = 0;

        while (currentPos < line.length()) {
            int quoteStart = line.indexOf("\"", currentPos);
            if (quoteStart == -1) {
                // No more strings found, append the rest of the line
                result.append(line.substring(currentPos));
                break;
            }

            // Check if this quote is escaped
            if (quoteStart > 0 && line.charAt(quoteStart - 1) == '\\') {
                // This is an escaped quote, append everything up to and including it and continue
                result.append(line, currentPos, quoteStart + 1);
                currentPos = quoteStart + 1;
                continue;
            }

            // Append everything before the quote
            result.append(line, currentPos, quoteStart);

            // Find the closing quote, taking into account escaped quotes
            int quoteEnd = quoteStart + 1;
            while (true) {
                quoteEnd = line.indexOf("\"", quoteEnd);
                if (quoteEnd == -1) {
                    throw new AssemblyError(lineNumber, line, "Unclosed string literal");
                }
                // Check if this quote is escaped
                if (quoteEnd > 0 && line.charAt(quoteEnd - 1) == '\\') {
                    quoteEnd++; // Skip this quote and continue searching
                    continue;
                }
                break; // Found unescaped closing quote
            }

            // Extract the string content and unescape any escaped quotes
            String stringContent = line.substring(quoteStart + 1, quoteEnd).replace("\\\"", "\"");
            if (stringContent.length() > 8) {
                throw new AssemblyError(lineNumber, line, "String literal exceeds maximum length of 8 characters");
            }

            // Convert to hex representation
            StringBuilder hexValue = new StringBuilder("#0x");

            // Fill with zeros for remaining bytes (each byte is 2 hex digits)
            for (int i = stringContent.length(); i < 8; i++) {
                hexValue.append("00");
            }

            // Add ASCII values in reverse order
            for (int i = stringContent.length() - 1; i >= 0; i--) {
                String hexByte = String.format("%02X", (int) stringContent.charAt(i));
                hexValue.append(hexByte);
            }

            result.append(hexValue);
            currentPos = quoteEnd + 1;
        }

        return result.toString();
    }
}
