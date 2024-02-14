package fitness;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    private int year;
    private int month;
    private int day;

    // Parameterized constructor that takes a string in the form of "mm/dd/yyyy"
    public Date(String date) {
        // Parse the date string and initialize the fields
        String[] parts = date.split("/");
        this.month = Integer.parseInt(parts[0]);
        this.day = Integer.parseInt(parts[1]);
        this.year = Integer.parseInt(parts[2]);
    }

    // Default constructor that creates a date with today's date
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        this.day = today.get(Calendar.DAY_OF_MONTH);
    }

    // Copy constructor that clones a Date object
    public Date(Date date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    // Static method to return today's date
    public static Date today() {
        return new Date();
    }

    // Method that compares two Date objects
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }

    // Check if this Date object is a valid calendar date
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

    // Helper method to determine if the year is a leap year
    private boolean isLeapYear() {
        return (year % QUADRENNIAL == 0) && ((year % CENTENNIAL != 0) || (year % QUATERCENTENNIAL == 0));
    }

    // Return the textual representation of the Date object
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", month, day, year);
    }

    // Getter methods for day, month, and year
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Check if two dates are the same date
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date other = (Date) obj;
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }
}

