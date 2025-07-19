package it.lysz210.fluitrix.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    public static <T> List<T> asMutableList(T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }
}
