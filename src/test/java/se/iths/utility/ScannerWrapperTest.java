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
        scannerWrapper = new ScannerWrapper();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalPrintStream);
        System.setErr(originalErrStream);
    }

    @Test
    public void textInputText_ValidInput() {
        String input = "This is a test for valid inputs";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = scannerWrapper.textInput(50);
        assertEquals("This is a test for valid inputs", result);
    }

    @Test
    public void textInputText_InvalidInput() {
        String input = "This is a test for invalid inputs. For example, writing this in a name field.\nTan\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = scannerWrapper.textInput(15);

        // Had to both StacksOverflow this and Chatgpt to figure out what my issue was. The forsaken issue stemmed from
        // a singular fricking "\n". The other issue was me not creating a stream for "err" prints...
        String capturedOutput = outputStream.toString().replace("\r\n", "\n"); // Normalize newlines
        assertTrue(capturedOutput.contains(
                "The name you entered is too long. Please enter a name with a maximum of 15 characters\n"));
        assertTrue(capturedOutput.contains("Please enter a name:"));

        assertEquals("Tan", result);
    }

    @Test
    public void numberInputTest_ValidInput() {
        String input = "007\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        int result = scannerWrapper.numberInput();
        assertEquals(7, result);
    }

    @Test
    public void numberInputTest_InvalidInput() {
        String input = "Random text whoop \n007\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        int result = scannerWrapper.numberInput();
        assertTrue(outputStream.toString().contains("Invalid input"));
        assertEquals(7, result);

    }

    @Test
    public void yesNoInputTest_ValidInput() {
        String inputCapitalLetter = "Y\n";
        String inputSmallLetter = "n\n";
        System.setIn(new ByteArrayInputStream(inputCapitalLetter.getBytes()));

        // answering yes returns true.
        boolean result = scannerWrapper.yesOrNoInput();
        assertTrue(result, "Capital letter was not accepted");

        // answering no returns false.
        System.setIn(new ByteArrayInputStream(inputSmallLetter.getBytes()));
        result = scannerWrapper.yesOrNoInput();
        assertFalse(result, "Small letter was not accepted");
    }

    @Test
    public void yesNoInputTest_InvalidInput() {
        // The test ensure that the invalid message is sent on failed input,
        // but also that we can proceed and answer properly after the mis-input.
        String input = "1234DSA\nY\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = scannerWrapper.yesOrNoInput();
        assertTrue(outputStream.toString().contains("Invalid input"));
        assertTrue(result, "Capital letter was not accepted");
    }

    @Test
    void closingScannerTest() {

    }
}
