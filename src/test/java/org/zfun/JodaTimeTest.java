package org.zfun;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.junit.Test;

/**
 * User: skuo Date: Apr 26, 2011
 */
public class JodaTimeTest {

    @Test
    public void testSimple() {
        // setting DateTime and DateMidnight
        DateTime dt = new DateTime();
        System.out.format("DateTime with default DateTimeZone = %s\n", dt);
        DateTime gmtDt = new DateTime(DateTimeZone.UTC);
        System.out.format("DateTime with default DateTimeZone.UTC = %s\n", gmtDt);
        DateMidnight gmtDm = new DateMidnight(DateTimeZone.UTC);
        System.out.format("DateMidnight with default DateTimeZone.UTC = %s\n", gmtDm);

        // Test period with UTC time
        Period oneDay = Period.days(1);
        DateMidnight midnightToday = new DateMidnight(DateTimeZone.UTC);
        DateTime now = new DateTime(DateTimeZone.UTC);
        if (now.plus(oneDay).isBefore(midnightToday))
            System.out.println("now is inactive");
        else
            System.out.println("now is active");
        DateTime oneDayAgoNow = now.minusDays(1);
        if (oneDayAgoNow.plus(oneDay).isBefore(midnightToday))
            System.out.println("oneDayAgoNow is inactive");
        else
            System.out.println("oneDayAgoNow is active");
        DateTime twoDaysAgoNow = now.minusDays(2);
        if (twoDaysAgoNow.plus(oneDay).isBefore(midnightToday))
            System.out.println("twoDaysAgoNow is inactive");
        else
            System.out.println("twoDaysAgoNow is active");
        System.out.println();
    }

    @Test
    public void testTimestamp() {
        // Test timestamp as long. TimeZone is UTC time
        Period oneDay = Period.days(1);
        long oneDayMillis = oneDay.toStandardDuration().getMillis();
        DateMidnight midnightToday = new DateMidnight(DateTimeZone.UTC);
        long midnightTodayTimestamp = midnightToday.getMillis();
        long nowTimestamp = System.currentTimeMillis();
        System.out.format("midnightTodayTimestamp=%d, nowTimestamp=%d\n", midnightTodayTimestamp, nowTimestamp);
        if (midnightTodayTimestamp - nowTimestamp > oneDayMillis)
            System.out.println("now is inactive");
        else
            System.out.println("now is active");
        DateTime now = new DateTime(DateTimeZone.UTC);
        long oneDayAgoNowTimestamp = now.minusDays(1).getMillis();
        if (midnightTodayTimestamp - oneDayAgoNowTimestamp > oneDayMillis)
            System.out.println("oneDayAgoNow is inactive");
        else
            System.out.println("oneDayAgoNow is active");
        long twoDaysAgoNowTimestamp = now.minusDays(2).getMillis();
        if (midnightTodayTimestamp - twoDaysAgoNowTimestamp > oneDayMillis)
            System.out.println("twoDaysAgoNow is inactive");
        else
            System.out.println("twoDaysAgoNow is active");
        System.out.println();
    }

    @Test
    public void testCalendar() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        Date now = cal.getTime();
        System.out.format("now = %s\n", sdf.format(now));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date nowMidnight = cal.getTime();
        System.out.format("nowMidnight = %s\n", sdf.format(nowMidnight));
        System.out.format("nowMidnight=%d, now=%d\n", nowMidnight.getTime(), now.getTime());

        System.out.println();
    }
}
