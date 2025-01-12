package se.iths;

import se.iths.core.MenuHandler;
import se.iths.core.SessionHandler;
import se.iths.core.User;
import se.iths.utility.ScannerWrapper;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        ScannerWrapper scanner = new ScannerWrapper();
        User user = createMockUser();
        addSessionCollection(user);

        MenuHandler menuHandler = new MenuHandler(scanner, user);

        menuHandler.printMainMenu();

    }

    private static User createMockUser() {
        return new User(
                "Tan",
                29,
                177,
                50,
                new SessionHandler());
    }

    private static SessionHandler addSessionCollection(User user) {
        SessionHandler sessionHandler = user.getSessionCollection();
        sessionHandler.createSession("Bloop2", 3, 2030, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("Most Recent", 33, 5032, LocalDate.of(2025, 1, 5));
        sessionHandler.createSession("Bloop", 12.3, 30934, LocalDate.of(2000, 4, 2));
        return sessionHandler;
    }

}
