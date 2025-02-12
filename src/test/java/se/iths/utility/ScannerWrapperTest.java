package se.iths.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ScannerWrapperTest {
    private ByteArrayOutputStream outputStream;
    private PrintStream originalPrintStream;
    private PrintStream originalErrStream;
    private ScannerWrapper scannerWrapper;

    // Both the before and after each were made with intellij's intellisense.
    // So I take no credit for the initial structure.
    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalPrintStream = System.out;
        originalErrStream = System.err;
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));

        /* Not sure why this does not work exactly, but I will keep this comment here as a reminder of what I attempted.
        It has to be newed up within the tests.
        scannerWrapper = new ScannerWrapper();
        */
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalPrintStream);
        System.setErr(originalErrStream);
        scannerWrapper.closeScanner();
    }

    @Test
    public void textInputText_ValidInput() {
        String input = "This is a test for valid inputs";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        String result = scannerWrapper.textInput(50);
        assertEquals("This is a test for valid inputs", result, "Correct input does not match expected string");
    }

    @Test
    public void textInputText_InvalidInput() {
        String input = "This is a test for invalid inputs. For example, writing this in a name field.\n\nTan\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        String result = scannerWrapper.textInput(15);

        // Had to both StacksOverflow this and Chatgpt to figure out what my issue was. The forsaken issue stemmed from
        // a singular fricking "\n". The other issue was me not creating a stream for "err" prints...
        // Normalize newlines
        String pulledOutput = outputStream.toString().replace("\r\n", "\n");
        assertTrue(pulledOutput.contains(
                "What you entered is too long. Please with a maximum of 15 characters\n"),
                "Expected error due to word limit.");

        assertTrue(pulledOutput.contains("Please enter something: "),
                "Expected prompt after word limit error.");

        assertTrue(pulledOutput.contains("Please try again: "),
                "Expected re-prompt after empty string.");

        assertEquals("Tan", result, "Correct input after failed attempt does not match expected string");
    }

    @Test
    public void numberInputTest_ValidInput() {
        String input = "007\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        double result = scannerWrapper.numberInput();
        assertEquals(7, result,0.1, "Correct input does not match expected int.");
    }

    @Test
    public void numberInputTest_InvalidInput() {
        String input = "Random text whoop \n\n-2\n007\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        double result = scannerWrapper.numberInput();
        assertTrue(outputStream.toString().contains("Invalid input, please enter a number: "), "Error message does not print.");
        assertTrue(outputStream.toString().contains("Invalid input, please enter a number: "), "Error message does not print.");
        assertTrue(outputStream.toString().contains("Please enter a positive number: "),"Error message does not print.");
        assertEquals(7, result, 0.1, "Correct input after failed attempt does not match expected output.");

    }

    @Test
    public void yesNoInputTest_ValidInput() {
        // string contains both a capital and small letter as well as the 2 alternatives for answer(y/n)
        String input = "Y\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        // answering yes returns true.
        boolean result = scannerWrapper.yesOrNoInput();
        assertTrue(result, "Capital letter was not accepted");

        // answering no returns false.
        result = scannerWrapper.yesOrNoInput();
        assertFalse(result, "Small letter was not accepted");
    }

    @Test
    public void yesNoInputTest_InvalidInput() {
        // The test ensure that the invalid message is sent on failed input,
        // but also that we can proceed and answer properly after the mis-input.
        String input = "1234DSA\nY\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        boolean result = scannerWrapper.yesOrNoInput();
        assertTrue(outputStream.toString().contains("Invalid input, please enter either Y or N: "), "Error message does not print.");
        assertTrue(result, "Capital letter was not accepted");
    }

    @Test
    public void dateInputTest_ValidInput() {
        //(YYYY-MM-DD)
        String input = "1999-12-31\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        LocalDate actual = scannerWrapper.dateInput();
        LocalDate expected = LocalDate.of(1999, 12, 31);
        assertEquals(expected, actual, "Actual date does not match expected date.");

    }

    @Test
    public void dateInputTest_BlankInput() {
        //(YYYY-MM-DD)
        String input = " \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scannerWrapper = new ScannerWrapper();

        LocalDate actual = scannerWrapper.dateInput();
        LocalDate expected = LocalDate.now();
        assertEquals(expected, actual, "Blank date should match current date.");

    }
}
