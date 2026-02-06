package calendarsystem;

/**
 * Enumeration representing the response to an event invitation, either ACCEPT or REJECT.
 */
public enum EventResponse {

    ACCEPT("accept"),
    REJECT("reject");

    private String eventResponse;

    /**
     * Constructs an EventResponse enumeration with the given response string.
     * 
     * @param eventResponse The string representation of the event response.
     */
    EventResponse(String eventResponse) {
        this.eventResponse = eventResponse;
    }
    
    /**
     * Returns the string representation of the event response.
     * 
     * @return The string representation of the event response.
     */
    public String toString() {
        return this.eventResponse;
    }
    
    /**
     * Checks if the given response string is a valid event response.
     * 
     * @param response The response string to check for validity.
     * @return true if the response string is valid (ACCEPT or REJECT), false otherwise.
     */
    public static boolean isResponseValid(String response) {
        return (ACCEPT.toString().equals(response)) || (REJECT.toString().equals(response));
    }
}
