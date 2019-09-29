package utils;

import com.sun.istack.NotNull;

public class StringUtils {



    public static boolean isInRange(@NotNull String toCheck, int minLen, int maxLen)
    {
        return toCheck.length() >= minLen && toCheck.length() <= maxLen;
    }



    public static boolean isNumber(@NotNull String toCheck)
    {
        return toCheck.matches("[0-9]+");
    }
}
