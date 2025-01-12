package se.iths.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        String pulledOutput = outputStream.toString().replace("\r\n", "\n"); // Normalize newlines
        assertTrue(pulledOutput.contains(
                "The name you entered is too long. Please enter a name with a maximum of 15 characters\n"),
                "Error message does not print.");

        assertTrue(pulledOutput.contains("Please enter a name: "),
                "Error message does not print.");

        assertTrue(pulledOutput.contains("Please enter a name:"),
                "Error message does not print.");

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

    // TODO Write tests for the leftover methods. Closing scanner, PromptEnterKey.
}
