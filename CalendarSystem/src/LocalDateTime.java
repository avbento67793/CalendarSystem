/**
 * Represents a simplified version of LocalDateTime.
 */
public class LocalDateTime {
    
    // Fields to store year, month, day, and hour
    private int year;
    private int month;
    private int day;
    private int hour;

    /**
     * Constructor for creating a LocalDateTime object with the specified parameters.
     * @param year The year component of the date and time.
     * @param month The month component of the date and time.
     * @param day The day component of the date and time.
     * @param hour The hour component of the date and time.
     */
    public LocalDateTime(int year, int month, int day, int hour) {
        // Initialize the fields with the provided values
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }
}
