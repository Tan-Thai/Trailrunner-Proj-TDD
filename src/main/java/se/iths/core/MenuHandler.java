package se.iths.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuHandler {
    public MenuHandler() {
    }

    public void addSession(SessionHandler sessionHandler) {
        System.out.print("Please enter the name of the session: ");

        sessionHandler.createSession(
                "Bloop",
                12.3,
                30934,
                LocalDate.of(1990, 1, 1));
    }

    public void RemoveSession(SessionHandler sessionHandler) {
    }

    public void searchSession() {
    }

    public void displayMenu() {
    }

}
