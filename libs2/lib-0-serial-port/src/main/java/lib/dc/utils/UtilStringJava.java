package lib.dc.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class UtilStringJava {

    /**
     * 在已排序的数组中查找某个值，并返回其索引
     */
    public static int searchInArray(@NotNull String[] array, String target) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}
