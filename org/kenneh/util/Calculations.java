package org.kenneh.util;

import java.text.DecimalFormat;
import java.util.List;

public class Calculations {

    private static final long start = System.currentTimeMillis();
    private static final DecimalFormat format = new DecimalFormat("###,###,###,###");

    public static int[] toArray(List<Integer> list) {
        final int[] array = new int[list.size()];
        int count = 0;
        for(int i : list) {
            array[count] = i;
            count++;
        }
        return array;
    }


    public static String capitalize(final String string) {
        return String.valueOf(string.charAt(0)).toUpperCase() + string.substring(1).toLowerCase();
    }

    /**
     * Converts milliseconds to a String in the format
     * hh:mm:ss.
     *
     * @param time The number of milliseconds.
     * @return The formatted String.
     */
    public static String formatTime(final long time) {
        final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    /**
     *
     * Converts an integer value into a decimal formatted String
     *
     * @param value the value to be formatted
     * @return The formatted String
     */
    public static String formatNumber(int value) {
        return format.format(value);
    }

    /**
     *
     * Calculates the 'per hour' value based on an integer and the start time
     *
     * @param gained the value to get the 'per hour' of
     * @return the formatted per hour value
     */
    public static String perHour(int gained) {
        return formatNumber((int) ((gained) * 3600000D / (System.currentTimeMillis() - start)));
    }

}
