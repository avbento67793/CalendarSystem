package calendarsystem;

/**
 * A Class that represents a command type.
 */
public enum CommandType {

    /** exit - Terminates the execution of the program. */
    CMD_EXIT("exit"),

    /** help - Shows the available commands. */
    CMD_HELP("help"),

    /** register - Registers a new account the system. */
    CMD_REGISTER("register"),

    /** accounts - Lists all registered accounts. */
    CMD_ACCOUNTS("accounts"),

    /** create - Creates an event. */
    CMD_CREATE("create"),

    /** events - Lists all events of an account. */
    CMD_EVENTS("events"),

    /** invite - Invites an user to an event. */
    CMD_INVITE("invite"),

    /** response - An user responds to an event invitation. */
    CMD_RESPONSE("response"),

    /** events - Shows detailed information of an event. */
    CMD_EVENT("event"),

    /** topics - Shows all events that cover a list of topics. */
    CMD_TOPICS("topics");

    /***/
    private String command;

    /**
     * Constructs an enumeration that represents a command type.
     * 
     * @param command the command type
     */
    CommandType(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }

    /**
     * Gets the command type enumerator if found, null otherwise.
     * 
     * @param command the command type to found.
     * @return the command type enumerator if found, null otherwise.
     */
    public static CommandType getEnum(String command) {
        if(CMD_EXIT.toString().equals(command)) {
            return CMD_EXIT;
        }
        if(CMD_HELP.toString().equals(command)) {
            return CMD_HELP;
        }
        if(CMD_REGISTER.toString().equals(command)) {
            return CMD_REGISTER;
        }
        if(CMD_ACCOUNTS.toString().equals(command)) {
            return CMD_ACCOUNTS;
        }
        if(CMD_CREATE.toString().equals(command)) {
            return CMD_CREATE;
        }
        if(CMD_EVENTS.toString().equals(command)) {
            return CMD_EVENTS;
        }
        if(CMD_INVITE.toString().equals(command)) {
            return CMD_INVITE;
        }
        if(CMD_RESPONSE.toString().equals(command)) {
            return CMD_RESPONSE;
        }
        if(CMD_EVENT.toString().equals(command)) {
            return CMD_EVENT;
        }
        if(CMD_TOPICS.toString().equals(command)) {
            return CMD_TOPICS;
        }
        return null;
    }
}
