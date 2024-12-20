package de.johndee.maple.assembler;

import de.johndee.maple.exceptions.AssemblyError;
import de.johndee.maple.instructions.MapleBinaryCodes;
import de.johndee.maple.utils.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MapleAssembler implements Assembler<Long>{

    public List<Long> assemblePreProcessedFile(Path path) throws IOException {
        File file = new File(path.toString());

        if (!file.exists()) {
            System.err.println("File does not exist: " + path);
            return List.of();
        }

        if (!file.isFile()) {
            System.err.println("Path is not a file: " + path);
            return List.of();
        }

        if (!file.canRead()) {
            System.err.println("Cannot read file: " + path);
            return List.of();
        }

        if (!file.getName().endsWith(".masm")) {
            System.err.println("File is not a preprocessed maple assembly (.pmasm) file: " + path);
            return List.of();
        }

        var lines = Files.lines(path).toList();
        var labelMap = createLabelMapChangeLines(lines);
        var instructions = new ArrayList<Long>(lines.size());

        for (int i = 0; i<lines.size(); i++) {
            var line = lines.get(i);
            try {
                instructions.add(parseLine(line, labelMap, i+1));
            } catch (AssemblyError e) {
                e.printStackTrace();
                instructions.add(0L); //Errors will always be parsed as NOP
            }

        }

        return null;
    }

    @Override
    public List<Long> assembleFile(Path path) {
        return List.of();
    }

    private Map<String, Integer> createLabelMapChangeLines(List<String> lines) {

        var map = new HashMap<String, Integer>();

        for (int i = 0; i<lines.size(); i++) {
            var line = lines.get(i);
            if (!line.startsWith(".")) {
                continue;
            }

            int end = 100;
            if (line.contains(" ")) {
                end = line.indexOf(' ');
            }

            var label = line.substring(1, end);
            map.put(label, i);

            var newLine = line.substring(end).trim();

            lines.set(i, newLine);
        }

        return map;
    }

    private long parseLine(String line, Map<String, Integer> labelMap, int lineNumber) {

        // Insert label name
        for (var labelName : labelMap.keySet()) {
            line = line.replace("@" + labelName, "PS, " + labelMap.get(labelName).toString());
        }


        String command = "";
        if (line.contains(" ")) {
            command = line.substring(0, line.indexOf(" "));
        } else {
            command = line;
        }


        if (!MapleBinaryCodes.codeMap.containsKey(command)) {
            throw new AssemblyError(lineNumber, line, "Instruction " + command
                    + " not known. Please reference the manual for a list of instructions.");
        }



        long opCode = MapleBinaryCodes.codeMap.get(command);

        if (opCode == MapleBinaryCodes.MOV_MVN) {
            //return parseMoveInstruction(line, labelMap, lineNumber);
        } else {
            return parseDefaultInstruction(line, labelMap, lineNumber, opCode);
        }

        return 0L; //NOP

    }

    private long parseDefaultInstruction(String line,
                                         Map<String, Integer> labelMap,
                                         int lineNumber,
                                         long opCode) {




        return 0;


    }

    private long getDefaultInstructionArgsRepresentation(String args, int index, int lineNumber, String line) {
        args = args.trim();

        int valueLength;
        boolean isRegister;
        long numericValue;

        valueLength = switch (index) {
            case 0 -> 4;
            default -> 24;
        };


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
            }

            else if (args.startsWith("0x")) {
                if (!NumberUtils.IsValidLong(args.substring(2), 16))
                    throw new AssemblyError(lineNumber, line, "Invalid hexadecimal number: " + args);

                numericValue = Long.parseLong(args.substring(2), 16);
            }

            else {
                if (!NumberUtils.IsValidLong(args))
                    throw new AssemblyError(lineNumber, line, "Invalid number: " + args);

                numericValue = Long.parseLong(args);
            }
        }
        else {
            isRegister = true;

            String name = args.toUpperCase(Locale.US);

            if (!MapleBinaryCodes.registerMap.containsKey(name))
                throw new AssemblyError(lineNumber, line, "Invalid register: " + args);

            numericValue = MapleBinaryCodes.registerMap.get(name);
        }


        if (numericValue >= (1L << (valueLength - 1)))
            throw new AssemblyError(lineNumber, line, "Value too large for " + (isRegister ? "register" : "number") + ": " + args);

        return (numericValue << 1L) | (isRegister ? 1L : 0L);
    }

    private String[] getArgs(String instructionName, String line) {
        var args = line.substring(instructionName.length()).split(",");

        for (int i = 0; i<args.length; i++) {
            args[i] = args[i].trim();
        }

        return args;
    }

}
