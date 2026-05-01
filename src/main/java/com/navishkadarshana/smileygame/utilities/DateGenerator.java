package com.navishkadarshana.smileygame.utilities;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Component
@Log4j2
public class DateGenerator {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT_24_HOUR_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_FORMAT_12_HOUR_FORMAT = "yyyy-MM-dd hh:mm a";
    private static final String DATE_TIME_FORMAT_WITH_MILI = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String END_TIME_OF_DAY = "23:59:59.000000";
    private static final String START_TIME_OF_DAY = "00:00:00.000000";
    private static final String FINAL_FORMAT = "%s %s";
    private static final String OVERDUE_TIME = "12:30:00.000000";
    private static final String CURRENT_TIME_FORMAT = "hh:mm:ss a";
    private static final String TIME_ZONE_UTC = "UTC";
    private static final String TIME_ZONE_ASIA_COLOMBO = "IST";

    public enum TimeZoneType {
        UTC, ASIA_COLOMBO
    }

    public Date[] getStartEndTimeOfCurrentDate() {
        try {

            String currentDate = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern(DATE_FORMAT));

            String startDateTime = String.format(FINAL_FORMAT, currentDate, START_TIME_OF_DAY);
            String endDateTime = String.format(FINAL_FORMAT, currentDate, END_TIME_OF_DAY);
            String overdueDateTime = String.format(FINAL_FORMAT, currentDate, OVERDUE_TIME);

            Date startDate = new SimpleDateFormat(DATE_TIME_FORMAT_WITH_MILI).parse(startDateTime);

            Date endDate = new SimpleDateFormat(DATE_TIME_FORMAT_WITH_MILI).parse(endDateTime);

            Date overDueDate = new SimpleDateFormat(DATE_TIME_FORMAT_WITH_MILI).parse(overdueDateTime);

            return new Date[]{startDate, endDate, overDueDate};

        } catch (ParseException e) {
            log.error("Method getStartEndTimeOfCurrentDate : " + e.getMessage());
            return new Date[]{new Date(), new Date(), new Date()};
        }
    }

    public String convertDateToString24HourFormat(Date date, TimeZoneType timeZoneType) {
        return getDateFromString(date, timeZoneType, DATE_TIME_FORMAT_24_HOUR_FORMAT);
    }

    public String convertDateToString12HourFormat(Date date, TimeZoneType timeZoneType) {
        return getDateFromString(date, timeZoneType, DATE_TIME_FORMAT_12_HOUR_FORMAT);
    }

    private String getDateFromString(Date date, TimeZoneType timeZoneType, String dateTimeFormat) {
        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_24_HOUR_FORMAT);
                String timeZone = TIME_ZONE_ASIA_COLOMBO;
                if (timeZoneType.equals(TimeZoneType.UTC)) timeZone = TIME_ZONE_UTC;
                return convertDateToLocalOrUTC(simpleDateFormat.format(date), timeZone, dateTimeFormat);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getUtcNowForTransDate(Date date) {
        try {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(date); // sets calendar time/date
            cal.add(Calendar.MINUTE, + 330); // add 5 hours
            return cal.getTime(); // returns new date object, one hour in the future
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }


    public static Date getPreviousDateTime(Date date) {
        try {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(date); // sets calendar time/date
            cal.add(Calendar.DAY_OF_YEAR, -1);
            return cal.getTime(); // returns new date object, one hour in the future
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public Date convertLongDateValueToDate(long longdate, int type) {
        // type 1 - END_TIME_OF_DAY, 0 - START_TIME_OF_DAY
        try {
            Date date = new Date(longdate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            String formatDate;
            if (type == 1) {
                formatDate = String.format(FINAL_FORMAT, simpleDateFormat.format(date), END_TIME_OF_DAY);
            } else {
                formatDate = String.format(FINAL_FORMAT, simpleDateFormat.format(date), START_TIME_OF_DAY);
            }
            return new SimpleDateFormat(DATE_TIME_FORMAT_WITH_MILI).parse(formatDate);
        } catch (ParseException e) {
            log.error("Method getStartEndTimeOfCurrentDate : " + e.getMessage());
            return new Date();
        }
    }

    public Date changeDateFromMinutes(Date date, int minutes){
        if(date != null){
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(date); // sets calendar time/date
            cal.add(Calendar.MINUTE, minutes); // plus or minus min
            return cal.getTime();
        }
        return null;
    }

    public Date convertStringDateValueToDate(String stringDate, int type) {
        // type 1 - END_TIME_OF_DAY, 0 - START_TIME_OF_DAY
        // string date format 2021/7/19
        try {
            stringDate = stringDate.replace("/", "-");
            String formatDate;
            if (type == 1) {
                formatDate = String.format(FINAL_FORMAT, stringDate, END_TIME_OF_DAY);
            } else {
                formatDate = String.format(FINAL_FORMAT, stringDate, START_TIME_OF_DAY);
            }
            return new SimpleDateFormat(DATE_TIME_FORMAT_WITH_MILI).parse(formatDate);
        } catch (ParseException e) {
            log.error("Method convertStringDateValueToDate : " + e.getMessage(), e);
            return new Date();
        }
    }

    public Date getCurrentDateStartTime() {
        try {
            String stringDate = getCurrentDate();

            String formatDate = String.format(FINAL_FORMAT, stringDate, START_TIME_OF_DAY);

            return new SimpleDateFormat(DATE_TIME_FORMAT_WITH_MILI).parse(formatDate);
        } catch (ParseException e) {
            log.error("Method convertStringDateValueToDate : " + e.getMessage(), e);
            return new Date();
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(new Date());
    }


    static Date getUtcNowForTrans(String pattern, String dateString) {
        try {
            DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = format.parse(dateString);

            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(date); // sets calendar time/date
            cal.add(Calendar.MINUTE, -330); // minus 5 hours
            return cal.getTime(); // returns new date object, one hour in the future
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public Date getDateOfDateTime(Date date) {
        try {
            String format = new SimpleDateFormat(DATE_FORMAT).format(date);
            return new SimpleDateFormat(DATE_FORMAT).parse(format);
        } catch (ParseException e) {
            return null;
        }
    }

    public String convertLongDateValueToStringDate(long longDate) {
        try {
            Date date = new Date(longDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    private String convertDateToLocalOrUTC(String utcTime, String timeZoneType, String datePattern) {
        try {
            if (utcTime != null) {

                SimpleDateFormat utcFormatter = new SimpleDateFormat(DATE_TIME_FORMAT_24_HOUR_FORMAT, Locale.ENGLISH);
                utcFormatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));
                Date gpsUTCDate = utcFormatter.parse(utcTime);

                SimpleDateFormat localFormatter = new SimpleDateFormat(datePattern, Locale.ENGLISH);
                localFormatter.setTimeZone(TimeZone.getTimeZone(timeZoneType));

                return localFormatter.format(gpsUTCDate.getTime());
            }
            return null;
        } catch (Exception e) {
            log.error("Method convertDateToLocalOrUTC : " + e.getMessage());
            return null;
        }
    }

    public Date changeHoursOfDate(Date currentDate, int minutes) {
        if (currentDate != null) {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(currentDate); // sets calendar time/date
            cal.add(Calendar.MINUTE, minutes); // plus or minus hours
            return cal.getTime();
        }
        return null;
    }

    public Date getLastDateOfYear(Date date) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.MONTH, 11);
        lastDate.set(Calendar.DAY_OF_MONTH, 31);
        return lastDate.getTime();
    }


    public Date getFirstDateOfYear(Date date) {
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(Calendar.MONTH, 0);
        firstDate.set(Calendar.DAY_OF_MONTH, 1);
        return firstDate.getTime();
    }


}
