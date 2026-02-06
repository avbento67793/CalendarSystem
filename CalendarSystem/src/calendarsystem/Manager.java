package calendarsystem;

/**
 * Represents a manager account in the calendar system.
 * Extends the Account class.
 */
public class Manager extends Account {

    /**
     * Constructor for creating a manager account with the specified name.
     * @param name The name of the manager.
     */
    public Manager(String name) {
        // Call the constructor of the superclass (Account) with the manager's name and account type (MANAGER)
        super(name, AccountType.MANAGER);
    }
}
