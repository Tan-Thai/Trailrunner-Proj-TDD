package se.iths.utility;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScannerSingletonTest {

    @Test
    // to ensure that it's actually a functioning singleton.
    void singletonInstanceTest() {
        ScannerSingleton instance1 = ScannerSingleton.getInstance();
        ScannerSingleton instance2 = ScannerSingleton.getInstance();

        assertSame(instance1, instance2, "Instances are not equal");
    }

    @Test
    // Adding a 'fake' input with ByteArrayInputStream. Mostly StackOverflow scouring here!
    void scannerFunctionalityTest() {
        String input = "This is a test";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ScannerSingleton sc = ScannerSingleton.getInstance();
        // This should send our input that was converted to bytes to result in theory
        String result = sc.getScanner().nextLine();

        assertEquals("This is a test", result, "The scanner returned the wrong result");
    }

    @Test
    // not 100% if this test works as intended, but I'll keep it since it's important to make sure the scanner is closed.
    void closingScannerTest() {
        ScannerSingleton sc = ScannerSingleton.getInstance();
        sc.closeScanner();

        assertThrows(IllegalStateException.class,
                () -> sc.getScanner().nextLine(),
                "Scanner should throw an exception");
    }
}
