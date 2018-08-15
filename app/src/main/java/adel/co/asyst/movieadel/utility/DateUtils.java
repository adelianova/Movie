package adel.co.asyst.movieadel.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String currentFormat, String resultFormat, String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentFormat, Locale.getDefault());
        Date newDate = null;
        try {
            newDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormatResult = new SimpleDateFormat(resultFormat, Locale.getDefault());
        return simpleDateFormatResult.format(newDate);
    }

    public static Date dateFromString(String currentFormat, String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentFormat, Locale.getDefault());
        Date newDate = null;
        try {
            newDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
