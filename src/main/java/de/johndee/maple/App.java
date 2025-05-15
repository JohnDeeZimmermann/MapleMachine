package de.johndee.maple;

import de.johndee.maple.interpreter.CLIInterpreter;
import org.jetbrains.annotations.Nullable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        var cli = new CLIInterpreter();

        if (isFlagSet("pmasm", null, args)) {
            var source = requireFlag("source", "s", args);
            var target = requireFlag("target", "t", args);
            var mapletext = getFlag("mapletext", "m", null, args);

            cli.compilePmasm(source, target, mapletext);
        }

        if (isFlagSet("run", "r", args)) {
            cli.run();
        }
    }

    private static String getFlag(String flag, @Nullable String shortened, @Nullable String fallback, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("--" + flag) || args[i].equalsIgnoreCase("-" + shortened)) {
                if (args.length < i + 2) {
                    throw new IllegalArgumentException("Missing argument after flag " + flag);
                }
                return args[i + 1];
            }
        }
        return fallback;
    }

    private static String requireFlag(String flag, @Nullable String shortened, String[] args) {
        var result = getFlag(flag, shortened, null, args);
        if (result == null) {
            throw new IllegalArgumentException("Missing flag --" + flag);
        }
        return result;
    }

    private static boolean isFlagSet(String flag, @Nullable String shortened, String[] args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--" + flag) || arg.equalsIgnoreCase("-" + shortened)) {
                return true;
            }
        }
        return false;
    }
}

