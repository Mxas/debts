package eu.fourFinance.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * Returns the given date new instance with time set to the begining of the day
     * 
     * @param date
     * @return
     */
    public Date getDateDayStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date.getTime()));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Returns the given date new instance with time set to the end of the day
     * 
     * @param date
     * @return
     */
    public Date getDateDayEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date.getTime()));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
    /**
     * Returns the given date new instance with time set to the end of the day
     * 
     * @param date
     * @return
     */
    public Date addHoursToCurrentDay(int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}
