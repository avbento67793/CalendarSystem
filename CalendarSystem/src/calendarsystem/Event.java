package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an event in the calendar system.
 */
public class Event implements EventInterface {

    private String name;
    private String type;
    private LocalDateTime ldt;
    private ArrayList<String> topics;
    private String promoterName;

    /** Contains all invited account names. */
    private ArrayList<String> invited;

    /** Contains all accepted account names. */
    private ArrayList<String> accepted;

    /** Contains all rejected account names. */
    private ArrayList<String> rejected;

    /**
     * Initializes an Event with the specified details.
     * 
     * @param name The name of the event.
     * @param type The type of the event.
     * @param ldt The date and time of the event.
     * @param topics The topics associated with the event.
     */
    public Event(String name, String type, LocalDateTime ldt, String[] topics) {
        this.name = name;
        this.type = type;
        this.ldt = ldt;
        this.topics = new ArrayList<>(Arrays.asList(topics));
        this.promoterName = null;
        this.invited = new ArrayList<>();
        this.accepted = new ArrayList<>();
        this.rejected = new ArrayList<>();
    }

    /**
     * Gets the name of the event.
     * 
     * @return The name of the event.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of the event.
     * 
     * @return The type of the event.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the date and time of the event.
     * 
     * @return The date and time of the event.
     */
    public LocalDateTime getDate() {
        return this.ldt;
    }

    /**
     * Gets the topics associated with the event.
     * 
     * @return The list of topics.
     */
    public ArrayList<String> getTopics() {
        return this.topics;
    }

    /**
     * Checks if the specified account is the promoter of the event.
     * 
     * @param accName The name of the account.
     * @return true if the account is the promoter, false otherwise.
     */
    public boolean isPromoter(String accName) {
        return this.promoterName != null && this.promoterName.equals(accName);
    }

    /**
     * Gets the number of invited accounts.
     * 
     * @return The number of invited accounts.
     */
    public int getInviteStatus() {
        return this.invited.size();
    }

    /**
     * Gets the number of accepted invitations.
     * 
     * @return The number of accepted invitations.
     */
    public int getAcceptedStatus() {
        return this.accepted.size();
    }

    /**
     * Gets the number of rejected invitations.
     * 
     * @return The number of rejected invitations.
     */
    public int getRejectedStatus() {
        return this.rejected.size();
    }

    /**
     * Gets the number of unanswered invitations.
     * 
     * @return The number of unanswered invitations.
     */
    public int getUnansweredStatus() {
        int count = 0;
        for (String accName : this.invited) {
            if (!this.accepted.contains(accName) && !this.rejected.contains(accName)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the specified account is invited to the event.
     * 
     * @param inviteeName The name of the account.
     * @return true if the account is invited, false otherwise.
     */
    public boolean isAccountInvited(String inviteeName) {
        return this.invited.contains(inviteeName);
    }

    /**
     * Checks if the specified account has accepted the event invitation.
     * 
     * @param inviteeName The name of the account.
     * @return true if the account has accepted, false otherwise.
     */
    public boolean isEventAccepted(String inviteeName) {
        return this.accepted.contains(inviteeName);
    }

    /**
     * Checks if the specified account has rejected the event invitation.
     * 
     * @param inviteeName The name of the account.
     * @return true if the account has rejected, false otherwise.
     */
    public boolean isEventRejected(String inviteeName) {
        return this.rejected.contains(inviteeName);
    }

    /**
     * Adds an account to the invited list.
     * 
     * @param accName The name of the account.
     */
    public void addInvitedAccount(String accName) {
        if (!this.invited.contains(accName)) {
            this.invited.add(accName);
        }
    }

    /**
     * Removes an account from all lists (invited, accepted, rejected).
     * 
     * @param accName The name of the account.
     */
    public void removeInvitedAccount(String accName) {
        this.invited.remove(accName);
        this.accepted.remove(accName);
        this.rejected.remove(accName);
    }

    /**
     * Adds an account to the accepted list.
     * 
     * @param accName The name of the account.
     */
    public void addAcceptedAccount(String accName) {
        this.rejected.remove(accName);
        if (!this.accepted.contains(accName)) {
            this.accepted.add(accName);
        }
    }

    /**
     * Adds an account to the rejected list.
     * 
     * @param accName The name of the account.
     */
    public void addRejectedAccount(String accName) {
        this.accepted.remove(accName);
        if (!this.rejected.contains(accName)) {
            this.rejected.add(accName);
        }
    }

    /**
     * Sets the promoter name for the event.
     * 
     * @param name The name of the promoter.
     */
    public void setPromoterName(String name) {
        this.promoterName = name;
    }

    /**
     * Gets the promoter name of the event.
     * 
     * @return The name of the promoter.
     */
    public String getPromoterName() {
        return this.promoterName;
    }

    /**
     * Gets a list of all invited account names.
     * 
     * @return A list of all invited account names.
     */
    public ArrayList<String> getAllInvitedNames() {
        return new ArrayList<>(this.invited);
    }

    /**
     * Checks if the event has a specific topic.
     * 
     * @param topic The topic to check.
     * @return true if the event has the topic, false otherwise.
     */
    public boolean hasEventWithTopic(String topic) {
        return this.topics.contains(topic);
    }

    /**
     * Counts how many of the specified topics are associated with the event.
     * 
     * @param topics The list of topics to check.
     * @return The number of matching topics.
     */
    public int countMatchingTopics(ArrayList<String> topics) {
        int count = 0;
        for (String topic : topics) {
            if (this.topics.contains(topic)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the event is a high-level event.
     * 
     * @return true if the event is a high-level event, false otherwise.
     */
    public boolean isHighEvent() {
        return EventType.isHigh(this.getType());
    }
}
