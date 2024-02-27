package fitness;

import static org.junit.Assert.*;

public class DateTest {

    @org.junit.Test
    public void testDaysInFeb_NonLeap() {
        Date date = new Date("2/30/2013");
        assertFalse(date.isValid());
    }

    @org.junit.Test
    public void testDaysInFeb_Leap(){
        Date date = new Date("2/29/2024");
        assertTrue(date.isValid());
    }

}