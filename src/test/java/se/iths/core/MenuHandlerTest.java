package se.iths.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.utility.ScannerSingleton;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class MenuHandlerTest {
    private SessionHandler sessionHandlerMock;
    private MenuHandler menuHandler;
    private ScannerSingleton scannerMock;

    @BeforeEach
    public void setUp() {
        // Mock ups
        sessionHandlerMock = mock(SessionHandler.class);
        scannerMock = mock(ScannerSingleton.class);

        // Have to replace my singleton scanner with the mock whenever called somehow
        // TODO figure out if singleton will disturb the mocking.

        menuHandler = new MenuHandler(sessionHandlerMock);
    }

    @Test
    void addSession() {
        // mocking inputs for a session.
        when(scannerMock.getScanner().nextLine())
                .thenReturn("TestID")
                .thenReturn("12.3")
                .thenReturn("4530")
                .thenReturn("2025-01-09");

        menuHandler.addSession();

        verify(sessionHandlerMock).createSession(
                "TestID",
                12.3,
                4530,
                LocalDate.of(2025, 1, 9));
    }

    @Test
    void deleteSession() {
        // same principle as above method. "mocks" input from user wanting to delete "TestID"
        when(scannerMock.getScanner().nextLine())
                .thenReturn("TestID");

        menuHandler.deleteSession();

        verify(sessionHandlerMock).deleteSession("TestID");
    }

    @Test
    void searchSession() {
        // once gain the same but the difference
        when(scannerMock.getScanner().nextLine())
                .thenReturn("TestID");

        when(sessionHandlerMock.readSession("TestID"))
                .thenReturn(new Session(
                        "TestID",
                        7,
                        2500,
                        LocalDate.of(2025,1,9)));

        menuHandler.searchSession();

        verify(sessionHandlerMock).readSession("TestID");
    }

    @Test
    void exitProgramTest() {
        when(scannerMock.getScanner().nextLine())
                .thenReturn("0");

        menuHandler.displayMenu();

        // from my understanding it's a check that ensures that there are no more interactions after.
        verifyNoInteractions(sessionHandlerMock);
    }
}
