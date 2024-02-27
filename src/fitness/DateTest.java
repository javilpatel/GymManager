package fitness;

import static org.junit.Assert.*;

public class DateTest {

    // Valid Case 1
    @org.junit.Test
    public void testDaysInFeb_Leap(){
        Date date = new Date("2/29/2024");
        assertTrue(date.isValid());
    }

    // Valid Case 2
    @org.junit.Test
    public void testDaysInFeb_Leap1(){
        Date date = new Date("2/29/2000");
        assertTrue(date.isValid());
    }

    // Invalid Case 1
    @org.junit.Test
    public void testDaysInFeb_NonLeap() {
        Date date = new Date("2/29/1987");
        assertFalse(date.isValid());
    }

    // Invalid Case 2
    @org.junit.Test
    public void testDaysInFeb_NonLeap1() {
        Date date = new Date("2/29/2015");
        assertFalse(date.isValid());
    }

    // Invalid Case 3
    @org.junit.Test
    public void testDaysInFeb_NonLeap2() {
        Date date = new Date("2/29/2007");
        assertFalse(date.isValid());
    }

    // Invalid Case 4
    @org.junit.Test
    public void testDaysInFeb_NonLeap3() {
        Date date = new Date("2/29/2019");
        assertFalse(date.isValid());
    }

    // Invalid Case 5
    @org.junit.Test
    public void testDaysInFeb_NonLeap4() {
        Date date = new Date("2/29/1645");
        assertFalse(date.isValid());
    }

}