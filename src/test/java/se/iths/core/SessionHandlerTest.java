package se.iths.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionHandlerTest {
    private SessionHandler sessionHandler;

    @BeforeEach
    void setUp() {
        sessionHandler = new SessionHandler();

        sessionHandler.createSession(
                "Bloop",
                12.3,
                30934,
                LocalDate.of(1990, 1, 1));
    }

    @Test
    void createSessionTest() {
        Session pulledTestSession = sessionHandler.readRecord("Bloop");

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
    void readRecordTest() {
        Session pulledTestSession = sessionHandler.readRecord("Bloop");

        assertNotNull(pulledTestSession, "The pulled record is null");
        assertEquals("Bloop", pulledTestSession.getId(), "The pulled record id is incorrect");
        assertEquals(12.3, pulledTestSession.getDistance(), "The pulled record distance is incorrect");
        assertEquals(30934, pulledTestSession.getTime(), "The pulled record time is incorrect");
        assertEquals(LocalDate.of(1990, 1, 1), pulledTestSession.getDate(), "The pulled record date is incorrect");

        //TODO Case-sensitivity is a thing, fix so it matches despite capital/small letters.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.readRecord("blop");
        });
        assertEquals("No recorded session with this ID exists.", exception.getMessage());
    }

    @Test
    void getRecordIDsTest() {
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

        List<String> recordIDs = sessionHandler.getRecordIDs();
        // "EXTRA" was spicy and took the 1 spot.
        assertNotNull(recordIDs, "The recordIDs is null");
        assertEquals(3, recordIDs.size(), "The recordIDs size is incorrect");
        assertEquals("Bloop", recordIDs.get(0), "The record ID is incorrect");
        assertEquals("Bloop2", recordIDs.get(2), "The record ID is incorrect");
        assertEquals("EXTRA", recordIDs.get(1), "The record ID is incorrect");

    }

    @Test
    void deleteRecordTest() {
        // once again lots of StackOverflow shenanigans
        // This confirms that we don't hit an Exception and pass through properly.
        assertDoesNotThrow(()-> sessionHandler.deleteRecord("Bloop"));

        // Attempting to read the "removed" record.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.readRecord("Bloop");
        });
        assertEquals("No recorded session with this ID exists.", exception.getMessage());

        // Trying to delete something that does not exist
        Exception deleteException = assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.deleteRecord("ThisIdDoesNotExist");
        });
        assertEquals("No recorded session with this ID exists.", deleteException.getMessage());

    }
}