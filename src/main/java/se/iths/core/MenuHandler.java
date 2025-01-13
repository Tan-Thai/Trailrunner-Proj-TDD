package se.iths.core;

import se.iths.utility.CmdUtility;
import se.iths.utility.ScannerWrapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private ScannerWrapper scannerWrapper;
    private User user;


    public MenuHandler(ScannerWrapper scannerWrapper,User user) {
        this.scannerWrapper = scannerWrapper;
        this.user = user;
    }

    public void runMenu() {

        while (true) {
            CmdUtility.clearConsole();
            printMainMenu();
            printInputPrompt();
            double userInput = scannerWrapper.numberInput();

            switch ((int) userInput){
                case 1:
                    runUserMenu();
                    break;
                case 2:
                    runSessionMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice, please try again");
                    scannerWrapper.promptEnterKey();
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
                resolveSessionCreation();
                break;
            case 2:
                resolveSessionSearch();
                break;
            case 3:
                List<String> sessionList = this.user.getSessionCollection().getSortedSessions(SortType.BY_DATE_DESC);
                resolveSessionView(sessionList);
                break;
            case 0:
                return;
            default:
                break;
        }
    }

    private void resolveSessionSearch() {
        CmdUtility.clearConsole();
        System.out.print("Enter the search term: "); // not sure how to ask this in a less formal way 🙃
        String searchQuery = scannerWrapper.textInput(15);
        List<String> foundSessions = user.getSessionCollection().searchSessionByID(searchQuery);

        resolveSessionView(foundSessions);
    }

    private void runUserMenu() {
        CmdUtility.clearConsole();
        printUserSettingsMenu();
        printInputPrompt();

        double userInput = scannerWrapper.numberInput();
        switch ((int) userInput){
            case 1:
                resolveUserConfig(UserConfig.NAME);
                break;
            case 2:
                resolveUserConfig(UserConfig.AGE);
                break;
            case 3:
                resolveUserConfig(UserConfig.WEIGHT);
                break;
            case 4:
                resolveUserConfig(UserConfig.HEIGHT);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice, please try again");
                scannerWrapper.promptEnterKey();
                break;
        }

    }

    private void resolveUserConfig(UserConfig choice) {
        CmdUtility.clearConsole();
        System.out.println("Are you sure you want to change your " + choice.name().toLowerCase() + "? (y/n)");
        printInputPrompt();

        if (scannerWrapper.yesOrNoInput()) {
            CmdUtility.clearConsole();
            System.out.print("Please enter your new " + choice.name().toLowerCase() + ": ");

            switch (choice) {
                case NAME:
                    user.setName(scannerWrapper.textInput(15));
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
                    System.out.println("Invalid choice, please try again");
                    scannerWrapper.promptEnterKey();
                    break;
            }
        }
    }

    public void printMainMenu() {
        System.out.println("1. User Settings\n" +
                           "2. Session Menu\n" +
                           "0. Exit");
    }

    public void printUserSettingsMenu() {
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

    public void printQueryResult(List<String> queryResult) {
        CmdUtility.clearConsole();
        int i = 1;
        for (String s : queryResult) {
            System.out.println(i++ + ". " + s);
        }
    }

    public void printAllSessions(List<String> fullSessionList) {
        int i = 1;
        for (String s : fullSessionList) {
            System.out.println(i++ + ". " + s);
        }
        // TODO need to print 0. Exit at the end
    }

    public void resolveSessionView(List<String> sessionList) {
        CmdUtility.clearConsole();
        printAllSessions(sessionList);
        printInputPrompt();

        int userInput = (int) scannerWrapper.numberInput();
        if (userInput > 0 && userInput <= sessionList.size()) {
            String selectedSession = sessionList.get(userInput - 1);
            Session pulledSession = user.getSessionCollection().readSession(selectedSession);
            viewSessionDetails(pulledSession);
        }
    }

    private void viewSessionDetails(Session pulledSession) {
        CmdUtility.clearConsole();
        System.out.println(
                "You have selected: " + pulledSession.getId() + "\n" +
                "Duration: " + pulledSession.getTime() + "\n" +
                "Distance: " + pulledSession.getDistance() + "\n" +
                "Date: " + pulledSession.getDate());

        printInputPrompt();

        int choice = (int) scannerWrapper.numberInput();
        switch (choice) {
            case 1:
                break;
            case 2:
                deleteSessionQuery(pulledSession);
                break;
            case 0:
                return;
            default:
                break;
        }
    }

    public void deleteSessionQuery(Session pulledSession) {
        CmdUtility.clearConsole();
        System.out.println("Are you sure you want to delete your " + pulledSession.getId() + "? (y/n)");
        printInputPrompt();

        if (scannerWrapper.yesOrNoInput()) {
            user.getSessionCollection().deleteSession(pulledSession.getId());
            System.out.println("Deleted session: " + pulledSession.getId());
            scannerWrapper.promptEnterKey();
        }

    }

    public void resolveSessionCreation() {
        CmdUtility.clearConsole();
        System.out.println("Please enter the corresponding info for this session:");
        System.out.print("Name of the session: \n");
        String sessionName = scannerWrapper.textInput(15);

        System.out.print("Distance in km: \n");
        double sessionDistance = scannerWrapper.numberInput();

        System.out.print("Duration in minutes: \n");
        int sessionDuration = (int) scannerWrapper.numberInput();

        // TODO This entire part below can become its own method within scanner.
        System.out.print("Date (YYYY-MM-DD): \n");
        String sessionDate = scannerWrapper.textInput(15);

        try {
            LocalDate date = LocalDate.parse(sessionDate);
            user.getSessionCollection().createSession(sessionName, sessionDistance ,sessionDuration, date);
            System.out.println("Session created successfully!");
            scannerWrapper.promptEnterKey();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            scannerWrapper.promptEnterKey();
        }
    }
}
