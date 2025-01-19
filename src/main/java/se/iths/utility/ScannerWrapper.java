package se.iths.utility;

import se.iths.core.InputLimit;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ScannerWrapper {
    private final Scanner scanner;

    //region Public Methods - Entry points into private methods.
    public ScannerWrapper() {
        this.scanner = new Scanner(System.in);
    }

    public String textInput(int maxLength) {
        return checkIfValidString(maxLength);
    }

    public double numberInput() {
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
                System.err.print("Please enter something: ");

            } else if (userInput.length() > maxLength) {
                System.err.println(
                        "What you entered is too long. Please with a maximum of " + maxLength +
                        " characters");

                System.err.print("Please try again: ");
            }

        } while (true);
    }

    private double checkIfNumber() {
        double userInput;
        while (true) {
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.err.print("Invalid input, please enter a number: ");
                continue;
            }

            try {
                userInput = Double.parseDouble(input);
                if (userInput < 0) {
                    System.err.print("Please enter a positive number: ");
                } else {
                    return userInput;
                }
            } catch (NumberFormatException e) {
                System.err.print("Invalid input, please enter a number: ");
            }
        }
    }

    private boolean checkYesOrNo() {

        do {
            String inputString = scanner.nextLine().trim();
            if (inputString.length() == 1) {
                char choice = inputString.charAt(0);
                choice = Character.toUpperCase(choice);

                switch (choice) {
                    case 'Y':
                        return true;
                    case 'N':
                        return false;
                }
            }
            System.err.print("Invalid input, please enter either Y or N: ");
        } while (true);
    }

    private void clearScanner(Scanner sc) {
        if (sc != null && sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    public LocalDate dateInput() {
        return checkIfValidDate();
    }

    public LocalDate checkIfValidDate() {
        /* Forced loop:
        If user enters a blank then it will use the current date.
        If the date is ahead of current time then an error will be put out.
        Try catch is if the input does not match anything at all in terms of (YYYY-MM-DD) structure.
        * */
        do {
            String userInput = textInput(InputLimit.DATE.getLimit());
            try {
                if (userInput.isBlank())
                    return LocalDate.now();

                if (LocalDate.parse(userInput).isAfter(LocalDate.now()))
                    System.err.print("That date is in the future, please enter a valid date: ");
                else
                    return LocalDate.parse(userInput);

            } catch (DateTimeParseException e) {
                System.err.print("Invalid input, please enter a valid date (YYYY-MM-DD): ");
            }
        } while (true);
    }
    // endregion
}
