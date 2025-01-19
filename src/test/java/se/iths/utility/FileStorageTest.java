package se.iths.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.core.Session;
import se.iths.core.SessionHandler;
import se.iths.core.SortType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
    public void saveRecordTest_ExceptionThrow() throws IOException {
        String sessionId = "First File!";
        double distance = 5.0;
        int time = 3000;
        LocalDate date = LocalDate.now();

        doThrow(IOException.class).when(fileStorageMock).saveRecord(sessionId, distance, time, date);

        sessionHandler.createSession(sessionId, distance, time, date);

        verify(fileStorageMock, times(1)).saveRecord(sessionId, distance, time, date);
        assertThrows(IOException.class, () -> fileStorageMock.saveRecord(sessionId, distance, time, date));
    }

    // readSession occurs when we want to bring out 1 session instance.
    // In this case it would occur when viewing details, or session manipulation.
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
        when(fileStorageMock.loadRecord(anyString()))
                .thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            sessionHandler.readSession(anyString());
        });
        verify(fileStorageMock, times(1)).loadRecord(anyString());
    }

    @Test
    public void deleteRecordTest() throws IOException {
        sessionHandler.createSession("Delete me!", 5, 3000, LocalDate.now());

        sessionHandler.deleteSession("Delete me!");

        verify(fileStorageMock, times(1)).deleteRecord("Delete me!");
        assertFalse(sessionHandler.getSessionIDs().contains("Delete me!"), "Expected session was not deleted.");
    }

    @Test
    public void getRecordIdsTest() {
        when(fileStorageMock.getRecordIDs())
                .thenReturn(new ArrayList<>());

        sessionHandler.getSessionIDs();

        verify(fileStorageMock, times(1)).getRecordIDs();
    }

    // Test for the method being called during search of session.
    // The "error" message of no match is resolved outside the called method. resolveSessionSearch() in MenuHandler.
    @Test
    public void getSessionIDsTest_SearchNoMatchFound() {
        when(fileStorageMock.getRecordIDs())
                .thenReturn(new ArrayList<>());

        sessionHandler.searchSessions_ByID("The Maximus Hike");

        verify(fileStorageMock, times(1)).getRecordIDs();
    }

    // Doesn't return anything since the collection is empty. But illustrates that it's called.
    @Test
    public void getSessionIDsTest_GetSortedSessionIDs() {
        when(fileStorageMock.getRecordIDs())
                .thenReturn(new ArrayList<>());

        sessionHandler.getSortedSessions(SortType.BY_DISTANCE_ASC);

        verify(fileStorageMock, times(1)).getRecordIDs();
    }
}
