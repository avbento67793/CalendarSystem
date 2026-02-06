package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface AccountInterface {

    /**
     * This method get the name of the account.
     * @return the name of the account.
     */
    String getName();

    /**
     * This method get the type of the account.
     * @return the type of the account.
     */
    AccountType getType();

    /**
     * This method add the event to the list of events of the account and also set the promoter of the event.
     * @param event The event to be added
     */
    void addPromoteEvent(Event event);
    
    /**
     * This method remove the event from the list of events in case the account is it´s promoter.
     * @param eventName The name of the event to be removed.
     */
    void removeEvent(String eventName);
    
    /**
     * This method add the event to the list of events of the account.
     * @param event The event to be added.
     */
    void addInviteeEvent(Event event);

    /**
     * This method checks if the event with the given name already exists.
     * @param eventName The name of the event.
     * @return true if the event with the given name exists.
     */
    boolean eventAlreadyExist(String eventName);

    /**
     * This method checks if the account has at least one event on the given date.
     * @param ldt The date to be checked.
     * @return true if the account has at least one event on the given date.
     */
    boolean hasEventOnDate(LocalDateTime ldt);

    /**This method get all the events that exist in that account.
     * @return a list of all the events that exist in that account.
     */
    ArrayList<Event> getAllAccountEvents();

    /**
     * This method get the event with the given name that was promoted by the account.
     * @param eventName The name of the event.
     * @return the event with the given name.
     */
    Event getPromoteEventByName(String eventName);

    /**
     * This method checks if the account has already been invited to the event.
     * @param eventName The name of the event.
     * @return true if the account has already been invited otherwise false.
     */
    boolean hasAlreadyBeenInvited(String eventName);
    
    /**
     * This method get a list of all the events that the account promoted on a specific day.
     * @param event The event to get the specific day.
     * @return a list of all the events that the account promoted on a specific day.
     */
    ArrayList<Event> getPromoteEventsListByDate(Event event);

    /**
     * This method get a list of all the events that the account was invited on a specific day.
     * @param event The event to get the specific day.
     * @return a list of all the events that the account invited on a specific day.
     */
    ArrayList<Event> getInviteeEventsListByDate(Event event);
    
    /**
     * This method get a list of all the events that the account has on a specific day.
     * @param event The event to get the specific day.
     * @return a list of all the events that the account has on a specific day.
     */
    ArrayList<Event> getConflictEventsListByDate(Event event);

    /**
     * This method checks if the account is on the invitation list of the event. 
     * @param eventName The name of the event.
     * @return true if the account is on the invitation list of the event otherwise false.
     */
    boolean isOnInvitationList(String eventName);

    /**
     * This method checks if the account has already responded to the event.
     * @param eventName The name of the event.
     * @return true if the account has already responded otherwise false.
     */
    boolean hasAlreadyResponded(String eventName);

    /**
     * This method get all the events of the account.
     * @return all the events of the account.
     */
    Events getAccountEvents();

    /**
     * This method checks if the event is accepted.
     * @param event The event to check.
     * @return true if the event is accepted otherwise false.
     */
    boolean isEventAccepted(Event event);
    
    /**
     * This method checks if the event is rejected.
     * @param event The event to check.
     * @return true if the event is rejected otherwise false.
     */
    boolean isEventRejected(Event event);
    
    /**
     * This method checks if any event on the account have at least one of the topics.
     * @param topics The list that contains the topics to be checked.
     * @return true if at least one of the events has at least on of the topics otherwise false.
     */
    boolean hasEventsWithTopic(ArrayList<String> topics);

    /**
     * This method get a list that contains the events that have at least one of the topics.
     * @param topics The list that contains the topics to be checked.
     * @return a list that contains the events that have at least one of the topics.
     */
    ArrayList<Event> getEventsWithTopics(ArrayList<String> topics);

    boolean hasHighEventOnDate(Event event);
}
