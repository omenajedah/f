package com.firman.finansial;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Firman on 5/5/2019.
 */
public class Utils {

    public static Date parseDate(String source, String pattern) throws ParseException {
        if (source == null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.parse(source);
    }

    public static String formatDate(Date source, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(source);
    }

    public static String formatDecimalIDR(long idr) {
        DecimalFormat kursIDR = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp");
        formatRp.setGroupingSeparator('.');
        kursIDR.setDecimalFormatSymbols(formatRp);

        return kursIDR.format(idr);
    }

    public static String formatIDR(long idr) {
        return NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(idr);
    }
}
