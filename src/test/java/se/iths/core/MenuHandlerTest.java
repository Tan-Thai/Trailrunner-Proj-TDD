package se.iths.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import se.iths.utility.FileStorage;
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
        user = new User("Old Name", 25, 70, 175, new SessionHandler(new FileStorage()));

        user.getSessionCollection().createSession("Bloop", 8,3600, LocalDate.of(2024, 12, 30));
        user.getSessionCollection().createSession("New years run!", 3,1230, LocalDate.of(2025, 1, 1));
        user.getSessionCollection().createSession("Morning walk", 4,4200, LocalDate.of(2025, 1, 2));

        menuHandler = new MenuHandler(scannerMock, user);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalPrintStream);
    }

    //region CRUD Tests
    // TODO User should be able to save attributes to a session.
    @Test
    void addSessionToCollectionTest() {

        when(scannerMock.numberInput())
                .thenReturn(2.0) // enter session menu
                .thenReturn(1.0) // add session
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
    void editUserDetails_ValidNameChange() {

        // adding a 3rd input to exit the menu, otherwise it would loop within itself forever.
        when(scannerMock.numberInput())
                .thenReturn(1.0) // enter view user details
                .thenReturn(1.0) // enter user settings
                .thenReturn(1.0) // enter change username
                .thenReturn(0.0); // exit the menu to exit the program.

        when(scannerMock.yesOrNoInput())
                .thenReturn(true); // confirm the want to change

        when(scannerMock.textInput(InputLimit.USERNAME.getLimit()))
                .thenReturn("New Name"); // the new input of name

        menuHandler.runMenu();

        assertEquals("New Name", user.getName(), "User name should be updated.");
    }

    @Test
    void editUserDetails_AbortedNameChange() {
        // adding a 3rd input to exit the menu, otherwise it would loop within itself forever.
        when(scannerMock.numberInput())
                .thenReturn(1.0) // enter view user details
                .thenReturn(1.0) // enter user settings
                .thenReturn(1.0) // enter change username
                .thenReturn(0.0);

        when(scannerMock.yesOrNoInput())
                .thenReturn(false); // abort change.

        menuHandler.runMenu();

        assertEquals("Old Name", user.getName(), "User name should *not* be updated.");
    }

    @Test
    void deleteSessionQueryTest() {
        // Test expectations : enter session view (ev. details) from runMenu()

        when(scannerMock.numberInput())
                .thenReturn(2.0) // enter session menu
                .thenReturn(3.0) // view sessions
                .thenReturn(1.0 ) // choose 1st session
                .thenReturn(2.0) // choose delete
                .thenReturn(0.0);

        when(scannerMock.yesOrNoInput()).thenReturn(true); // confirms deletion

        menuHandler.runMenu();

        assertTrue(user.getSessionCollection().getSessionIDs().size() == 2);
    }
    //endregion

    // TODO Filter related searches is under this region.
    //region Search Tests
    @Test
    void resolveSessionSearch_ByString_ValidSearch() {
        user.getSessionCollection().createSession("Bloop2", 8,3600, LocalDate.of(2024, 12, 30));
        user.getSessionCollection().createSession("Bloop3", 8,3600, LocalDate.of(2024, 12, 30));

        when(scannerMock.numberInput())
                .thenReturn(2.0) // session menu
                .thenReturn(2.0) // search menu
                .thenReturn(1.0) // search by string
                .thenReturn(0.0);

        when(scannerMock.textInput(InputLimit.SESSION_NAME.getLimit()))
                .thenReturn("Bloop");

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

    // TODO Error message prints out when user looks for a session that does not exist/non-existent string ID.
    @Test
    void resolveSessionSearch_ByString_InvalidSearch() {

        when(scannerMock.numberInput())
                .thenReturn(2.0) // session menu
                .thenReturn(2.0) // search menu
                .thenReturn(1.0)  // search by string
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
    void resolveSessionSearch_ByDistance_ValidSearch() {
        user.getSessionCollection().createSession("Bloop2", 8,3600, LocalDate.of(2024, 12, 30));

        when(scannerMock.numberInput())
                .thenReturn(2.0) // session menu
                .thenReturn(2.0) // search menu
                .thenReturn(2.0) // choose to search by distance
                .thenReturn(8.0) // enter distance in km.
                .thenReturn(0.0);

        menuHandler.runMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("1. Bloop");
        int endIndex = actual.indexOf("Please enter your choice:", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "1. Bloop\n" +
                          "2. Bloop2\n" +
                          "3. To change sort method\n" +
                          "0. Exit";

        assertEquals(expected, actual, "Session search result does not match.");
    }

    @Test
    void resolveSessionSearch_ByTime_ValidSearch() {

        when(scannerMock.numberInput())
                .thenReturn(2.0) // session menu
                .thenReturn(2.0) // search menu
                .thenReturn(3.0) // choose to search by time
                .thenReturn(60.0) // enter time in min
                .thenReturn(0.0);

        menuHandler.runMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("1. Bloop");
        int endIndex = actual.indexOf("Please enter your choice:", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "1. Bloop\n" +
                          "2. To change sort method\n" +
                          "0. Exit";

        assertEquals(expected, actual, "Session search result does not match.");
    }

    @Test
    void resolveSessionSearch_ByDate_ValidSearch() {
        when(scannerMock.numberInput())
                .thenReturn(2.0) // session menu
                .thenReturn(2.0) // search menu
                .thenReturn(4.0) // choose to search by date
                .thenReturn(0.0);

        when(scannerMock.dateInput())
                .thenReturn(LocalDate.of(2025, 1, 2)); // enter date

        menuHandler.runMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        int startIndex = actual.indexOf("1. Morning walk");
        int endIndex = actual.indexOf("Please enter your choice:", startIndex);
        actual = actual.substring(startIndex, endIndex).trim();

        String expected = "1. Morning walk\n" +
                          "2. To change sort method\n" +
                          "0. Exit";

        assertEquals(expected, actual, "Session search result does not match.");
    }
    //endregion

    //region Print Tests
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
                .thenReturn(2.0) // view session menu
                .thenReturn(3.0) // view session list
                .thenReturn(4.0) // change sort order
                .thenReturn(3.0) // sort by time ascending
                .thenReturn(0.0) // exit list view
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
    //endregion

}
