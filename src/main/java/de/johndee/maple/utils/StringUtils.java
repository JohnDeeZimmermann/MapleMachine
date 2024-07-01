package de.johndee.maple.utils;

public class StringUtils {

    public static boolean matchAny(String s, String... compare) {
        for (String c : compare) {
            if (c.equals(s)) return true;
        }

        return false;
    }

}
