package com.incon.connect.utils;


import com.incon.connect.AppConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils implements AppConstants.DateFormatterConstants {
    private static HashMap<String, String> monthNames = new HashMap<>();

    static {

        monthNames.put("01", "Jan");
        monthNames.put("02", "Feb");
        monthNames.put("03", "Mar");
        monthNames.put("04", "Apr");
        monthNames.put("05", "May");
        monthNames.put("06", "June");
        monthNames.put("07", "July");
        monthNames.put("08", "Aug");
        monthNames.put("09", "Sep");
        monthNames.put("10", "Oct");
        monthNames.put("11", "Nov");
        monthNames.put("12", "Dec");
    }
    /**
     * Returns Today, Yesterday, the day of the week within one week, or a
     * symptomSelectedDate if greater.
     * @param milliSeconds
     * @return
     */
    public static String formatTimeDay(long milliSeconds) {
        Date currentDate = new Date();

        String timeBarDayText = "";
        long differenceInMillis = currentDate.getTime() - milliSeconds;

        long differenceInSeconds = differenceInMillis / 1000;
        if (differenceInSeconds < 60) {
            timeBarDayText = differenceInSeconds + "s ago";
            return timeBarDayText;
        }

        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
        if (differenceInMinutes < 60) {
            timeBarDayText = differenceInMinutes + "m ago";
            return timeBarDayText;
        }

        long differenceInHours = TimeUnit.MILLISECONDS.toHours(differenceInMillis);
        if (differenceInHours < 24) {
            timeBarDayText = differenceInHours + "h ago";
            return timeBarDayText;
        }

        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
        if (differenceInDays < 7) {
            timeBarDayText = differenceInDays + "d ago";
            return timeBarDayText;
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(differenceInMillis);
        int differenceInMonths = c.get(Calendar.MONTH);

        long differenceInWeeks = TimeUnit.MILLISECONDS.toDays(differenceInMillis) / 7;
        if (differenceInWeeks < 4 || differenceInMonths == 0) {
            timeBarDayText = differenceInWeeks == 1 ? differenceInWeeks + "week ago"
                    : differenceInWeeks + "weeks ago";
            return timeBarDayText;
        }


        if (differenceInMonths < 12) {
            timeBarDayText = differenceInMonths == 1 ? differenceInMonths + "month ago"
                    : differenceInMonths + "months ago";
            return timeBarDayText;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        int differenceInYears = ValidationUtils.calculateAge(calendar); //milliseconds are
        if (differenceInYears > 0) {
            timeBarDayText = differenceInYears == 1 ? differenceInYears + "year ago"
                    : differenceInYears + "years ago";
            return timeBarDayText;
        }

        return timeBarDayText;
    }






    /////////////////////////////////After api changes
    public static String convertFormattedDateToDateUsingTimezone(String inputTimezone, String
            inputDate, String inputDateFormat, String outputDateFormat) {

        try {

            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone(inputTimezone)); // missing line
            Date parsedInputDate = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getTimeZone(inputTimezone)); // missing line

            return writeDate.format(parsedInputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String convertDateToOtherFormat(Date inputDate, String outputDateFormat) {
        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            String s = writeDate.format(inputDate);

            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date convertStringFormatToDate(String inputDate, String inputDateFormat) {

        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(inputDateFormat, DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            Date outputDate = writeDate.parse(inputDate);
            return outputDate;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long convertStringFormatToMillis(String inputDate, String inputDateFormat) {

        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(inputDateFormat, DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            Date outputDate = writeDate.parse(inputDate);
            return outputDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String convertMillisToStringFormat(long milliSeconds,
                                                     String outputDateFormat) {
        try {
            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            Date date = new Date(milliSeconds);

            return writeDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertStringFormatToOtherStringFormat(String inputDateFormat,
                                                                String inputDate,
                                                                String outputDateFormat) {
        try {
            SimpleDateFormat readDateFormatter = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            Date readDate = readDateFormatter.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);

            return writeDate.format(readDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static long getLocalMillisFromUTCDate(String utcDate, String utcDateFormat) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(utcDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line
            Date date = readDate.parse(utcDate);


            SimpleDateFormat writeDate = new SimpleDateFormat(LOCAL_DATE_DD_MM_YYYY_HH_MM_SS,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            Date outputDate = writeDate.parse(writeDate.format(date));

            return outputDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    //////////////////////////////////

    public static long getUTCMillisFromUTCDate(String utcDateFormat, String utcDate) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(utcDateFormat, DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line
            Date date = readDate.parse(utcDate);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }



    public static long getLocalMillisFromUTCMillis(long utcMilliSeconds) {

        try {

            Date utcDate = new Date(utcMilliSeconds);
            Calendar calendar = setCalendarToUtcTimeZone(Calendar.getInstance());
            calendar.setTime(utcDate);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(calendar.getTimeInMillis());
            SimpleDateFormat writeDate = new SimpleDateFormat(DD_MM_YYYY,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            Date outputDate = writeDate.parse(writeDate.format(calendar.getTime()));

            return outputDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }



    public static String getUTCFormattedDateFromUTCDate(String utcInputDate, String inputDateFormat,
                                                        String utcDateFormat) {

        try {

            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line
            Date inputDate = readDate.parse(utcInputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(utcDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line

            return writeDate.format(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getUTCFormatFromUTCMillis(long utcMilliSeconds, String utcDateFormat) {

        try {

            Calendar utcCalendar = setCalendarToUtcTimeZone(Calendar.getInstance());
            utcCalendar.setTimeInMillis(utcMilliSeconds);
            utcCalendar.setTimeZone(TimeZone.getTimeZone("UTC"));


            SimpleDateFormat writeDate = new SimpleDateFormat(utcDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line

            Date date = utcCalendar.getTime();

            return writeDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getUTCFormatFromLocalMillis(long localMilliSeconds,
                                                     String outputDateFormat) {
        try {
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(localMilliSeconds);
            Calendar utcCalendar = setCalendarToUtcTimeZone(localCalendar);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = utcCalendar.getTime();

            return writeDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date getDateFromLocalMillis(long localMilliSeconds) {

        try {

            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(localMilliSeconds);
            return localCalendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStringFormatFromLocalMillis(long localMilliSeconds,
                                                        String outputDateFormat) {
        try {

            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(localMilliSeconds);
            Date date = localCalendar.getTime();

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);

            return writeDate.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String utcToLocal(String inputDate, String inputDateFormat,
                                    String outputDateFormat) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(
                    inputDateFormat, DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getLocalDateFromUtc(String inputDate, String inputDataFormat,
                                             String outputDateFormat) {
        try {
            SimpleDateFormat readDate = new SimpleDateFormat(inputDataFormat, DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String utcToLocal(String inputDate, String outputDateFormat) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(
                    FROM_API,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getTimeZone("UTC")); // missing line
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String convertMillisToDatFormat(long timeInMillis, String timeZone,
                                                  String outputDateFormat) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));

        SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                DATE_FORMAT_LOCALE);
        writeDate.setTimeZone(calendar.getTimeZone());

        return writeDate.format(calendar.getTime());

    }

    public static Calendar setCalendarToUtcTimeZone(Calendar calendarInstance) {
        Date date = new Date(calendarInstance.getTimeInMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat sdf = new SimpleDateFormat(
                FROM_API,
                DATE_FORMAT_LOCALE);
        sdf.setTimeZone(TimeZone.getDefault());
        String newdt = sdf.format(date);
        try {
            date = sdf.parse(newdt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar1.setTime(date);

        return calendar1;
    }
    ///////////////////////////////////////////////////////

    /**
     * This method converts the symptomSelectedDate from string.
     *
     * @param milliseconds time
     */
    public static String convertMillsToDateString(String format, long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        return dateFormat.format(calendar.getTime());
    }


    public static String convertUTCMillsToDateString(String format, long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static String getDateFromDayMonthYear(String year, String nameOfMonth, int dayOfMonth) {
        return  year + "-"
                + DateUtils.convertMonthNameToNumber(nameOfMonth)
                + "-" + DateUtils.convertSingleDigitToTwoDigits(
                dayOfMonth);
    }

    public static Date convertStringToDate(String inputDate, String inputDateFormat,
                                           String outputDateFormat) {
        try {
            String dtc = inputDate;
            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getDefault()); // missing line
            Date date = readDate.parse(dtc);
            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());
            String s = writeDate.format(date);
            Date date1 = writeDate.parse(s);

            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDateToAnotherFormat(String inputDate, String inputDateFormat,
                                                    String outputDateFormat) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(inputDateFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(TimeZone.getDefault()); // missing line
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputDateFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(TimeZone.getDefault());

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String convertMonthNameToNumber(String monthName) {
        Date date;
        try {
            date = new SimpleDateFormat("MMMM",
                    DATE_FORMAT_LOCALE).parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return convertSingleDigitToTwoDigits(cal.get(Calendar.MONTH) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertSingleDigitToTwoDigits(int num) {
        return num <= 9 ? "0" + num : String.valueOf(num);
    }


    public static long convertDateToUTCMilliseconds(String format, String date) {
        SimpleDateFormat writeDate = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        writeDate.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date outputDate = writeDate.parse(date);
            return outputDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long convertToMilliseconds(String format, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                DATE_FORMAT_LOCALE);
        try {
            Date outputDate = dateFormat.parse(date);
            return outputDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long localToUtcInMillis(long time, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    format, DATE_FORMAT_LOCALE);
            Date date = new Date(time);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String strDate = dateFormat.format(date);

            SimpleDateFormat dateFormatLocal = new SimpleDateFormat(
                    format, DATE_FORMAT_LOCALE);
            Date utcDate = dateFormatLocal.parse(strDate);
            return utcDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long utcToLocalInMillis(long utcTime, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    format, DATE_FORMAT_LOCALE);
            Date date = new Date(utcTime);
            dateFormat.setTimeZone(TimeZone.getDefault());
            String strDate = dateFormat.format(date);

            SimpleDateFormat dateFormatLocal = new SimpleDateFormat(
                    format, DATE_FORMAT_LOCALE);
            Date utcDate = dateFormatLocal.parse(strDate);
            return utcDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utcTime;
    }


    public static String convertTimeFormat(String inputDate,
                                           String inputFormat, String outputFormat,
                                           TimeZone inputTimeZone, TimeZone outputTimezone) {

        try {
            SimpleDateFormat readDate = new SimpleDateFormat(inputFormat,
                    DATE_FORMAT_LOCALE);
            readDate.setTimeZone(inputTimeZone);
            Date date = readDate.parse(inputDate);

            SimpleDateFormat writeDate = new SimpleDateFormat(outputFormat,
                    DATE_FORMAT_LOCALE);
            writeDate.setTimeZone(outputTimezone);

            return writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getMonthName(String month) {

        return monthNames.get(month);
    }

}
