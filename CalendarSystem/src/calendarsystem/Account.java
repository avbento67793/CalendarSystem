package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Abstract class representing an account in the calendar system.
 */
public abstract class Account implements AccountInterface{

    // The name of the account.
    private String name;

    // The type of the account.
    private AccountType type;

    // The events of the account (that he promoted or was invited).
    private Events accEvents;

    /**
     * Initializes the constructor account with the name, the type and the events that it has.
     * @param name The name of the account.
     * @param type The type of the account.
     */
    public Account(String name, AccountType type) {
        this.name = name;
        this.type = type;
        this.accEvents = new Events();
    }

    /**
     * Gets the name of the account.
     * @return the account's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of the account.
     * @return the account's type.
     */
    public AccountType getType() {
        return this.type;
    }

    /**
     * Adds an event promoted by the account.
     * @param event The event to be promoted.
     */
    public void addPromoteEvent(Event event) {
        event.setPromoterName(this.getName());
        this.accEvents.addEvent(event, this.getName());
    }

    /**
     * Removes an event by its name.
     * @param eventName The name of the event to be removed.
     */
    public void removeEvent(String eventName) {
        this.accEvents.removeEvent(eventName);
    }

    /**
     * Adds an event where the account is invited.
     * @param event The event to be added.
     */
    public void addInviteeEvent(Event event) {
        this.accEvents.addEvent(event, null);
    }

    /**
     * Checks if an event with the given name already exists.
     * @param eventName The name of the event to check.
     * @return true if the event already exists, false otherwise.
     */
    public boolean eventAlreadyExist(String eventName) {
        return this.getPromoteEventByName(eventName) != null;
    }

    /**
     * Checks if the account has an event on a specific date.
     * @param ldt The date to check.
     * @return true if the account has an event on the given date, false otherwise.
     */
    public boolean hasEventOnDate(LocalDateTime ldt) {
        return this.accEvents.hasEventOnDate(ldt, this.getName());
    }

    /**
     * Gets all events associated with the account.
     * @return a list of all events associated with the account.
     */
    public ArrayList<Event> getAllAccountEvents() {
        return this.accEvents.getEvents();
    }

    /**
     * Gets the account's events.
     * @return the account's events.
     */
    public Events getAccountEvents() {
        return this.accEvents;
    }

    /**
     * Gets a promoted event by its name.
     * @param eventName The name of the event.
     * @return the event if found, null otherwise.
     */
    public Event getPromoteEventByName(String eventName){
        return this.accEvents.getPromoteEventByName(this.getName(), eventName);
    }

    /**
     * Checks if the account has already been invited to an event by its name.
     * @param eventName The name of the event.
     * @return true if the account has already been invited, false otherwise.
     */
    public boolean hasAlreadyBeenInvited(String eventName){
        return this.accEvents.getInviteeEventByName(this.getName(), eventName) != null;
    }

    /**
     * Gets a list of promoted events by date.
     * @param event The event to compare dates with.
     * @return a list of promoted events by date.
     */
    public ArrayList<Event> getPromoteEventsListByDate(Event event){
        Events promoteEvents = this.accEvents.getPromoteEvents(this.getName());
        return promoteEvents.getEventsListByDate(event, this.getName());
    }

    /**
     * Gets a list of invited events by date.
     * @param event The event to compare dates with.
     * @return a list of invited events by date.
     */
    public ArrayList<Event> getInviteeEventsListByDate(Event event){
        Events inviteeEvents = this.accEvents.getInviteeEvents(this.getName());
        return inviteeEvents.getEventsListByDate(event, this.getName());
    }

    /**
     * Gets a list of conflict events by date.
     * @param event The event to compare dates with.
     * @return a list of conflict events by date.
     */
    public ArrayList<Event> getConflictEventsListByDate(Event event){
        return this.accEvents.getEventsListByDate(event, this.getName());
    }

    /**
     * Checks if the account is on the invitation list for an event.
     * @param eventName The name of the event.
     * @return true if the account is on the invitation list, false otherwise.
     */
    public boolean isOnInvitationList(String eventName) {
        Events inviteeEvents = this.accEvents.getInviteeEvents(this.getName());
        return inviteeEvents.isOnInvitationList(this.getName(), eventName);
    }

    /**
     * Checks if the account has already responded to an event invitation.
     * @param eventName The name of the event.
     * @return true if the account has already responded, false otherwise.
     */
    public boolean hasAlreadyResponded(String eventName) {
        Events inviteeEvents = this.accEvents.getInviteeEvents(this.getName());
        return inviteeEvents.hasAlreadyResponded(this.getName(), eventName);
    }

    /**
     * Rejects an event invitation.
     * @param eventName The name of the event.
     */
    public void rejectEvent(String eventName) {
        Events inviteeEvents = this.accEvents.getInviteeEvents(this.getName());
        inviteeEvents.rejectEvent(this.getName(), eventName);
    }

    /**
     * Checks if an event is accepted by the account.
     * @param event The event to check.
     * @return true if the event is accepted, false otherwise.
     */
    public boolean isEventAccepted(Event event) {
        return this.accEvents.isEventAccepted(this.getName(), event.getName());
    }

    /**
     * Checks if an event is rejected by the account.
     * @param event The event to check.
     * @return true if the event is rejected, false otherwise.
     */
    public boolean isEventRejected(Event event) {
        return this.accEvents.isEventRejected(this.getName(), event.getName());
    }

    /**
     * Checks if the account has events with specific topics.
     * @param topics The list of topics to check.
     * @return true if the account has events with the given topics, false otherwise.
     */
    public boolean hasEventsWithTopic(ArrayList<String> topics) {
        return this.accEvents.hasEventWithTopic(topics);
    }

    /**
     * Gets events with specific topics.
     * @param topics The list of topics to filter events by.
     * @return a list of events with the given topics.
     */
    public ArrayList<Event> getEventsWithTopics(ArrayList<String> topics){
        return this.accEvents.getEventsWithTopics(topics);
    }

    /**
     * Checks if there is a high priority event on the same date as the given event.
     * @param event The event to compare dates with.
     * @return true if there is a high priority event on the same date, false otherwise.
     */
    public boolean hasHighEventOnDate(Event event) {
        return this.accEvents.hasHighEventOnDate(event, this.getName());
    }
}
