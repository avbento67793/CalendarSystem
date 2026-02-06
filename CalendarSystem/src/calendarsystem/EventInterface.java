package calendarsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface EventInterface {
    String getName();
    String getType();
    LocalDateTime getDate();
    ArrayList<String> getTopics();
    boolean isPromoter(String accName);
    int getInviteStatus();
    int getAcceptedStatus();
    int getRejectedStatus();
    int getUnansweredStatus();
    boolean isAccountInvited(String inviteeName);
    boolean isEventAccepted(String inviteeName);
    boolean isEventRejected(String inviteeName);
    void addInvitedAccount(String accName);
    void removeInvitedAccount(String accName);
    void addAcceptedAccount(String accName);
    void addRejectedAccount(String accName);
    void setPromoterName(String name);
    String getPromoterName();
    ArrayList<String> getAllInvitedNames();
    boolean hasEventWithTopic(String topic);
    int countMatchingTopics(ArrayList<String> topics);
    boolean isHighEvent();
}
