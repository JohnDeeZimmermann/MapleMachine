package de.johndee.maple.interpreter;

import de.johndee.maple.exceptions.IllegalMemoryAccessException;
import de.johndee.maple.impl.Maple64;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class CLIInterpreter {

    private Maple64 maple = new Maple64();

    public void run() {

        boolean running = true;

        Scanner input = new Scanner(System.in);

        while (running) {
            String in = input.nextLine();
            String[] parts = in.split(" ");


            if (parts.length == 0) {
                System.out.println("Please enter a command.");
                continue;
            }

            else if (parts[0].equalsIgnoreCase("help")) {
                System.out.println("Commands:");
                System.out.println("lf [PATH] [ADDRESS] - Load a file into memory at the specified address.");
                System.out.println("rf [ADDRESS] - Run the program starting from the specified address.");
                System.out.println("peek [ADDRESS] - Peek at the memory at the specified address.");
                System.out.println("lr - Lists the register values.");
                System.out.println("exit - Exit the interpreter.");
            }

            else if (parts[0].equalsIgnoreCase("lf") || parts[0].equalsIgnoreCase("loadfile")) {
                if (parts.length < 3) {
                    System.out.println("Wrong argument count: Try >lf [PATH] [ADDRESS]");
                    continue;
                }
                try {
                    loadFile(parts[1], Long.parseLong(parts[2]));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number: Try >lf [PATH] [ADDRESS]");
                    continue;
                }
            }

            else if (parts[0].equalsIgnoreCase("rf") || parts[0].equalsIgnoreCase("runfrom")) {
                if (parts.length < 2) {
                    System.out.println("Wrong argument count: Try >rf [ADDRESS]");
                    continue;
                }
                try {
                    runFrom(Long.parseLong(parts[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number: Try >rf [ADDRESS]");
                    continue;
                }
            }

            else if (parts[0].equalsIgnoreCase("peek")) {
                if (parts.length < 2) {
                    System.out.println("Wrong argument count: Try >peek [ADDRESS]");
                    continue;
                }
                try {
                    peek(Long.parseLong(parts[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number: Try >peek [ADDRESS]");
                    continue;
                }
            }

            else if (parts[0].equalsIgnoreCase("lr") || parts[0].equalsIgnoreCase("listregisters")) {
                listRegisters();
            }

            else if (parts[0].equalsIgnoreCase("exit")) {
                running = false;
            }
        }
    }

    private void loadFile(String file, long address) {

        Long[] data;
        if (file.endsWith(".maple")) {
            try {
                data = MapleDataFileHandler.getDataFromMapleFile(Path.of(file));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else if (file.endsWith(".mapletext")) {
            try {
                data = MapleDataFileHandler.getDataFromMapleTextFile(Path.of(file));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("Invalid file type. Please use .maple or .mapletext files.");
            return;
        }

        try {
            maple.getMemoryDevice().write(address, data, -1L);
        } catch (IllegalMemoryAccessException e) {
            e.printStackTrace();
        }

    }

    private void runFrom(long address) {
        try {
            maple.run(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void peek(long address) {
        try {
            System.out.println(Arrays.toString(maple.getMemoryDevice().read(address, 8L, -1L)));
        } catch (IllegalMemoryAccessException e) {
            e.printStackTrace();
        }
    }

    private void listRegisters() {
        System.out.println(Arrays.toString(maple.getRegisters()));
    }
}
