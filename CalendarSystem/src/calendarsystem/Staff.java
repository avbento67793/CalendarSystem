package calendarsystem;

/**
 * Represents a staff account in the calendar system.
 * Extends the Account class.
 */
public class Staff extends Account {

    /**
     * Constructor for creating a staff account with the specified name.
     * @param name The name of the staff member.
     */
    public Staff(String name) {
        // Call the constructor of the superclass (Account) with the staff member's name and account type (STAFF)
        super(name, AccountType.STAFF);
    }
}
