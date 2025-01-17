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

        // Act
        sessionHandler.createSession("First File!", 5, 3000, LocalDate.now());

        //Assert - times(1) since we want to ensure that it got called.
        verify(fileStorageMock).SaveRecord("First File!", 5, 3000, LocalDate.now());
        assertTrue(fileStorageMock.getRecordIDs().contains("First File!"), "Expected ID not found.");
    }

    @Test
    public void loadRecordTest() throws IOException {
        Session expectedSession = new Session("I exist!", 5, 3000, LocalDate.now());

        when(fileStorageMock.loadRecord("I exist!"))
                .thenReturn(expectedSession);

        Session actualSession = sessionHandler.readSession("I exist!");
        verify(fileStorageMock).loadRecord("I exist!");
        assertEquals(expectedSession, actualSession, "Returned session does not match.");
    }

    @Test
    public void loadRecordTest_NoMatchFound() throws IOException {
        // error message need to be sent out when match wasn't found.
        when(fileStorageMock.loadRecord("I exist! Maybe?"))
                .thenReturn(null);

        verify(fileStorageMock).loadRecord("I exist! Maybe?");
    }

    @Test
    public void deleteRecordTest() throws IOException {
        sessionHandler.createSession("Delete me!", 5, 3000, LocalDate.now());

        fileStorageMock.deleteRecord("Delete me!");

        verify(fileStorageMock, times(1)).deleteRecord("Delete me!");
        assertFalse(fileStorageMock.getRecordIDs().contains("Delete me!"), "Expected session was not deleted.");
    }

}
