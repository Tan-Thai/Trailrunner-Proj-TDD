package se.iths;

import se.iths.core.MenuHandler;
import se.iths.core.SessionHandler;
import se.iths.core.User;
import se.iths.utility.FileStorage;
import se.iths.utility.ScannerWrapper;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        ScannerWrapper scanner = new ScannerWrapper();
        FileStorage fileStorage = new FileStorage();
        User user = createTempUser(fileStorage);
        addSessionCollection(user);

        MenuHandler menuHandler = new MenuHandler(scanner, user);
        menuHandler.runMenu();
        scanner.closeScanner();
    }

    private static User createTempUser(FileStorage fileStorage) {
        return new User(
                "Tan",
                29,
                177,
                50,
                new SessionHandler(fileStorage));
    }

    private static SessionHandler addSessionCollection(User user) {
        SessionHandler sessionHandler = user.getSessionCollection();
        sessionHandler.createSession("Short Fast Run - Christmas!", 3, 2030, LocalDate.of(2024, 12, 24));
        sessionHandler.createSession("New year, new me!", 11.2, 5032, LocalDate.of(2025, 1, 5));
        sessionHandler.createSession("Not that new I'm afraid...", 7, 3203, LocalDate.of(2025, 1, 13));
        return sessionHandler;
    }

}
