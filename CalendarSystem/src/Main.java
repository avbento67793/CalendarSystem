import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

import calendarsystem.*;

public class Main {

    /** The message that has to be printed in command exit.*/
    private static final String MSG_EXIT = "Bye!";

    /** The initial part of the message that will be printed in the case of an unknown command.*/
    private static final String MSG_UNKNOWN_COMMAND = "Unknown command ";

    /** The final part of the message that will be printed be printed in the case of an unknown command.*/
    private static final String MSG_TYPE_HELP = ". Type help to see available commands.";

    /** The reference to the calendar system. */
    private static CalendarSystem calendar;

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);

        calendar = new CalendarSystem();

        // read commands
        executeCommands(sn);

        sn.close();
        System.exit(0);
    }

    /**
     * This method executes the commands according to the command of the user.
     * @param sn The reference to the Scanner.
     */
    private static void executeCommands(Scanner sn) {
        boolean exitCommand = false;      
        do {
            // read command line
            String[] line = sn.nextLine().split(" ");


            CommandType command;
            try {
                // get the command from the command line
                command = CommandType.getEnum(line[0].toLowerCase());
                switch (command) {
                    case CMD_EXIT:
                        exit();
                        exitCommand = true;
                        break;
                    case CMD_HELP:
                        help();
                        break;
                    case CMD_REGISTER:
                        register(line);
                        break;
                    case CMD_ACCOUNTS:
                        accounts();
                        break;
                    case CMD_CREATE:
                        create(sn, line);
                        break;
                    case CMD_EVENTS:
                        events(line);
                        break;
                    case CMD_INVITE:
                        invite(sn, line);
                        break;
                    case CMD_RESPONSE:
                        response(sn, line);
                        break;
                    case CMD_EVENT:
                        event(line);
                        break;
                    case CMD_TOPICS:
                        topics(line);
                        break;
                    default:
                        printUnknownCommand(line);
                        break;
                }
            } catch (Exception e) {
                printUnknownCommand(line);
            }
        } while(exitCommand == false);
    }

    /**
     * This method executes the command "register".
     * @param line The command line already separated in a Vector.
     */
    private static void register(String[] line) {
        String accName = line[1];
        String accType = line[2];

        if(accountNameAlreadyExists(accName)) {
            return;
        }

        if (!isAccountTypeValid(accType)) {
            return;
        }

        calendar.addAccount(accName, accType);
        System.out.println(accName + " was registered.");
        return;
    }

    /**
     * This method executes the command "accounts".
     */
    private static void accounts() {
        ArrayList<AccountInterface> accList = calendar.getSortedList();

        if (accList.size() == 0) {
            System.out.println("No account registered.");
            return;
        }

        System.out.println("All accounts:");
        for (int i = 0; i < accList.size(); i++) {
            System.out.println(accList.get(i).getName() + " " + "[" + accList.get(i).getType().toString() + "]");
        }
    }

    /**
     * This method executes the command "create".
     * @param sn The reference to the scanner.
     * @param line The command line already separated in a Vector.
     */
    private static void create(Scanner sn, String[] line) {
        String accName = line[1];


        String eventName = sn.nextLine();

        String[] eventDateLine = sn.nextLine().split(" ");
        String pType = eventDateLine[0];
        int year = Integer.parseInt(eventDateLine[1]);
        int month = Integer.parseInt(eventDateLine[2]);
        int day = Integer.parseInt(eventDateLine[3]);
        int hour = Integer.parseInt(eventDateLine[4]);

        String[] topics = sn.nextLine().split(" ");


        if(!doesAccountExist(accName)) {
            return;
        }

        if (!isPriorityTypeValid(pType)) {
            return;
        }

        if (isGuestAccount(accName)) {
            return;
        }

        if(isStaffAccountAndHighType(accName, pType)) {
            return;
        }

        if(eventAlreadyExists(accName, eventName)) {
            return;
        }

        LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, 0);

        if(hasEventOnDate(accName, ldt)) {
            return;
        }

        if(hasEventOnDate(accName, ldt)) {
            return;
        }

        calendar.addEvent(accName, pType, eventName, ldt, topics);
        System.out.println(eventName + " is scheduled.");

        Iterator<Event> itEvents = calendar.getInvitedAccountEventsOnDate(accName, eventName).iterator();
        while(itEvents.hasNext()) {
            Event event = itEvents.next();
            event.addRejectedAccount(accName);
        }
    }

    /**
     * This method executes the command "events".
     * @param line The command line already separated in a Vector.
     */
    private static void events(String[] line) {
        String accName = line[1];

        if(!doesAccountExist(accName)) {
            return;
        }

        ArrayList<Event> eventsList = calendar.getAccountEvents(accName);

        if(eventsList.size() > 0) {
            Iterator<Event> itEvents = eventsList.iterator();
            System.out.println("Account " + accName + " events:");
            while(itEvents.hasNext()) {
                Event event = itEvents.next();
                System.out.println(event.getName() + " status " + "[invited " + event.getInviteStatus() + "] [accepted " + event.getAcceptedStatus() + "] [rejected " + event.getRejectedStatus() + "] [unanswered " + event.getUnansweredStatus() + "]");
            }
        } else {
            System.out.println("Account " + accName + " has no events.");
            return;
        }
    }

    /**
     * This method executes the command "invite".
     * @param sn The reference to the scanner.
     * @param line The command line already separated in a Vector.
     */
    private static void invite(Scanner sn, String[] line) {
        String inviteeName = line[1];

        // read promoter invitation Line
        String[] pInvitationLine = sn.nextLine().split(" ");
        String promoterName = pInvitationLine[0];
        String eventName = String.join(" ", Arrays.copyOfRange(pInvitationLine, 1, pInvitationLine.length));

        if(!doesAccountExist(promoterName)) {
            return;
        }

        if(!doesAccountExist(inviteeName)) {
            return;
        }

        if (!doesEventExistInAccount(eventName, promoterName)) {
            return;
        }
        
        if(hasAlreadyBeenInvited(inviteeName, eventName)) {
            return;
        }

        // If the invitation concerns a staff user and a high priority event then 2 cases exist
        if(calendar.isStaffAccount(inviteeName) && calendar.isPromoterHighEvent(promoterName, eventName)) {
            
            if(calendar.hasInviteeAccountHighEventOnDate(inviteeName, promoterName, eventName)) {
                System.out.println("Account " + inviteeName + " already attending another event.");
                return;
            }
            
            System.out.println(inviteeName + " accepted the invitation.");
            Iterator<Event> itEvent = calendar.getInviteeConflictEvents(inviteeName, promoterName, eventName).iterator();
            while(itEvent.hasNext()) {
                Event event = itEvent.next();
                if(event.isPromoter(inviteeName)) {
                    System.out.println(event.getName() + " promoted by " + inviteeName + " was removed.");
                    calendar.promoterRemoveEvent(inviteeName, event);
                } else {
                    System.out.println(event.getName() + " promoted by " + event.getPromoterName() + " was rejected.");
                    event.addRejectedAccount(inviteeName);
                }
            }
            
            calendar.inviteeAcceptEvent(promoterName, inviteeName, eventName);
            return;
            
        } else if(calendar.hasInviteeAccountEventOnDate(inviteeName, promoterName, eventName)) {
            System.out.println("Account " + inviteeName + " already attending another event.");
            return;
        }

        calendar.inviteUpdateEvent(inviteeName, promoterName, eventName);
        System.out.println(inviteeName + " was invited.");
    }

    /**
     * This method executes the command "response".
     * @param sn The reference to the scanner.
     * @param line The command line already separated in a Vector.
     */
    private static void response(Scanner sn, String[] line) {
        String inviteeName = line[1];

        // read promoter invitation Line
        String[] pInvitationLine = sn.nextLine().split(" ");
        String promoterName = pInvitationLine[0];
        String eventName = String.join(" ", Arrays.copyOfRange(pInvitationLine, 1, pInvitationLine.length));

        // read the invite response
        String response = sn.nextLine();

        if(!doesAccountExist(promoterName)) {
            return;
        }

        if(!doesAccountExist(inviteeName)) {
            return;
        }

        if(!responseTypeInvalid(response)) {
            return;
        }

        if (!doesEventExistInAccount(eventName, promoterName)) {
            return;
        }

        if(!isOnInvitationList(inviteeName, eventName)) {
            return;
        }

        if(hasAlreadyResponded(inviteeName, eventName)) {
            return;
        }

        if(calendar.responseIsAccepted(response)) {
            System.out.println("Account " + inviteeName + " has replied " + response + " to the invitation.");
            Iterator<Event> itEvent = calendar.getInviteeInvitedConflictEvents(inviteeName, promoterName, eventName).iterator();
            while(itEvent.hasNext()) {
                Event event = itEvent.next();
                System.out.println(event.getName() + " promoted by " + event.getPromoterName() + " was rejected.");
                event.addRejectedAccount(inviteeName);
            }
            calendar.inviteeAcceptEvent(promoterName, inviteeName, eventName);
            return;
        } else {
            calendar.inviteeRejectEvent(promoterName, inviteeName, eventName);
            System.out.println("Account " + inviteeName + " has replied " + response + " to the invitation.");
        }
    }

    /**
     * This method executes the command "event".
     * @param line The command line already separated in a Vector.
     */
    private static void event(String[] line) {
        String promoterName = line[1];
        String eventName = String.join(" ", Arrays.copyOfRange(line, 2, line.length));

        if(!doesAccountExist(promoterName)) {
            return;
        }

        if(!doesEventExistInAccount(eventName, promoterName)) {
            return;
        }

        System.out.println(eventName + " occurs on " + calendar.getEventDay(eventName, promoterName) + "-0" + 
                calendar.getEventMonth(eventName, promoterName) + "-" + 
                calendar.getEventYear(eventName, promoterName) + " " + 
                calendar.getEventHour(eventName, promoterName) + "h:");

        Iterator<String> inviteeNames = calendar.getInvitedNamesList(promoterName, eventName).iterator();
        while(inviteeNames.hasNext()) {
            String name = inviteeNames.next();
            if(calendar.isEventAccepted(name, eventName, promoterName)) {
                System.out.println(name + " [accept]");
            } else if(calendar.isEventRejected(name, eventName, promoterName)) {
                System.out.println(name + " [reject]");
            } else {
                System.out.println(name + " [no_answer]");
            }
        }
        return;
    }


    /**
     * This method executes the command "topics".
     * @param line The command line already separated in a Vector.
     */
    private static void topics(String[] line) {

        ArrayList<String> topics = new ArrayList<String>(Arrays.asList(line));
        topics.remove(0);
        ArrayList<Event> eventsWithTopics = calendar.getEventsWithTopics(topics);

        if(eventsWithTopics.size() == 0) {
            System.out.println("No events on those topics.");
            return;
        }

        String formattedTopics = formatTopicsToString(topics);
        System.out.println("Events on topics " + formattedTopics + ":");

        Iterator<Event> events = eventsWithTopics.iterator();
        while(events.hasNext()) {
            Event event = events.next();
            String formattedEventTopics = formatTopicsToString(event.getTopics());
            System.out.println(event.getName() + " promoted by " + event.getPromoterName() + " on " + formattedEventTopics);
        }
    }

    /**
     * This method executes the command "help".
     * Informs the user about the available commands.
     */
    private static void help() {
        System.out.println("Available commands:");
        System.out.println("register - registers a new account");
        System.out.println("accounts - lists all registered accounts");
        System.out.println("create - creates a new event");
        System.out.println("events - lists all events of an account");
        System.out.println("invite - invites an user to an event");
        System.out.println("response - response to an invitation");
        System.out.println("event - shows detailed information of an event");        
        System.out.println("topics - shows all events that cover a list of topics");
        System.out.println("help - shows the available commands");  
        System.out.println("exit - terminates the execution of the program");  
    }

    /**
     * This method executes the command "quit".
     * Ends the program execution.
     */
    private static void exit() {
        System.out.println(MSG_EXIT);
    }

    /**
     * This method executes a specific print when the command is unknown.
     * @param line The command line already separated in a Vector.
     */
    private static void printUnknownCommand(String[] line) {
        if(line.length == 1) {
            System.out.println(MSG_UNKNOWN_COMMAND + line[0].toUpperCase() + MSG_TYPE_HELP);
            return;
        } 

        for (int i = 0; i < line.length; i++) {
            System.out.println(MSG_UNKNOWN_COMMAND + line[i].toUpperCase() + MSG_TYPE_HELP);
        }
    }

    /**
     * This method checks if an account already exists with the given name..
     * @param accName The account's name.
     * @return true if the account already exists, false otherwise.
     */
    private static boolean accountNameAlreadyExists(String accName) {
        if(calendar.accountAlreadyExists(accName)) {
            System.out.println("Account " + accName + " already exists.");
            return true;
        } 
        return false;
    }

    /**
     * This method checks if there is an account with the given name.
     * @param accName The name of the account.
     * @return true if the account exists, false otherwise..
     */
    private static boolean doesAccountExist(String accName) {
        if(calendar.accountAlreadyExists(accName)) {
            return true;
        } 
        System.out.println("Account " + accName + " does not exist.");
        return false;
    }

    /**
     * This method checks if the account type is valid or not.
     * @param accType The type of the account.
     * @return true if the account type is valid, false otherwise.
     */
    private static boolean isAccountTypeValid(String accType) {
        if(AccountType.isTypeValid(accType)) {
            return true;
        }
        System.out.println("Unknown account type.");
        return false;
    }

    /**
     * This method checks if there is  an event with the given name in the account.
     * @param eventName The name of the event.
     * @param promoterName The name of the account to be checked.
     * @return true if there is is, false otherwise.
     */
    private static boolean doesEventExistInAccount(String eventName, String promoterName) {
        if(calendar.isValidEvent(promoterName, eventName)) {
            return true;
        }
        System.out.println(eventName + " does not exist in account " + promoterName + ".");
        return false;
    }

    /**
     * This method checks if an account has already been invited to an event.
     * @param inviteeName The name of the account that was invited.
     * @param eventName The name of the event.
     * @return true if the account has already been invited, false otherwise.
     */
    private static boolean hasAlreadyBeenInvited(String inviteeName, String eventName) {
        if(calendar.hasAlreadyBeenInvited(inviteeName, eventName)) {
            System.out.println("Account " + inviteeName + " was already invited.");
            return true;
        }
        return false;
    }

    /**
     * This method checks if the response to the invitation is valid.
     * @param response The given response.
     * @return true if the response is valid, false otherwise.
     */
    private static boolean responseTypeInvalid(String response) {
        if(calendar.isResponseValid(response)) {
            return true;
        }
        System.out.println("Unknown event response.");
        return false;
    }

    /**
     * This method checks if the account is in the invitation list of an event.
     * @param inviteeName The name of account to check.
     * @param eventName The name of the event.
     * @return true if the account is on the invitation list, false otherwise.
     */
    private static boolean isOnInvitationList(String inviteeName, String eventName) {
        if(calendar.isOnInvitationList(inviteeName, eventName)) {
            return true;
        }
        System.out.println("Account " + inviteeName + " is not on the invitation list.");
        return false;
    }

    /**
     * This method checks if an account has already responded to an invitation.
     * @param inviteeName The name of the invited account.
     * @param eventName The name of the event.
     * @return true if the invited account has already responded, false otherwise.
     */
    private static boolean hasAlreadyResponded(String inviteeName, String eventName) {
        if(calendar.hasAlreadyResponded(inviteeName, eventName)) {
            System.out.println("Account " + inviteeName + " has already responded.");
            return true;
        }
        return false;
    }

    /**
     * This method checks if the priority type is valid.
     * @param pType The priority type.
     * @return true if the priority type is valid, false otherwise.
     */
    private static boolean isPriorityTypeValid(String pType) {
        if(EventType.isPriorityTypeValid(pType)) {
            return true;
        }
        System.out.println("Unknown priority type.");
        return false;
    }

    /**
     * This method checks if an account is a guest.
     * @param accName The name of the account to be checked.
     * @return true if the account is a guest, false otherwise.
     */
    private static boolean isGuestAccount(String accName) {
        if(calendar.isGuestAccount(accName)) {
            System.out.println("Guest account " + accName + " cannot create events.");
            return true;
        }
        return false;
    }

    /**
     * This method checks if an account is a staff and if the priority type of an event is high.
     * @param accName The name of the account to be checked.
     * @param pType The priority type of the event.
     * @return true if the account is a staff account and the event is a high priority one, false otherwise.
     */
    private static boolean isStaffAccountAndHighType(String accName, String pType) {
        if(calendar.isStaffAccount(accName) && EventType.isHigh(pType)) {
            System.out.println("Account " + accName + " cannot create high priority events.");
            return true;
        }
        return false;
    }

    /**
     * This method checks if an event with the given name already exists in the given account.
     * @param accName The name of the account.
     * @param eventName The name of the event to be checked.
     * @return true if already exists, false otherwise.
     */
    private static boolean eventAlreadyExists(String accName, String eventName) {
        if(calendar.eventAlreadyExists(accName, eventName)) {
            System.out.println(eventName + " already exists in account " + accName + ".");
            return true;
        }
        return false;
    }

    /**
     * This method checks if an account already has an event on the given date.
     * @param accName The name of the account.
     * @param ldt The date to be checked.
     * @return true if the account already has an event on the given date, false otherwise.
     */
    private static boolean hasEventOnDate(String accName, LocalDateTime ldt) {
        if(calendar.hasEventOnDate(accName, ldt)) {
            System.out.println("Account " + accName + " is busy.");
            return true;
        }
        return false;
    }

    /**
     * This method formats the topics to a string to be printed in the way that is asked.
     * @param topics The list of topics.
     * @return A formatted string in the way that we want.
     */
    private static String formatTopicsToString(ArrayList<String> topics) {
        return topics.toString()
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "");  //remove the left bracket
    }
}
