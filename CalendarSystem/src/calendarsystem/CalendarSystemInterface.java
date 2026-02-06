package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface CalendarSystemInterface {
    boolean accountAlreadyExists(String accName);
    void addAccount(String accName, String accType);
    ArrayList<AccountInterface> getSortedList();
    AccountInterface getAccountByName(String accName);
    void addEvent(String accName, String pType, String eventName, LocalDateTime ldt, String[] topics);
    boolean eventAlreadyExists(String accName, String eventName);
    boolean isGuestAccount(String accName);
    boolean isStaffAccount(String accName);
    boolean hasEventOnDate(String accName, LocalDateTime ldt);
    ArrayList<Event> getAccountEvents(String accName);
    Event getEventByName(String promoterName, String eventName);
    boolean isValidEvent(String promoterName, String eventName);
    boolean isPromoterHighEvent(String promoterName, String eventName);
    void inviteeAcceptEvent(String promoterName, String inviteeName, String eventName);
    void inviteeRejectEvent(String promoterName, String inviteeName, String eventName);
    void promoterRemoveEvent(String promoterName, Event event);
    boolean hasAlreadyBeenInvited(String inviteeName, String eventName);
    boolean hasInviteeAccountEventOnDate(String inviteeName, String promoterName, String eventName);
    ArrayList<Event> getInviteeConflictEvents(String inviteeName, String promoterName, String eventName);
    void inviteUpdateEvent(String inviteeName, String promoterName, String eventName);
    boolean isResponseValid(String response);
    ArrayList<Event> getInviteeInvitedConflictEvents(String inviteeName, String promoterName, String eventName);
    boolean responseIsAccepted(String response);
    boolean respondeIsRejected(String response);
    boolean isOnInvitationList(String inviteeName, String eventName);
    boolean hasAlreadyResponded(String inviteeName, String eventName);
    int getEventDay(String eventName, String promoterName);
    int getEventMonth(String eventName, String promoterName);
    int getEventYear(String eventName, String promoterName);
    int getEventHour(String eventName, String promoterName);
    ArrayList<String> getInvitedNamesList(String promoterName, String eventName);
    boolean isEventAccepted(String inviteeName, String eventName, String promoterName);
    boolean isEventRejected(String inviteeName, String eventName, String promoterName);
    boolean hasEventsWithTopic(ArrayList<String> topics);
    ArrayList<Event> getEventsWithTopics(ArrayList<String> topics);
    ArrayList<Event> getInvitedAccountEventsOnDate(String accName, String eventName);
    boolean hasInviteeAccountHighEventOnDate(String inviteeName, String promoterName, String eventName);
}
