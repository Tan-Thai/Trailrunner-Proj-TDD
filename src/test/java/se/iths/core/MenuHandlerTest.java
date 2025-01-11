package se.iths.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.iths.utility.ScannerSingleton;

import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class MenuHandlerTest {
    private SessionHandler sessionHandlerMock;
    private MenuHandler menuHandler;
    private ScannerSingleton scannerSingletonMock;
    private Scanner scannerMock;

    @BeforeEach
    public void setUp() {
        // Mock ups
        sessionHandlerMock = mock(SessionHandler.class);
        scannerSingletonMock = mock(ScannerSingleton.class);
        scannerMock = mock(Scanner.class);

        // Have to replace my singleton scanner with the mock whenever called somehow
        // TODO figure out if singleton will disturb the mocking.
        // from my understanding, we are creating a MockedStatic of ScannerSingleton. When the getInstance is called
        // we instead return the mocked instance?
        ScannerSingleton instance = ScannerSingleton.getInstance();
        Mockito.mockStatic(ScannerSingleton.class)
                .when(ScannerSingleton::getInstance)
                .thenReturn(scannerSingletonMock);

        when(scannerSingletonMock.getScanner()).thenReturn(scannerMock);

        menuHandler = new MenuHandler(sessionHandlerMock);
    }

    @Test
    void addSession() {
        // mocking inputs for a session.
        when(scannerSingletonMock.getScanner().nextLine())
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
        when(scannerSingletonMock.getScanner().nextLine())
                .thenReturn("TestID");

        menuHandler.deleteSession();

        verify(sessionHandlerMock).deleteSession("TestID");
    }

    @Test
    void searchSession() {
        // once gain the same but the difference
        when(scannerSingletonMock.getScanner().nextLine())
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
        when(scannerSingletonMock.getScanner().nextLine())
                .thenReturn("0");

        menuHandler.displayMenu();

        // from my understanding it's a check that ensures that there are no more interactions after.
        verifyNoInteractions(sessionHandlerMock);
    }
}
