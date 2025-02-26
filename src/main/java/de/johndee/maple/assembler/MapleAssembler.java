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

    @Override
    public List<Long> assembleFile(Path path) {
        return List.of();
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

        // Insert label name
        for (var labelName : labelMap.keySet()) {
            line = line.replace("@" + labelName, "PS, #" + labelMap.get(labelName).toString());
        }


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

}
