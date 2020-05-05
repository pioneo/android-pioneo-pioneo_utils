package de.pioneo.pioutils;

import java.text.NumberFormat;
import java.util.Locale;

public class PioNumberUtils {

    /**
     * Format your given number to German Number Style (1000 --> 1.000)
     * @param i the number that
     * @return the formatted number as String
     */
    public static String makeThousandSeperators(long i) {
        return NumberFormat.getNumberInstance(Locale.GERMANY).format(i);
    }

    /**
     * Format given value, appending 'k' once it goes above 1000.
     */
    public static String appendThousandsSeperator(int value) {
        if(value < 1000) {
            return String.valueOf(value);
        } else {
            return String.format(Locale.GERMAN,"%.1f", value / 1000f).replace(".0", "") + "k";
        }
    }
}
