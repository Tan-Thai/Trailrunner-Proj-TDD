package se.iths.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.utility.FileStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionHandlerTest {
    private SessionHandler sessionHandler;
    private FileStorage fileStorage;

    @BeforeEach
    void setUp() {
        fileStorage = new FileStorage();
        sessionHandler = new SessionHandler(fileStorage);

        sessionHandler.createSession(
                "Bloop",
                12.3,
                30934,
                LocalDate.of(1990, 1, 1));
    }

    @Test
    void createSessionTest() {
        Session pulledTestSession = sessionHandler.readSession("Bloop");

        assertNotNull(pulledTestSession,"The pulled record is null");
        assertEquals("Bloop", pulledTestSession.getId(), "The pulled record id is incorrect");

        // I'll be honest and say that this is something new for me so im slapping together things from StackOverflow.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sessionHandler.createSession(
                "Bloop",
                12.3,
                30934,
                LocalDate.of(2000, 4, 2)
        ));
        assertEquals("A recorded session with this ID already exists.", exception.getMessage());
    }

    @Test
    void readSessionTest() {
        Session pulledTestSession = sessionHandler.readSession("Bloop");

        assertNotNull(pulledTestSession, "The pulled record is null");
        assertEquals("Bloop", pulledTestSession.getId(), "The pulled record id is incorrect");
        assertEquals(12.3, pulledTestSession.getDistance(), "The pulled record distance is incorrect");
        assertEquals(30934, pulledTestSession.getTime(), "The pulled record time is incorrect");
        assertEquals(LocalDate.of(1990, 1, 1), pulledTestSession.getDate(), "The pulled record date is incorrect");

        //TODO Case-sensitivity is a thing, fix so it matches despite capital/small letters.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.readSession("blop");
        });
        assertEquals("No recorded session with this ID exists.", exception.getMessage());
    }

    @Test
    void getSessionIDsTest() {
        sessionHandler.createSession(
                "Bloop2",
                3,
                2030,
                LocalDate.of(1990, 1, 4));

        sessionHandler.createSession(
                "EXTRA",
                33,
                5032,
                LocalDate.of(1990, 1, 4));

        List<String> recordIDs = sessionHandler.getSessionIDs();
        // "EXTRA" was spicy and took the 1 spot.
        assertNotNull(recordIDs, "The recordIDs is null");
        assertEquals(3, recordIDs.size(), "The recordIDs size is incorrect");
        assertEquals("Bloop", recordIDs.get(0), "The record ID is incorrect");
        assertEquals("Bloop2", recordIDs.get(2), "The record ID is incorrect");
        assertEquals("EXTRA", recordIDs.get(1), "The record ID is incorrect");

    }

    @Test
    void searchSessionsByIdTest() {
        sessionHandler.createSession("Bloop2", 3, 2030, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("EXTRA", 33, 5032, LocalDate.of(1990, 1, 4));

        SessionHandler foundSessions = sessionHandler.searchSessions_ByID("Bloop");
        List<String> sessionList = foundSessions.getSessionIDs();

        assertFalse(sessionList.isEmpty(), "Expected to find at least one session.");
        assertEquals(sessionList.size(), 2, "Expected two sessions.");

        // Emptying sessions-handler to test 0 results
        sessionHandler.deleteSession("Bloop");
        sessionHandler.deleteSession("Bloop2");
        sessionHandler.deleteSession("EXTRA");
        sessionList = sessionHandler.getSessionIDs();

        assertTrue(sessionList.isEmpty(), "Expected to not find any session.");

    }

    // keeping it as a single one for now. But if we want to implement by date, alphabetical, ascending, descending etc.
    // Then this test will have to be refactored to take a parameter to tell it which way to sort.
    @Test
    void getSortedSessionsTest_Date_Descending() {
        sessionHandler.createSession("Bloop2", 3, 2030, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("Most Recent", 33, 5032, LocalDate.of(2025, 1, 5));

        List<String> sortedList = sessionHandler.getSortedSessions(SortType.BY_DATE_DESC);

        assertTrue(sortedList.get(0).equals("Most Recent"), "Expected the most recent session to be first.");
        assertTrue(sortedList.get(1).equals("Bloop2"), "Expected the second most recent session.");
        assertTrue(sortedList.get(2).equals("Bloop"), "Expected the oldest session.");
    }

    @Test
    void getSortedSessionsTest_Distance_Ascending() {
        sessionHandler.createSession("Shortest Distance", 3, 2030, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("Longest Distance", 33, 5032, LocalDate.of(2025, 1, 5));

        List<String> sortedList = sessionHandler.getSortedSessions(SortType.BY_DISTANCE_ASC);

        assertTrue(sortedList.get(0).equals("Shortest Distance"), "Expected the shortest distance to be first.");
        assertTrue(sortedList.get(1).equals("Bloop"), "Expected the second shortest distance.");
        assertTrue(sortedList.get(2).equals("Longest Distance"), "Expected the furthest distance.");
    }

    @Test
    void deleteSessionTest() {
        // once again lots of StackOverflow shenanigans
        // This confirms that we don't hit an Exception and pass through properly.
        assertDoesNotThrow(()-> sessionHandler.deleteSession("Bloop"));

        // Attempting to read the "removed" record.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.readSession("Bloop");
        });
        assertEquals("No recorded session with this ID exists.", exception.getMessage());

        // Trying to delete something that does not exist
        Exception deleteException = assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.deleteSession("ThisIdDoesNotExist");
        });
        assertEquals("No recorded session with this ID exists.", deleteException.getMessage());

    }
}