package calendarsystem;

/**
 * Enum representing different types of accounts in the calendar system.
 */
public enum AccountType {

    MANAGER("manager"),
    STAFF("staff"),
    GUEST("guest");
    
    // The string representation of the account type.
    private String accountType;

    /**
     * Constructor for AccountType.
     * @param accountType The string representation of the account type.
     */
    AccountType(String accountType) {
        this.accountType = accountType;
    }
    
    /**
     * Returns the string representation of the account type.
     * @return the string representation of the account type.
     */
    @Override
    public String toString() {
        return this.accountType;
    }

    /**
     * Gets the enum constant of the specified account type.
     * @param accType The string representation of the account type.
     * @return the AccountType constant, or null if no matching constant is found.
     */
    public static AccountType getEnum(String accType) {
        if (accType.equals(MANAGER.toString())) {
            return MANAGER;
        }
        if (accType.equals(STAFF.toString())) {
            return STAFF;
        }
        if (accType.equals(GUEST.toString())) {
            return GUEST;
        }
        return null;
    }
    
    /**
     * Checks if the given string is a valid account type.
     * @param accType The string representation of the account type.
     * @return true if the account type is valid, false otherwise.
     */
    public static boolean isTypeValid(String accType) { 
        return AccountType.getEnum(accType) != null;
    }
    
    /**
     * Checks if the given account type is a guest.
     * @param accType The account type to check.
     * @return true if the account type is GUEST, false otherwise.
     */
    public static boolean isGuest(AccountType accType) { 
        return AccountType.GUEST.equals(accType);
    }
    
    /**
     * Checks if the given account type is a staff.
     * @param accType The account type to check.
     * @return true if the account type is STAFF, false otherwise.
     */
    public static boolean isStaff(AccountType accType) { 
        return AccountType.STAFF.equals(accType);
    }
    
    /**
     * Checks if the given account type is a manager.
     * @param accType The account type to check.
     * @return true if the account type is MANAGER, false otherwise.
     */
    public static boolean isManager(AccountType accType) { 
        return AccountType.MANAGER.equals(accType);
    }
}
