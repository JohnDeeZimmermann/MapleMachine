package de.johndee.maple.utils;

public class NumberUtils {

    public static boolean IsValidLong(String string, int radix) {
        try {
            Long.parseLong(string, radix);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean IsValidLong(String string) {
       return IsValidLong(string, 10);
    }

}
