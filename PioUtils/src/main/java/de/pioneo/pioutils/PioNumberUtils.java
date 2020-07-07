package de.pioneo.pioutils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PioNumberUtils {

    /**
     * Format your given number to German Number Style (1000 --> 1.000)
     *
     * @param i the number that
     * @return the formatted number as String
     */
    public static String getGermanNumberFormat(long i) {
        return NumberFormat.getNumberInstance(Locale.GERMANY).format(i);
    }

    /**
     * Format your given number to US Number Style (1000 --> 1,000)
     *
     * @param i the number that
     * @return the formatted number as String
     */
    public static String getAmericanNumberFormat(long i) {
        //*****************************************************************************************
        //*****************************************************************************************
        //**                                      NOT TESTED                                     **
        //*****************************************************************************************
        //*****************************************************************************************
        return NumberFormat.getNumberInstance(Locale.US).format(i);
    }


    /**
     * Format your Float value to Decimal Format with x digits.
     *
     * @param f      given Float value
     * @param digits number of digits for Decimal Format
     * @return the formatted Float value
     */
    public static Float getDecimalNumber(float f, int digits) {
        //*****************************************************************************************
        //*****************************************************************************************
        //**                                      NOT TESTED                                     **
        //*****************************************************************************************
        //*****************************************************************************************
        StringBuilder format = new StringBuilder();
        format.append("0.");
        for (int i = 1; i <= digits; i++) {
            format.append("#");
        }
        DecimalFormat decimalFormat = new DecimalFormat(format.toString());
        return Float.parseFloat(decimalFormat.format(f));
    }

    /**
     * Format given value, appending 'k' once it goes above 1000.
     */
    public static String getKiloValueFromNumber(long value) {
        if (value < 1000) {
            return String.valueOf(value);
        } else {
            return String.format(Locale.GERMAN, "%.1f", value / 1000f)
                    .replace(".0", "") + "k";
        }
    }
}
