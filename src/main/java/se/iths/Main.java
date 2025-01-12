package se.iths;

import se.iths.core.MenuHandler;
import se.iths.utility.ScannerWrapper;

public class Main {

    public static void main(String[] args) {
        MenuHandler menuHandler = new MenuHandler();
        ScannerWrapper scanner = new ScannerWrapper();

        menuHandler.printMainMenu();

        scanner.closeScanner();
    }

}
