package se.iths.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import se.iths.utility.ScannerWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuHandlerTest {
    private MenuHandler menuHandler;
    private PrintStream originalPrintStream;
    private ByteArrayOutputStream outputStream;

    @Mock
    private ScannerWrapper scannerMock;
    private User user;

    @BeforeEach
    public void setUp() {
        scannerMock = mock(ScannerWrapper.class);
        outputStream = new ByteArrayOutputStream();
        originalPrintStream = System.out;
        System.setOut(new PrintStream(outputStream));
        user = new User("Old Name", 25, 70, 175, new SessionHandler());

        user.getSessionCollection().createSession("Bloop", 8,3600, LocalDate.of(2024, 12, 30));
        user.getSessionCollection().createSession("New years run!", 3,1230, LocalDate.of(2025, 1, 1));
        user.getSessionCollection().createSession("Morning walk", 4,4200, LocalDate.of(2025, 1, 2));

        menuHandler = new MenuHandler(scannerMock, user);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalPrintStream);
    }

    @Test
    public void editUserDetails_ValidNameChange() {

        // adding a 3rd input to exit the menu, otherwise it would loop within itself forever.
        when(scannerMock.numberInput())
                .thenReturn(1.0)
                .thenReturn(1.0)
                .thenReturn(1.0)
                .thenReturn(0.0);
        when(scannerMock.yesOrNoInput()).thenReturn(true);
        when(scannerMock.textInput(InputLimit.USERNAME.getLimit())).thenReturn("New Name");

        menuHandler.runMenu();

        assertEquals("New Name", user.getName(), "User name should be updated.");
    }

    @Test
    public void editUserDetails_AbortedNameChange() {
        // adding a 3rd input to exit the menu, otherwise it would loop within itself forever.
        when(scannerMock.numberInput())
                .thenReturn(1.0)
                .thenReturn(1.0)
                .thenReturn(1.0)
                .thenReturn(0.0);
        when(scannerMock.yesOrNoInput()).thenReturn(false);

        menuHandler.runMenu();

        assertEquals("Old Name", user.getName(), "User name should *not* be updated.");
    }

    @Test
    public void deleteSessionQueryTest() {
        // Test expectations : enter session view (ev. details) from runMenu()

        when(scannerMock.numberInput())
                .thenReturn(2.0)
                .thenReturn(3.0)
                .thenReturn(1.0)
                .thenReturn(2.0)
                .thenReturn(0.0);
        when(scannerMock.yesOrNoInput()).thenReturn(true);

        menuHandler.runMenu();

        assertTrue(user.getSessionCollection().getSessionIDs().size() == 2);
    }

    @Test
    void resolveSessionSearch_ValidSearch() {
        user.getSessionCollection().createSession("Bloop2", 8,3600, LocalDate.of(2024, 12, 30));
        user.getSessionCollection().createSession("Bloop3", 8,3600, LocalDate.of(2024, 12, 30));

        when(scannerMock.numberInput())
                .thenReturn(2.0)
                .thenReturn(2.0)
                .thenReturn(0.0);

        when(scannerMock.textInput(InputLimit.SESSION_NAME.getLimit())).thenReturn("Bloop");

        menuHandler.runMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("1. Bloop");
        int endIndex = actual.indexOf("Please enter your choice:", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "1. Bloop\n" +
                          "2. Bloop2\n" +
                          "3. Bloop3\n" +
                          "4. To change sort method\n" +
                          "0. Exit";

        assertEquals(expected, actual, "Session search result does not match.");
    }

    @Test
    void resolveSessionSearch_InvalidSearch() {

        when(scannerMock.numberInput())
                .thenReturn(2.0)
                .thenReturn(2.0)
                .thenReturn(0.0);

        when(scannerMock.textInput(InputLimit.USERNAME.getLimit())).thenReturn("YOOO THAT ONE EXTREME DAY");

        menuHandler.runMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("No sessions found, ");
        int endIndex = actual.indexOf("returning to main menu.\n", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "No sessions found,";

        assertEquals(expected, actual, "0 query results print does not match.");
    }

    @Test
    void addSessionToCollectionTest() {

        when(scannerMock.numberInput())
                .thenReturn(2.0)
                .thenReturn(1.0)
                .thenReturn(5.1) // distance in km
                .thenReturn(30.2) // duration in min (convert behind the scenes)
                .thenReturn(0.0);

        when(scannerMock.textInput(InputLimit.SESSION_NAME.getLimit()))
                .thenReturn("One cold run");

        when(scannerMock.dateInput())
                .thenReturn(LocalDate.of(2025, 1, 13));

        menuHandler.runMenu();

        // finds the start point of our check, since stream keeps *all* text printed.
        // Finds the end string-line starting the search from the start index pos.
        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("Please enter the corresponding info for this session:");
        int endIndex = actual.indexOf("Session created successfully!", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "Please enter the corresponding info for this session:\n" +
                          "\nName of the session: " +
                          "\nDistance in km: " +
                          "\nDuration in minutes: " +
                          "\nDate (YYYY-MM-DD):";
        assertEquals(expected, actual, "Print for session creation does not match.");

        List<String> sessions = user.getSessionCollection().getSessionIDs();
        assertTrue(sessions.contains("One cold run"), "Created session was not found.");
    }

    @Test
    void printMainMenuTest() {

        String expected = "1. User Settings\n" +
                          "2. Session Menu\n" +
                          "0. Exit\n";

        menuHandler.printMainMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printSessionMenuTest() {
        String expected = ("1. Add session\n" +
                           "2. Search by name\n" +
                           "3. View all sessions\n" +
                           "0. Exit\n");

        menuHandler.printSessionMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printUserMenuTest() {
        String expected = "1. Change Name\n" +
                          "2. Change Age\n" +
                          "3. Change Weight\n" +
                          "4. Change Height\n" +
                          "0. Exit\n";

        menuHandler.printUserEditMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printAllSessionsTest() {
        SessionHandler sessionHandler = user.getSessionCollection();
        List<String> fullSessionList = sessionHandler.getSessionIDs();

        menuHandler.printAllSessions(fullSessionList);

        String expected = "1. Bloop\n" +
                          "2. Morning walk\n" +
                          "3. New years run!\n" +
                          "4. To change sort method\n" +
                          "0. Exit\n";
        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void changeSortMethod_SessionPrintTest() {
        // tests both the menu system to change sort-order. time-asc specifically.

        when(scannerMock.numberInput())
                .thenReturn(2.0)
                .thenReturn(3.0)
                .thenReturn(4.0)
                .thenReturn(3.0)
                .thenReturn(0.0)
                .thenReturn(0.0);

        menuHandler.runMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("1. New years run!");
        int endIndex = actual.indexOf("Please enter your choice:", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "1. New years run!\n" +
                          "2. Bloop\n" +
                          "3. Morning walk\n" +
                          "4. To change sort method\n" +
                          "0. Exit";

        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printUserPromptTest() {
        String expected = "\nPlease enter your choice: ";

        menuHandler.printInputPrompt();

        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }


}
