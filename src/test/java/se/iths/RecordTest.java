package se.iths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecordTest {
    private Record record;

    @BeforeEach
    void setUp() {
        record = new Record("1", 2.3, 1350, LocalDate.of(2025, 1, 2));
    }

    @Test
    void RecordConstructorTest() {
        assertNotNull(record);
    }

    @Test
    void getIDTest() {
        assertEquals("1", record.getId());
    }

    @Test
    void getDistanceTest() {
        assertEquals(2.3, record.getDistance());
    }

    @Test
    void getTimeTest() {
        assertEquals(1350, record.getTime());
    }

    @Test
    void getDateTest() {
        assertNotNull(record.getDate());
        assertEquals(LocalDate.of(2025, 1,2), record.getDate());
    }
}
