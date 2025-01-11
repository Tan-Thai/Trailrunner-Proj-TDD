package se.iths.utility;

import java.util.Scanner;

public class ScannerWrapper {
    private final Scanner scanner;

    //region Public Methods
    public ScannerWrapper() {
        this.scanner = new Scanner(System.in);
    }

    public String textInput(int maxLength) {
        return checkIfValidString(maxLength);
    }

    public int numberInput() {
        return checkIfNumber();
    }

    public boolean yesOrNoInput() {
        return checkYesOrNo();
    }

    public void promptEnterKey() {
        System.out.print("\nPress \"ENTER\" to continue.");

        clearScanner(scanner);
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
    //endregion

    // region Private Methods
    private String checkIfValidString(int maxLength) {
        String userInput;

        do {
            userInput = scanner.nextLine();

            if (!userInput.isEmpty() && userInput.length() <= maxLength) {
                return userInput;
            }

            if (userInput.isEmpty()) {
                System.err.print("Please enter a name: ");

            } else if (userInput.length() > maxLength) {
                System.err.println(
                        "The name you entered is too long. Please enter a name with a maximum of " + maxLength +
                        " characters");

                System.err.print("Please enter a name: ");
            }

        } while (true);
    }

    // endregion
}
