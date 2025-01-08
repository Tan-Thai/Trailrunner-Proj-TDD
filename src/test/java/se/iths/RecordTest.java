package se.iths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecordTest {
    private Record record;

    @BeforeEach
    void setUp() {
        record = new Record(1, 2.3, 1350, 20);
    }

    @Test
    void RecordTest() {
        assertNotNull(record);
    }

    @Test
    void getIDTest() {
        assertEquals(record.getID, 1);
    }

    @Test
    void getDistanceTest() {
        assertEquals(record.getDistance, 2.3);
    }

    @Test
    void getTimeTest() {
        assertEquals(record.getTime, 1350);
    }

    @Test
    void getDateTest() {
        assertEquals(record.getDate, 20);
    }
}
