package fitness;

/**
 * Enum representing different times of the day for scheduling fitness classes.
 */
public enum Time {
    /**
     * Represents the morning time slot for a fitness class.
     */
    MORNING(9, 30),
    /**
     * Represents the afternoon time slot for a fitness class.
     */
    AFTERNOON(14, 0),
    /**
     * Represents the evening time slot for a fitness class.
     */
    EVENING(18, 30);

    private final int hour;
    private final int minute;

    /**
     * Constructs a Time instance with specified hour and minute.
     *
     * @param hour   The hour of the time slot.
     * @param minute The minute of the time slot.
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the hour of the time slot.
     *
     * @return The hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute of the time slot.
     *
     * @return The minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Returns a string representation of the time slot in HH:MM format.
     *
     * @return A string representing the time slot.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}

