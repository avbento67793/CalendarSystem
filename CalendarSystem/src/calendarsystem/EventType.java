package calendarsystem;

/**
 * Enumeration representing different types of event priorities.
 */
public enum EventType {

    HIGH("high"), // High priority event type
    MID("mid");  // Medium priority event type
    
    private String eventType; // String representation of the event type

    /**
     * Constructor for EventType enum.
     * @param eventType The string representation of the event type.
     */
    EventType(String eventType) {
        this.eventType = eventType;
    }
    
    /**
     * Returns the string representation of the event type.
     * @return The string representation of the event type.
     */
    public String toString() {
        return this.eventType;
    }

    /**
     * Checks if the provided priority type is valid.
     * @param pType The priority type to check.
     * @return True if the priority type is valid, otherwise false.
     */
    public static boolean isPriorityTypeValid(String pType) {
        if(HIGH.toString().equals(pType)) {
            return true;
        }
        if(MID.toString().equals(pType)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the priority type is high.
     * @param pType The priority type to check.
     * @return True if the priority type is high, otherwise false.
     */
    public static boolean isHigh(String pType) {
        return EventType.HIGH.toString().equals(pType);
    }
    
    /**
     * Checks if the priority type is medium.
     * @param pType The priority type to check.
     * @return True if the priority type is medium, otherwise false.
     */
    public static boolean isMid(String pType) {
        return EventType.MID.toString().equals(pType);
    }
}
