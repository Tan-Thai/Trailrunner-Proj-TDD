package se.iths.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuHandlerTest {
    private MenuHandler menuHandler;
    private PrintStream originalPrintStream;
    private PrintStream originalErrStream;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        originalPrintStream = System.out;
        originalErrStream = System.err;
        outputStream = new ByteArrayOutputStream();
        menuHandler = new MenuHandler();

        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalPrintStream);
        System.setErr(originalErrStream);
    }

    @Test
    void printMainMenuTest() {
        menuHandler.printMainMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        String expected = "1. User Settings\n" +
                          "2. Session Menu\n" +
                          "0. Exit\n";

        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printSessionMenuTest() {
        menuHandler.printSessionMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        String expected = "1. View all sessions\n" +
                          "2. Search by name\n" +
                          "0. Exit\n";

        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printUserMenuTest() {
        menuHandler.printMainSettingsMenu();

        String actual = outputStream.toString().replace("\r\n", "\n");
        String expected = "1. Change Name\n" +
                          "2. Change Age\n" +
                          "3. Change Weight\n" +
                          "4. Change Height" +
                          "0. Exit\n";

        assertEquals(expected, actual, "Expected print does not match the actual output.");
    }

    @Test
    void printQueryResultTest() {}


}
