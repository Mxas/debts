package eu.fourFinance.utils;

import static eu.fourFinance.utils.DateUtils.getDateDayEnd;
import static eu.fourFinance.utils.DateUtils.getDateDayStart;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.fourFinance.testsuites.categories.UnitTests;

@Category(UnitTests.class)
public class DateUtilsTest {

    @Test
    public void testGetDateDayStart() {

        Calendar c = Calendar.getInstance();
        c.setTime(getDateDayStart(new Date()));

        assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, c.get(Calendar.MINUTE));
        assertEquals(0, c.get(Calendar.SECOND));
        assertEquals(0, c.get(Calendar.MILLISECOND));

        c.add(Calendar.MILLISECOND, -1);

        assertEquals(23, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, c.get(Calendar.MINUTE));
        assertEquals(59, c.get(Calendar.SECOND));
        assertEquals(999, c.get(Calendar.MILLISECOND));

    }

    @Test
    public void testGetDateDayEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(getDateDayEnd(new Date()));

        assertEquals(23, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, c.get(Calendar.MINUTE));
        assertEquals(59, c.get(Calendar.SECOND));
        assertEquals(999, c.get(Calendar.MILLISECOND));

        c.add(Calendar.MILLISECOND, 1);

        assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, c.get(Calendar.MINUTE));
        assertEquals(0, c.get(Calendar.SECOND));
        assertEquals(0, c.get(Calendar.MILLISECOND));

    }

}
