package se.iths.core;

import se.iths.utility.ScannerWrapper;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

public class MenuHandler {
    private ScannerWrapper scannerWrapper;
    private User user;


    public MenuHandler(ScannerWrapper scannerWrapper,User user) {
        this.scannerWrapper = scannerWrapper;
        this.user = user;
    }

    public void runMenu() {

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
                           "4. Change Height" +
                           "0. Exit");

    }

    public void printSessionMenu() {
        System.out.println("1. View all sessions\n" +
                           "2. Search by name\n" +
                           "0. Exit");
    }

    public void printUserChoicePrompt() {
        System.out.print("\nPlease enter your choice: ");
    }

    public void printQueryResult(List<String> queryResult) {

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
    }
}
