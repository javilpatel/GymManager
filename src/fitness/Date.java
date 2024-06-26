/**
 * Represents a calendar date with year, month, and day fields.
 * It checks the validity of a date and compares dates. This class
 * also includes methods to get the current date and check for leap years.
 * The class follows the coding standards provided for CS 213 Spring 2024.
 *
 * @author Javil Patel
 */
package fitness;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    private final int year;
    private final int month;
    private final int day;

    /**
     * Constructs a Date object from a string representation "mm/dd/yyyy".
     * If the date string is invalid, throws IllegalArgumentException.
     *
     * @param date the string representation of the date.
     * @throws IllegalArgumentException if the date is invalid.
     */
    public Date(String date) {
        // Parse the date string and initialize the fields
        String[] parts = date.split("/");
        this.month = Integer.parseInt(parts[0]);
        this.day = Integer.parseInt(parts[1]);
        this.year = Integer.parseInt(parts[2]);
    }

    /**
     * Constructs a Date object with today's date.
     */
    public Date(int i, int i1, int i2) {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH) + 1;
        this.day = today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Copy constructor for Date.
     *
     * @param date the Date object to clone.
     */
    public Date(Date date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    /**
     * Returns today's date as a Date object.
     *
     * @return the current date.
     */
    public static Date today() {
        Calendar cal = Calendar.getInstance();
        return new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    // Method that compares two Date objects
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }

    /**
     * Checks if this Date object represents a valid calendar date.
     *
     * @return true if the date is valid, false otherwise.
     */
    public boolean isValid() {
        if (month < 1 || month > 12 || day < 1) return false;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return day <= 30;
            case 2:
                return (isLeapYear() && day <= 29) || (!isLeapYear() && day <= 28);
            default:
                return day <= 31;
        }
    }

    /**
     * Determines if the year is a leap year.
     *
     * @return true if the year is a leap year, false otherwise.
     */
    private boolean isLeapYear() {
        return (year % QUADRENNIAL == 0) && ((year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0));
    }

    /**
     * Checks if this Date object represents a date in the future relative to today.
     *
     * @return true if this date is in the future, false otherwise.
     */
    public boolean isFutureDate() {
        Calendar today = Calendar.getInstance();
        Calendar thisDate = Calendar.getInstance();
        thisDate.set(year, month - 1, day); // Calendar months are 0-based
        return thisDate.after(today);
    }

    /**
     * Calculates the age in years based on this Date object, assuming it represents a birth date.
     *
     * @return the age in years.
     */
    public int calculateAge() {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(year, month - 1, day); // Setting the birth date

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--; // Adjust if we're before the birthday this year
        }
        return age;
    }

    /**
     * Returns a string representation of this Date object in "mm/dd/yyyy" format.
     *
     * @return A string representing the date, ensuring zero padding for month and day for single digits.
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", month, day, year);
    }

    /**
     * Returns the day of the month represented by this Date object.
     *
     * @return The day of the month (1-31).
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the month represented by this Date object.
     *
     * @return The month of the year (1-12).
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the year represented by this Date object.
     *
     * @return The year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Compares this Date object with another object for equality.
     *
     * @param obj The object to compare this Date against.
     * @return true if the given object represents a Date equivalent to this date, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date other = (Date) obj;
        return year == other.year && month == other.month && day == other.day;
    }

}