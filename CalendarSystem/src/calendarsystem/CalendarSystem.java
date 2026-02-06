package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarSystem implements CalendarSystemInterface {

    // The accounts that exist in the system.
    Accounts accounts;

    /**
     * Initializes the constructor with new accounts.
     */
    public CalendarSystem() {
        this.accounts = new Accounts();
    }

    /**
     * This method checks if already an account already exists with the given name.
     * @param accName The account name.
     * @return true if the account already exists, false otherwise.
     */
    public boolean accountAlreadyExists(String accName) {
        return accounts.accountAlreadyExists(accName);
    }

    /**
     * This method adds an account to the system.
     * @param accName The name of the account.
     * @param accType The type of the account.
     */
    public void addAccount(String accName, String accType) {
        accounts.addAccount(accName, accType);
    }

    /**
     * This method gets a list of the accounts sorted by alphabetical order.
     * @return a sorted list of the accounts by alphabetical order.
     */
    public ArrayList<AccountInterface> getSortedList(){
        return accounts.sortedList();
    }

    /**
     * This method gets the account with the given name.
     * @param accName The name of the account.
     * @return the account with the given name.
     */
    public AccountInterface getAccountByName(String accName) {
        return accounts.getAcountByName(accName);
    }

    /**
     * This method adds an event to the system.
     * @param accName The name of the account that promotes the event.
     * @param pType The priority type of the event.
     * @param eventName The name of the event.
     * @param ldt The date of the event.
     * @param topics The topics of the event.
     */
    public void addEvent(String accName, String pType, String eventName, LocalDateTime ldt, String[] topics) {
        AccountInterface acc = this.getAccountByName(accName);
        Event event = new Event(eventName, pType, ldt, topics);
        acc.addPromoteEvent(event);
    }

    /**
     * This method checks if the event with the given name already exists in that account.
     * @param accName The name of the account.
     * @param eventName The name of the event to be checked.
     * @return true if already exists otherwise false.
     */
    public boolean eventAlreadyExists(String accName, String eventName) {
        AccountInterface acc = this.getAccountByName(accName);
        return acc.eventAlreadyExist(eventName);
    }

    /**
     * This method checks if the account is a guest.
     * @param accName The name of the account to be checked.
     * @return true if the account is a guest, false otherwise.
     */
    public boolean isGuestAccount(String accName) {
        AccountInterface acc = this.getAccountByName(accName);
        return AccountType.isGuest(acc.getType());
    }

    /**
     * This method checks if the account is a staff.
     * @param accName The name of the account to be checked.
     * @return true if the account is a staff, false otherwise.
     */
    public boolean isStaffAccount(String accName) {
        AccountInterface acc = this.getAccountByName(accName);
        return AccountType.isStaff(acc.getType());
    }

    /**
     * This method checks if the account already has an event on the given date.
     * @param accName The name of the account.
     * @param ldt The date that will be checked.
     * @return true if the account already has an event on that date, false otherwise.
     */
    public boolean hasEventOnDate(String accName, LocalDateTime ldt) {
        AccountInterface acc = this.getAccountByName(accName);
        return acc.hasEventOnDate(ldt);
    }

    /**
     * This method gets all the events that exist in that account.
     * @param accName The name of the account.
     * @return a list of all the events that exist in that account. 
     */
    public ArrayList<Event> getAccountEvents(String accName) {
        AccountInterface acc = this.getAccountByName(accName);
        return acc.getAllAccountEvents();
    }

    /**
     * This method gets the event with the given name.
     * @param promoterName The promoter of the event.
     * @param eventName The name of the event.
     * @return the event with the given name.
     */
    public Event getEventByName(String promoterName, String eventName) {
        AccountInterface acc = this.getAccountByName(promoterName);
        return acc.getPromoteEventByName(eventName);
    }

    /**
     * This method checks if there is an event with the given name in the account.
     * @param promoterName The name of the account to be checked.
     * @param eventName The name of the event.
     * @return true if there is, false otherwise.
     */
    public boolean isValidEvent(String promoterName, String eventName) {
        return this.getEventByName(promoterName, eventName) != null;
    }

    /**
     * This method checks if the event priority of the promoter is high.
     * @param promoterName The name of the promoter.
     * @param eventName The name of the event to be checked.
     * @return true if it is, false otherwise.
     */
    public boolean isPromoterHighEvent(String promoterName, String eventName) {
        return EventType.isHigh(this.getEventByName(promoterName, eventName).getType());
    }

    /**
     * This method accepts the invitation of the event.
     * @param promoterName The name of the account that promotes the event.
     * @param inviteeName The name of the account that was invited to the event.
     * @param eventName The name of the event.
     */
    public void inviteeAcceptEvent(String promoterName, String inviteeName, String eventName) {
        AccountInterface acc = this.getAccountByName(promoterName);
        Event event = acc.getPromoteEventByName(eventName);
        event.addInvitedAccount(inviteeName);
        event.addAcceptedAccount(inviteeName);

        AccountInterface accInvitee = this.getAccountByName(inviteeName);
        accInvitee.addInviteeEvent(event);
    }

    /**
     * This method rejects the invitation of the event.
     * @param promoterName The name of the account that promote the event.
     * @param inviteeName The name of the account that was invited to the event.
     * @param eventName The name of the event.
     */
    public void inviteeRejectEvent(String promoterName, String inviteeName, String eventName) {
        AccountInterface acc = this.getAccountByName(promoterName);
        Event event = acc.getPromoteEventByName(eventName);
        event.addInvitedAccount(inviteeName);
        event.addRejectedAccount(inviteeName);

        AccountInterface accInvitee = this.getAccountByName(inviteeName);
        accInvitee.addInviteeEvent(event);
    }

    /**
     * This method removes the event from the promoter's account.
     * @param promoterName The name of the account that promotes the event.
     * @param event The event to be removed.
     */
    public void promoterRemoveEvent(String promoterName, Event event) {
        this.accounts.removeEventFromInvitees(event);
        event.setPromoterName(null);
        AccountInterface accPromoter = this.getAccountByName(promoterName);
        accPromoter.removeEvent(event.getName());
    }

    /**
     * This method checks if an account has already been invited to the event.
     * @param inviteeName The name of the account that was invited.
     * @param eventName The name of the event.
     * @return true if the account was already invited, false otherwise.
     */
    public boolean hasAlreadyBeenInvited(String inviteeName, String eventName) {
        AccountInterface acc = this.getAccountByName(inviteeName);
        return acc.hasAlreadyBeenInvited(eventName);
    }

    /**
     * This method checks if the invited account has already an event on that date.
     * @param inviteeName The name of the account that was invited.
     * @param promoterName The name of the account that promoted the event.
     * @param eventName The name of the event.
     * @return true if the invited account already has an event on that date, false otherwise.
     */
    public boolean hasInviteeAccountEventOnDate(String inviteeName, String promoterName, String eventName) {
        Event event = this.getEventByName(promoterName, eventName);
        return this.hasEventOnDate(inviteeName, event.getDate());
    }

    /**
     * This method gets a list that contains all the invited account events that are on the given date.
     * @param inviteeName The name of the account that was invited.
     * @param promoterName The name of the account that promoted the event.
     * @param eventName The name of the event.
     * @return a list that contains all the invited account events that are on the given date.
     */
    public ArrayList<Event> getInviteeConflictEvents(String inviteeName, String promoterName, String eventName) {
        Event event = this.getEventByName(promoterName, eventName);
        AccountInterface acc = this.getAccountByName(inviteeName);
        ArrayList<Event> eventList = acc.getConflictEventsListByDate(event);
        return eventList;
    }


    /**
     * This method updates the invited list of the event and also adds the event to the list of invited events in the invited account.
     * @param inviteeName The name of the account that was invited.
     * @param promoterName The name of the account that promoted the event.
     * @param eventName The name of the event.
     */
    public void inviteUpdateEvent(String inviteeName, String promoterName, String eventName) {
        AccountInterface promoterAcc = this.getAccountByName(promoterName);
        Event event = promoterAcc.getPromoteEventByName(eventName);
        event.addInvitedAccount(inviteeName);

        AccountInterface accInvitee = this.getAccountByName(inviteeName);
        accInvitee.addInviteeEvent(event);
    }

    /**
     * This method checks if the response to the invitation is valid.
     * @param response The response to the invitation.
     * @return true if the response is valid, false otherwise.
     */
    public boolean isResponseValid(String response) {
        return EventResponse.isResponseValid(response);
    }

    /**
     * This method gets a list that contains the invited account events which were invited on the given date.
     * @param The name of the account that was invited.
     * @param promoterName The name of the account that promoted the event.
     * @param eventName The name of the event.
     * @return a list that contains the invited account events which were invited on the given date.
     */
    public ArrayList<Event> getInviteeInvitedConflictEvents(String inviteeName, String promoterName, String eventName) {
        Event event = this.getEventByName(promoterName, eventName);
        AccountInterface acc = this.getAccountByName(inviteeName);
        ArrayList<Event> eventList = acc.getInviteeEventsListByDate(event);
        return eventList;
    }

    /**
     * This method checks if the response is "accept".
     * @param response The response to the invitation.
     * @return true if the response is "accept".
     */
    public boolean responseIsAccepted(String response) {
        return EventResponse.ACCEPT.toString().equals(response);
    }

    /**
     * This method checks if the response is "reject".
     * @param response The response to the invitation.
     * @return true if the response is "reject".
     */
    public boolean respondeIsRejected(String response) {
        return EventResponse.REJECT.toString().equals(response);
    }

    /**
     * This method checks if the account is on the invitation list of the event.
     * @param inviteeName The name of account to be checked.
     * @param eventName The name of the event.
     * @return true if the account is on the invitation list, false otherwise.
     */
    public boolean isOnInvitationList(String inviteeName, String eventName) {
        AccountInterface acc = this.getAccountByName(inviteeName);
        return acc.isOnInvitationList(eventName);
    }

    /**
     * This method checks if the account has already responded to the invitation.
     * @param inviteeName The name of the invited account.
     * @param eventName The name of the event.
     * @return true if the invited account has already responded otherwise false.
     */
    public boolean hasAlreadyResponded(String inviteeName, String eventName) {
        AccountInterface acc = this.getAccountByName(inviteeName);
        return acc.hasAlreadyResponded(eventName);
    }

    /**
     * This method gets the day of the event.
     * @param eventName The name of the event.
     * @param promoterName The name of the account that promoted the event.
     * @return the day of the event.
     */
    public int getEventDay(String eventName, String promoterName) {
        Event event = this.getEventByName(promoterName, eventName);
        return event.getDate().getDayOfMonth();
    }

    /**
     * This method gets the month of the event.
     * @param eventName The name of the event.
     * @param promoterName The name of the account that promoted the event.
     * @return the month of the event.
     */
    public int getEventMonth(String eventName, String promoterName) {
        Event event = this.getEventByName(promoterName, eventName);
        return event.getDate().getMonthValue();
    }

    /**
     * This method gets the year of the event.
     * @param eventName The name of the event.
     * @param promoterName The name of the account that promoted the event.
     * @return the year of the event.
     */
    public int getEventYear(String eventName, String promoterName) {
        Event event = this.getEventByName(promoterName, eventName);
        return event.getDate().getYear();
    }

    /**
     * This method gets the hour of the event.
     * @param eventName The name of the event.
     * @param promoterName The name of the account that promoted the event.
     * @return the hour of the event.
     */
    public int getEventHour(String eventName, String promoterName) {
        Event event = this.getEventByName(promoterName, eventName);
        return event.getDate().getHour();
    }

    /**
     * This method gets a list that contains the names of the accounts that were invited to the event.
     * @param promoterName The name of the account that promoted the event.
     * @param eventName The name of the event.
     * @return a list that contains the names of the accounts that were invited to the event.
     */
    public ArrayList<String> getInvitedNamesList(String promoterName, String eventName) {
        Event event = this.getEventByName(promoterName, eventName);
        return event.getAllInvitedNames();
    }

    /**
     * This method checks if the invited account has accepted the event. 
     * @param inviteeName The name of the account that was invited.
     * @param eventName The name of the event.
     * @param promoterName The name of the account that promoted the event.
     * @return true if the invited account has accepted the event otherwise false.
     */
    public boolean isEventAccepted(String inviteeName, String eventName, String promoterName) {
        Event event = this.getEventByName(promoterName, eventName);
        AccountInterface acc = this.getAccountByName(inviteeName);
        return acc.isEventAccepted(event);
    }

    /**
     * This method checks if the invited account has rejected the event. 
     * @param inviteeName The name of the account that was invited.
     * @param eventName The name of the event.
     * @param promoterName The name of the account that promoted the event.
     * @return true if the invited account has rejected the event, false otherwise.
     */
    public boolean isEventRejected(String inviteeName, String eventName, String promoterName) {
        Event event = this.getEventByName(promoterName, eventName);
        AccountInterface acc = this.getAccountByName(inviteeName);
        return acc.isEventRejected(event);
    }

    /**
     * This method checks if any event has at least one of the topics that were given.
     * @param topics A list of topics to be checked.
     * @return true if at least one event has at least one of the given topics.
     */
    public boolean hasEventsWithTopic(ArrayList<String> topics) {
        return this.accounts.hasEventWithTopic(topics);
    }

    /**
     * This method gets a list that contains all the events that have at least one of the given topics.
     * @param topics A list of topics to be checked.
     * @return a list that contains all the events that have at least one of the given topics.
     */
    public ArrayList<Event> getEventsWithTopics(ArrayList<String> topics) {
        return this.accounts.getEventsWithTopics(topics);
    }

    /**
     * This method gets a list that contains all the events that the account has been invited to on a specific date.
     * @param accName The name of the account.
     * @param eventName The name of the event.
     * @return a list that contains all the events that the account has been invited to on a specific date.
     */
    public ArrayList<Event> getInvitedAccountEventsOnDate(String accName, String eventName) {
        AccountInterface acc = this.getAccountByName(accName);
        Event event = this.getEventByName(accName, eventName);
        return acc.getInviteeEventsListByDate(event);
    }

    /**
     * This method checks if the inviteeAccount already has a high priority event on a specific date.
     * @param inviteeName The name of the account that was invited.
     * @param promoterName The name of the account that was promoted.
     * @param eventName The name of the event.
     * @return true if already has, false otherwise.
     */
    public boolean hasInviteeAccountHighEventOnDate(String inviteeName, String promoterName, String eventName) {
        Event event = this.getEventByName(promoterName, eventName);
        AccountInterface acc = this.getAccountByName(inviteeName);
        return acc.hasHighEventOnDate(event);
    }
}

