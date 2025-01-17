package se.iths.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.core.Session;
import se.iths.core.SessionHandler;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileStorageTest {
    private FileStorage fileStorageMock;
    private SessionHandler sessionHandler;

    @BeforeEach
    public void setUp() {
        fileStorageMock = mock(FileStorage.class);
        sessionHandler = new SessionHandler(fileStorageMock);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void saveRecordTest() throws IOException {
        // Setup/Input
        String sessionId = "First File!";
        double distance = 5.0;
        int time = 3000;
        LocalDate date = LocalDate.now();

        doNothing().when(fileStorageMock).saveRecord(sessionId, distance, time, date);

        // Act
        sessionHandler.createSession(sessionId, distance, time, date);

        //Assert - times(1) since we want to ensure that it got called.
        verify(fileStorageMock, times(1)).saveRecord(sessionId, distance, time, date);
    }

    @Test
    public void loadRecordTest_FoundMatch() throws IOException {
        sessionHandler.createSession("I exist!", 5, 3000, LocalDate.now());
        Session expectedSession = sessionHandler.readSession("I exist!");

        when(fileStorageMock.loadRecord("I exist!"))
                .thenReturn(expectedSession);

        verify(fileStorageMock, times(1)).loadRecord("I exist!");
        // I would normally add an assertEquals here but this method does not return anything properly
        // as of now. The actual stored session is tested within SessionHandlerTest.
    }

    @Test
    public void loadRecordTest_NoMatchFound() throws IOException {
        // error message need to be sent out when match wasn't found.
        when(fileStorageMock.loadRecord("I exist! Maybe?"))
                .thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.readSession("I exist! Maybe?");
        });
        verify(fileStorageMock, times(1)).loadRecord("I exist! Maybe?");
    }

    @Test
    public void deleteRecordTest() throws IOException {
        sessionHandler.createSession("Delete me!", 5, 3000, LocalDate.now());

        sessionHandler.deleteSession("Delete me!");

        verify(fileStorageMock, times(1)).deleteRecord("Delete me!");
        assertFalse(sessionHandler.getSessionIDs().contains("Delete me!"), "Expected session was not deleted.");
    }
}
