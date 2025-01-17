package se.iths.core;

import se.iths.utility.Calculator;
import se.iths.utility.CmdUtility;
import se.iths.utility.ScannerWrapper;

import java.time.LocalDate;
import java.util.List;

public class MenuHandler {
    private ScannerWrapper scannerWrapper;
    private User user;
    private Calculator calc = new Calculator();

    public MenuHandler(ScannerWrapper scannerWrapper,User user) {
        this.scannerWrapper = scannerWrapper;
        this.user = user;
    }

    //region Run - First tier of the branching menu. General menu(User/Session)
    public void runMenu() {

        while (true) {
            CmdUtility.clearConsole();
            printMainMenu();
            printInputPrompt();
            double userInput = scannerWrapper.numberInput();

            switch ((int) userInput){
                case 1:
                    viewUserDetails();
                    break;
                case 2:
                    runSessionMenu();
                    break;
                case 0:
                    return;
                default:
                    printFailedMenuChoice();
                    break;
            }
        }
    }

    private void runSessionMenu() {
        CmdUtility.clearConsole();
        printSessionMenu();
        printInputPrompt();

        double userInput = scannerWrapper.numberInput();
        switch ((int) userInput){
            case 1:
                addSessionToCollection();
                break;
            case 2:
                resolveSessionSearch();
                break;
            case 3:
                viewSessionList(user.getSessionCollection());
                break;
            case 0:
                return;
            default:
                printFailedMenuChoice();
                break;
        }
    }

    private void runUserSettingsMenu() {
        CmdUtility.clearConsole();
        printUserEditMenu();
        printInputPrompt();

        double userInput = scannerWrapper.numberInput();
        switch ((int) userInput){
            case 1:
                editUserDetails(UserConfig.NAME);
                break;
            case 2:
                editUserDetails(UserConfig.AGE);
                break;
            case 3:
                editUserDetails(UserConfig.WEIGHT);
                break;
            case 4:
                editUserDetails(UserConfig.HEIGHT);
                break;
            case 0:
                return;
            default:
                printFailedMenuChoice();
                break;
        }
    }
    //endregion


    //region View - Second tier of the branching menu. Enters details and larger scale views.
    private void viewUserDetails() {
        CmdUtility.clearConsole();
        // Todo this does not have a test (it's essentially another menu check test I would need to do.)
        SessionHandler sessionHandler = user.getSessionCollection();
        System.out.println("Welcome " + user.getName() + "!");
        System.out.println("This is the current details of you:\n");
        System.out.println("Age: " + user.getAge() + "\n" +
                           "Height: " + user.getHeight() + " cm\n" +
                           "Weight: " + user.getWeight() + " kg\n" +
                           "-----\n" +
                           "Fitness Score: " + sessionHandler.getTotalFitnessScore() + "\n" +
                           "Total Distance Traveled: " + calc.calcTotalDistanceTraveled(sessionHandler) + " km\n" +
                           "Average Distance Traveled per Session: " + calc.calcAverageDistanceTraveled(sessionHandler) + " km\n");

        printUserDetailsMenu();
        printInputPrompt();
        double userInput = scannerWrapper.numberInput();
        switch ((int) userInput) {
            case 1:
                runUserSettingsMenu();
                break;
            case 0:
                return;
            default:
                printFailedMenuChoice();
                break;

        }
    }

    public void viewSessionList(SessionHandler sessionHandler) {
        SortType currentSortType = SortType.BY_DATE_DESC;

        boolean viewingSessions = true;
        while (viewingSessions) {
            List<String> sessionList = sessionHandler.getSortedSessions(currentSortType);
            CmdUtility.clearConsole();
            printAllSessions(sessionList);
            printInputPrompt();

            int userInput = (int) scannerWrapper.numberInput();

            if (userInput > 0 && userInput <= sessionList.size()) {
                String selectedSession = sessionList.get(userInput - 1);
                Session pulledSession = user.getSessionCollection().readSession(selectedSession);
                viewSessionDetails(pulledSession);

            } else if (userInput == sessionList.size() + 1) {
                currentSortType = resolveChangeSessionOrder();
                sessionHandler.getSortedSessions(currentSortType);

            } else if (userInput == 0) {
                System.out.println("Going back to main menu.");
                viewingSessions = false;
                scannerWrapper.promptEnterKey();
            }
        }
    }

    private void viewSessionDetails(Session pulledSession) {
        CmdUtility.clearConsole();
        System.out.println(
                "You have selected: " + pulledSession.getId() + "\n" +
                "Duration: " + calc.calcSecToMin(pulledSession.getTime()) + " minutes\n" +
                "Distance: " + pulledSession.getDistance() + " km\n" +
                "Date: " + pulledSession.getDate());

        printInputPrompt();
        printSessionDetailMenu();

        int choice = (int) scannerWrapper.numberInput();
        switch (choice) {
            case 1:
                editSessionDetails(pulledSession);
                break;
            case 2:
                deleteSessionQuery(pulledSession);
                break;
            case 0:
                return;
            default:
                printFailedMenuChoice();
                break;
        }
    }
    //endregion


    //region Methods that leads into data manipulation (add, delete, edit)
    private void editUserDetails(UserConfig choice) {
        CmdUtility.clearConsole();
        System.out.println("Are you sure you want to change your " + choice.name().toLowerCase() + "? (y/n)");
        printInputPrompt();

        if (scannerWrapper.yesOrNoInput()) {
            CmdUtility.clearConsole();
            System.out.print("Please enter your new " + choice.name().toLowerCase() + ": ");

            switch (choice) {
                case NAME:
                    user.setName(scannerWrapper.textInput(InputLimit.USERNAME.getLimit()));
                    break;
                case AGE:
                    user.setAge((int) scannerWrapper.numberInput());
                    break;
                case WEIGHT:
                    user.setWeight(scannerWrapper.numberInput());
                    break;
                case HEIGHT:
                    user.setHeight(scannerWrapper.numberInput());
                    break;
                default:
                    printFailedMenuChoice();
                    break;
            }
        }
    }

    private void editSessionDetails(Session pulledSession) {
        //TODO Add tests + func.
        System.out.println("You have selected to edit: " + pulledSession.getId() + "\n");
        System.out.println("Function not yet implemented - returning to main menu");
        scannerWrapper.promptEnterKey();
    }

    public void deleteSessionQuery(Session pulledSession) {
        CmdUtility.clearConsole();
        System.out.println("Are you sure you want to delete the session: " + pulledSession.getId() + "? (y/n)");
        printInputPrompt();

        if (scannerWrapper.yesOrNoInput()) {
            user.getSessionCollection().deleteSession(pulledSession.getId());
            System.out.println("Deleted session: " + pulledSession.getId());
            scannerWrapper.promptEnterKey();
        }
    }

    public void addSessionToCollection() {
        CmdUtility.clearConsole();
        System.out.println("Please enter the corresponding info for this session:");
        System.out.print("\nName of the session: ");
        String sessionName = scannerWrapper.textInput(InputLimit.SESSION_NAME.getLimit());

        System.out.print("\nDistance in km: ");
        double sessionDistance = scannerWrapper.numberInput();

        System.out.print("\nDuration in minutes: ");
        int sessionDuration = (int) calc.calcMinToSec(scannerWrapper.numberInput());

        // TODO This entire part below can become its own method within scanner.
        System.out.print("\nDate (YYYY-MM-DD): ");
        LocalDate sessionDate = scannerWrapper.dateInput();

        try {
            user.getSessionCollection().createSession(sessionName, sessionDistance ,sessionDuration, sessionDate);
            System.out.println("\nSession created successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("\nSession already exists!");
        }

        scannerWrapper.promptEnterKey();
    }
    //endregion


    //region Resolves - Executes parts of a function connected to a larger method.
    private void resolveSessionSearch() {
        CmdUtility.clearConsole();
        System.out.print("Enter the search term: "); // not sure how to ask this in a less formal way 🙃
        String searchQuery = scannerWrapper.textInput(InputLimit.SESSION_NAME.getLimit());

        SessionHandler foundSessions = user.getSessionCollection().searchSessionsByID(searchQuery);
        if (foundSessions.getSessionIDs().isEmpty()) {
            System.out.println("No sessions found, returning to main menu.");
            scannerWrapper.promptEnterKey();
        } else
            viewSessionList(foundSessions);
    }

    // TODO add search based on date and time - both before/after.
    private SortType resolveChangeSessionOrder() {
        CmdUtility.clearConsole();
        printSortTypesAlternatives();
        printInputPrompt();

        int userInput = (int) scannerWrapper.numberInput();
        switch (userInput) {
            case 1:
                return SortType.BY_DATE_ASC;
            case 2:
                return SortType.BY_DATE_DESC;
            case 3:
                return SortType.BY_TIME_ASC;
            case 4:
                return SortType.BY_TIME_DESC;
            case 5:
                return SortType.BY_DISTANCE_ASC;
            case 6:
                return SortType.BY_DISTANCE_DESC;
            case 0:
                return SortType.BY_DATE_DESC; // defaulting back to standard sort
            default:
                return SortType.BY_DATE_DESC;
        }
    }
    //endregion


    //region Prints (should maybe be their own class)
    private void printSortTypesAlternatives() {

        System.out.println("Pick the order you wish to have.");
        System.out.print("1. Date (ascending)\n" +
                         "2. Date (descending)\n" +
                         "3. Time (ascending)\n" +
                         "4. Time (descending)\n" +
                         "5. Distance (ascending)\n" +
                         "6. Distance (descending)\n" +
                         "0. Exit");

    }

    private void printUserDetailsMenu() {
        System.out.println("1. Edit your details\n" +
                           "0. Exit");
    }

    private void printSessionDetailMenu() {
        System.out.println("1. Edit session details\n" +
                           "2. Delete this session.\n" +
                           "0. Exit");
    }

    private void printFailedMenuChoice() {
        System.out.println("Invalid choice, please try again");
        scannerWrapper.promptEnterKey();
    }

    public void printMainMenu() {
        System.out.println("1. User Settings\n" +
                           "2. Session Menu\n" +
                           "0. Exit");
    }

    public void printUserEditMenu() {
        System.out.println("1. Change Name\n" +
                           "2. Change Age\n" +
                           "3. Change Weight\n" +
                           "4. Change Height\n" +
                           "0. Exit");
    }

    public void printSessionMenu() {
        System.out.println("1. Add session\n" +
                           "2. Search by name\n" +
                           "3. View all sessions\n" +
                           "0. Exit");
    }

    public void printInputPrompt() {
        System.out.print("\nPlease enter your choice: ");
    }

    public void printAllSessions(List<String> fullSessionList) {
        int i = 1;
        for (String s : fullSessionList) {
            System.out.println(i++ + ". " + s);
        }
        System.out.println( i + ". To change sort method");
        System.out.println("0. Exit");
    }
    //endregion
}
