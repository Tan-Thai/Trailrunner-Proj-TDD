package se.iths.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import se.iths.utility.ScannerWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        user = new User("OldName", 25, 70, 175, new SessionHandler());
        menuHandler = new MenuHandler(scannerMock, user);
        outputStream = new ByteArrayOutputStream();
        originalPrintStream = System.out;

        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalPrintStream);
    }

    @Test
    public void resolveUserConfig_ValidNameChange() {

        // adding a 3rd input to exit the menu, otherwise it would loop within itself forever.
        when(scannerMock.numberInput())
                .thenReturn(1.0)
                .thenReturn(1.0)
                .thenReturn(0.0);
        when(scannerMock.yesOrNoInput()).thenReturn(true); // Confirm change
        when(scannerMock.textInput(15)).thenReturn("NewName"); // Provide new name


        menuHandler.runMenu();

        assertEquals("NewName", user.getName(), "User name should be updated.");
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
        String expected = "1. View all sessions\n" +
                          "2. Search by name\n" +
                          "0. Exit\n";

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

        menuHandler.printUserSettingsMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printQueryResultTest() {
        // creating a session handler with 3 sessions
        SessionHandler sessionHandler = user.getSessionCollection();
        sessionHandler.createSession("Bloop2", 3, 2030, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("EXTRA", 33, 5032, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("Bloop", 12.3, 30934, LocalDate.of(1990, 1, 1));

        List<String> queryResult = sessionHandler.searchSessionByID("Bloop");
        String expected = "1. Bloop\n" +
                          "2. Bloop2\n";

        menuHandler.printQueryResult(queryResult);

        String actual = outputStream.toString().replace("\r\n", "\n");
        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printAllSessionsTest() {

        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.createSession("Bloop2", 3, 2030, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("EXTRA", 33, 5032, LocalDate.of(1990, 1, 4));
        sessionHandler.createSession("Bloop", 12.3, 30934, LocalDate.of(1990, 1, 1));

        List<String> fullSessionList = sessionHandler.getSessionIDs();
        String expected = "1. Bloop\n" +
                          "2. EXTRA\n" +
                          "3. Bloop2\n";

        menuHandler.printAllSessions(fullSessionList);

        String actual = outputStream.toString().replace("\r\n", "\n");
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
