package calendarsystem;

/**
 * Represents a guest account in the calendar system.
 * Extends the Account class.
 */
public class Guest extends Account {

    /**
     * Constructor for creating a guest account with the specified name.
     * @param name The name of the guest.
     */
    public Guest(String name) {
        // Call the constructor of the superclass (Account) with the guest's name and account type (GUEST)
        super(name, AccountType.GUEST);
    }
}
