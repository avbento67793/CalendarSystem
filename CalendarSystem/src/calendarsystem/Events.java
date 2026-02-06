package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a collection of events in the calendar system.
 */
public class Events {
	// List to maintain the order of events.
    private ArrayList<Event> eventsOrder;
    
 // Map to store events by their names.
    private HashMap<String, Event> eventsMap;

    /**
     * Constructs an Events object.
     */
    public Events() {
        this.eventsOrder = new ArrayList<>();
        this.eventsMap = new HashMap<>();
    }
    
    /**
     * Adds an event to the collection of events.
     * @param event The event to be added.
     * @param accName The name of the account associated with the event.
     */
    public void addEvent(Event event, String accName) {
    	// Add the event to the list if it's not already present.
        if (!this.eventsOrder.contains(event)) {
            this.eventsOrder.add(event);
        }
        // Add the event to the map by its name.
        this.eventsMap.put(event.getName(), event);
        // If the account is the promoter, add the account to the invited and accepted list.
        if (event.isPromoter(accName)) {
            event.addInvitedAccount(accName);
            event.addAcceptedAccount(accName);
        }
    }

    /**
     * Removes an event from the collection of events.
     * @param eventName The name of the event to be removed.
     */
    public void removeEvent(String eventName) {
        // Remove the event from the map.
        this.eventsMap.remove(eventName);

        // Remove the event from the list.
        this.removeEventNameFromOrderList(eventName);
    }

    /**
     * Removes the event name from the order list.
     * @param eventName The name of the event to be removed from the order list.
     */
    private void removeEventNameFromOrderList(String eventName) {
        int index = -1;
        for (int i = 0; i < this.eventsOrder.size(); i++) {
            String name = this.eventsOrder.get(i).getName();
            if (name.equals(eventName)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.eventsOrder.remove(index);
        }
    }

    /**
     * Retrieves an event by its name.
     * @param eventName The name of the event to retrieve.
     * @return The event with the specified name, or null if not found.
     */
    public Event getEventByName(String eventName) {
        return this.eventsMap.get(eventName);
    }
    /**
     * Checks if an event exists with the given name.
     * @param eventName The name of the event to check.
     * @return True if the event exists, otherwise false.
     */
    public boolean hasEvent(String eventName) {
        return this.getEventByName(eventName) != null;
    }

    /**
     * Checks if there is an event on a specific date associated with a given account.
     * @param ldt The date to check.
     * @param accName The name of the account.
     * @return True if there is an event on the date for the specified account, otherwise false.
     */
    public boolean hasEventOnDate(LocalDateTime ldt, String accName) {
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            Event event = eventEntry.getValue();
            if(event.getDate().equals(ldt) && event.isPromoter(accName) || event.getDate().equals(ldt) && event.isEventAccepted(accName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all events in the collection.
     * @return An ArrayList containing all events.
     */
    public ArrayList<Event> getEvents() {
        ArrayList<Event> accountEvents = new ArrayList<>();

        Iterator<Event> itEvents = this.eventsOrder.iterator();
        while(itEvents.hasNext()) {
            Event event = itEvents.next();
            if (event != null) {
                accountEvents.add(event);
            }
        }
        return accountEvents;
    }

    /**
     * Retrieves a list of events on the same date as a given event, excluding the event itself.
     * @param event The event to compare dates.
     * @param accName The name of the account associated with the event.
     * @return An ArrayList containing events on the same date as the given event.
     */
    public ArrayList<Event> getEventsListByDate(Event event, String accName) {
        ArrayList<Event> eventList = new ArrayList<>();

        for (int i = 0; i < this.eventsOrder.size(); i++) {
            Event otherEvent = this.eventsOrder.get(i);

            if (!otherEvent.isEventRejected(accName)) {
                if((event.getDate().equals(otherEvent.getDate()) && !event.getName().equals(otherEvent.getName())) ||
                        (event.getDate().equals(otherEvent.getDate()) && event.getName().equals(otherEvent.getName()) && 
                                !event.getPromoterName().equals(otherEvent.getPromoterName()))) {
                    if (!eventList.contains(otherEvent)) {
                        eventList.add(otherEvent);
                    }
                }
            }
        }
        return eventList;
    }

    /**
     * Checks if an account is on the invitation list for a specific event.
     * @param accName The name of the account to check.
     * @param eventName The name of the event to check.
     * @return True if the account is on the invitation list for the event, otherwise false.
     */
    public boolean isOnInvitationList(String accName, String eventName) {
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            String eventN = eventEntry.getKey();
            Event event = eventEntry.getValue();
            if(eventN.equals(eventName) && event.isAccountInvited(accName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an account has already responded to an event (either accepted or rejected).
     * @param accName The name of the account to check.
     * @param eventName The name of the event to check.
     * @return True if the account has already responded to the event, otherwise false.
     */
    public boolean hasAlreadyResponded(String accName, String eventName) {
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            String eventN = eventEntry.getKey();
            Event event = eventEntry.getValue();
            if(eventN.equals(eventName) && (event.isEventAccepted(accName) || event.isEventRejected(accName))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rejects an event on behalf of an account.
     * @param accName The name of the account rejecting the event.
     * @param eventName The name of the event to reject.
     */
    public void rejectEvent(String accName, String eventName) {
        Event event = this.getEventByName(eventName);
        event.addRejectedAccount(accName);
    }
    /**
     * Retrieves events promoted by a specific account.
     * @param promoterName The name of the promoter account.
     * @return Events promoted by the specified account.
     */
    public Events getPromoteEvents(String promoterName){
        Events promoteEventsList = new Events();

        Iterator<Event> itEvents = this.eventsOrder.iterator();
        while(itEvents.hasNext()) {
            Event event = itEvents.next();
            if(event.isPromoter(promoterName)) {
                promoteEventsList.addEvent(event, promoterName);
            }
        }
        return promoteEventsList;
    }

    /**
     * Retrieves events to which a specific account has been invited.
     * @param inviteeName The name of the invited account.
     * @return Events to which the specified account has been invited.
     */
    public Events getInviteeEvents(String inviteeName){
        Events inviteeEventsList = new Events();

        Iterator<Event> itEvents = this.eventsOrder.iterator();
        while(itEvents.hasNext()) {
            Event event = itEvents.next();
            if (event.isAccountInvited(inviteeName)) {
                inviteeEventsList.addEvent(event, null);
            }
        }
        return inviteeEventsList;
    }

    /**
     * Retrieves a promoted event by its name.
     * @param promoterName The name of the promoter account.
     * @param eventName The name of the event.
     * @return The event promoted by the specified account with the given name, or null if not found.
     */
    public Event getPromoteEventByName(String promoterName, String eventName){
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            String otherEventName = eventEntry.getKey();
            Event event = eventEntry.getValue();
            if(otherEventName.equals(eventName) && event.isPromoter(promoterName)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Retrieves an event to which an account has been invited by its name.
     * @param inviteeName The name of the invited account.
     * @param eventName The name of the event.
     * @return The event invited to the specified account with the given name, or null if not found.
     */
    public Event getInviteeEventByName(String inviteeName, String eventName){
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            String otherEventName = eventEntry.getKey();
            Event event = eventEntry.getValue();
            if(otherEventName.equals(eventName) && event.isAccountInvited(inviteeName)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Checks if an event is accepted by a specific account.
     * @param accName The name of the account.
     * @param eventName The name of the event.
     * @return True if the event is accepted by the specified account, otherwise false.
     */
    public boolean isEventAccepted(String accName, String eventName) {
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            String otherEventName = eventEntry.getKey();
            Event event = eventEntry.getValue();
            if(otherEventName.equals(eventName) && event.isEventAccepted(accName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if an event is rejected by a specific account.
     * @param accName The name of the account.
     * @param eventName The name of the event.
     * @return True if the event is rejected by the specified account, otherwise false.
     */
    public boolean isEventRejected(String accName, String eventName) {
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            String otherEventName = eventEntry.getKey();
            Event event = eventEntry.getValue();
            if(otherEventName.equals(eventName) && event.isEventRejected(accName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is an event with any of the specified topics.
     * @param topics The list of topics to check.
     * @return True if there is an event with any of the specified topics, otherwise false.
     */
    public boolean hasEventWithTopic(ArrayList<String> topics) {
        Iterator<String> itTopics = topics.iterator();

        while(itTopics.hasNext()) {
            String topic = itTopics.next();
            for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
                Event event = eventEntry.getValue();
                if(event.hasEventWithTopic(topic)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves events that contain any of the specified topics.
     * @param topics The list of topics to filter events.
     * @return An ArrayList of events that contain any of the specified topics.
     */
    public ArrayList<Event> getEventsWithTopics(ArrayList<String> topics) {
        ArrayList<Event> eventsList = new ArrayList<>();

        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            Event event = eventEntry.getValue();
            Iterator<String> itTopics = topics.iterator();
            while(itTopics.hasNext()) {
                String topic = itTopics.next();
                if(event.getTopics().contains(topic) && !eventsList.contains(event)){
                    eventsList.add(event);
                }
            }
        }
        return eventsList;
    }
    
    /**
     * Checks if there is a high priority event on a specific date associated with a given account.
     * @param event The event to compare dates.
     * @param accName The name of the account associated with the event.
     * @return True if there is a high priority event on the date for the specified account, otherwise false.
     */
    public boolean hasHighEventOnDate(Event event, String accName) {
        for (Map.Entry<String, Event> eventEntry : eventsMap.entrySet()) {
            Event otherEvent = eventEntry.getValue();
            boolean matchDate = otherEvent.getDate().equals(event.getDate());

            if (matchDate && otherEvent.isPromoter(accName) && EventType.isHigh(otherEvent.getType())) {
                return true;
            }
            if (matchDate && otherEvent.isEventAccepted(accName) && EventType.isHigh(otherEvent.getType())) {
                return true;
            }
        }
        return false;
    }
}
