package calendarsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing a collection of accounts in the calendar system.
 */
public class Accounts {

    // A map storing accounts with their names as keys.
    private HashMap<String, AccountInterface> accList;

    /**
     * Initializes the Accounts class.
     */
    public Accounts() {
        this.accList = new HashMap<>();
    }

    /**
     * Checks if an account already exists.
     * @param accName The name of the account to check.
     * @return true if the account exists, false otherwise.
     */
    public boolean accountAlreadyExists(String accName) {
        return this.accList.containsKey(accName);
    }

    /**
     * Adds a new account.
     * @param accName The name of the new account.
     * @param accType The type of the new account.
     */
    public void addAccount(String accName, String accType) {
        AccountInterface account;
        switch (AccountType.getEnum(accType)) {
            case MANAGER:
                account = new Manager(accName);
                break;
            case STAFF:
                account = new Staff(accName);
                break;
            case GUEST:
                account = new Guest(accName);
                break;
            default:
                return; 
        }
        this.accList.put(accName, account);
    }

    /**
     * Retrieves an account given its name.
     * @param accName The name of the account.
     * @return the account if found, null otherwise.
     */
    public AccountInterface getAcountByName(String accName) {
        return this.accList.get(accName);
    }

    /**
     * Gets a sorted set of account names.
     * @return a TreeSet containing sorted account names.
     */
    private TreeSet<String> getSortedAccountsNames(){
        Set<String> accountsNames = new TreeSet<>();

        for (Map.Entry<String, AccountInterface> accountEntry : this.accList.entrySet()) {
            AccountInterface acc = accountEntry.getValue();
            accountsNames.add(acc.getName());
        }

        return (TreeSet<String>) accountsNames;
    }

    /**
     * Gets a sorted list of accounts.
     * @return an ArrayList containing sorted accounts.
     */
    public ArrayList<AccountInterface> sortedList(){
        ArrayList<AccountInterface> sortedList = new ArrayList<>();
        TreeSet<String> sortedAccountsNames = this.getSortedAccountsNames();
        List<String> list = new ArrayList<String>(sortedAccountsNames); 

        for (int i = 0; i < sortedAccountsNames.size(); i++) {
            String name = list.get(i);
            AccountInterface acc = this.getAcountByName(name);
            if (acc != null) {
                sortedList.add(acc);
            }
        }

        return sortedList;
    }

    /**
     * Removes an event from all invitees.
     * @param event The event to be removed.
     */
    public void removeEventFromInvitees(Event event) {
        for (Map.Entry<String, AccountInterface> accountEntry : this.accList.entrySet()) {
            AccountInterface acc = accountEntry.getValue();
            if (acc.hasAlreadyBeenInvited(event.getName())) {
                acc.removeEvent(event.getName());
            }
        }
    }

    /**
     * Checks if any account has events with the specified topics.
     * @param topics The list of topics to check.
     * @return true if any account has events with the given topics, false otherwise.
     */
    public boolean hasEventWithTopic(ArrayList<String> topics) {
        for (Map.Entry<String, AccountInterface> accountEntry : this.accList.entrySet()) {
            AccountInterface acc = accountEntry.getValue();
            if (acc.hasEventsWithTopic(topics)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a list of events with the specified topics.
     * @param topics The list of topics to filter events by.
     * @return an ArrayList of events with the given topics.
     */
    public ArrayList<Event> getEventsWithTopics(ArrayList<String> topics) {
        Set<Event> eventsList = new HashSet<>();

        for (Map.Entry<String, AccountInterface> accountEntry : this.accList.entrySet()) {
            AccountInterface acc = accountEntry.getValue();
            eventsList.addAll(acc.getEventsWithTopics(topics));
        }

        ArrayList<Event> newEventsList = new ArrayList<>(eventsList);

        newEventsList.sort(new EventComparator(topics));
        
        return newEventsList;
    }
}
