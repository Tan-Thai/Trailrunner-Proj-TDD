package se.iths.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.core.Session;
import se.iths.core.SessionHandler;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertTrue(fileStorageMock.getRecordIDs().contains("First File!"), "Expected file not found.");
    }

    @Test
    public void retrieveRecordTest() throws IOException {
        Session expectedSession = new Session("I exist!", 5, 3000, LocalDate.now());

        when(fileStorageMock.LoadRecord("I exist!"))
                .thenReturn(expectedSession);

        Session actualSession = sessionHandler.readSession("I exist!");
        verify(fileStorageMock).LoadRecord("I exist!");
        assertEquals(expectedSession, actualSession, "Returned session does not match.");
    }


}
