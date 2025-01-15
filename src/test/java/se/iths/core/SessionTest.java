package se.iths.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SessionTest {
    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session("1", 2.3, 1350, LocalDate.of(2025, 1, 2));
    }

    @Test
    void RecordConstructorTest() {
        assertNotNull(session);
    }

    @Test
    void getIDTest() {
        assertEquals("1", session.getId());
    }

    @Test
    void getDistanceTest() {
        assertEquals(2.3, session.getDistance());
    }

    @Test
    void getTimeTest() {
        assertEquals(1350, session.getTime());
    }

    @Test
    void getDateTest() {
        assertNotNull(session.getDate());
        assertEquals(LocalDate.of(2025, 1,2), session.getDate());
    }

    @Test
    void constructorWithoutDateTest() {
        session = new Session("1", 2.3, 1350);

        LocalDate expected = LocalDate.now();
        LocalDate actual = session.getDate();
        assertNotNull(actual, "Actual date should not be null");
        assertEquals(expected, actual, "Actual date should be " + expected);
    }
}
