package se.iths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculatorTest {
    private Calculator calculator;
    private SessionHandler sessionHandler;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
        sessionHandler = new SessionHandler();

        sessionHandler.createRecord(
                "Bloop",
                11.2,
                4300,
                LocalDate.of(2025, 1, 1));

        sessionHandler.createRecord(
                "Day3",
                6,
                2430,
                LocalDate.of(2025, 1, 3));

    }

    @Test
    void calcAverageSpeedTest(){
        Session session = sessionHandler.readRecord("Bloop");
        double averageSpeed = calculator.calcAverageSpeed(session);

        //assertNotNull(averageSpeed); removed for now due to usage of Double
        assertEquals(9.4, averageSpeed, 0.1 ,"Average speed is incorrect.");
    }

    @Test
    void calcKilometersPerMinuteTest(){
        Session session = sessionHandler.readRecord("Bloop");
        double kmPerMin = calculator.calcKilometresPerMinute(session);

        assertEquals(6.4, kmPerMin, 0.1, "KM per minute is incorrect.");
    }

    @Test
    void calcFitnessScoreTest(){
        /* Created 2 separate variables for session for clarity, one for when the user initially starts to make sure CS
        * is starting at 0 and the calculation is fully correct in this one instance.
        * Second tests is to see that the calculations for the following sessions work as intended. */
        Session session = sessionHandler.readRecord("Bloop");
        double firstSession = calculator.calcFitnessScore(session);

        Session sessionDay3 = sessionHandler.readRecord("Day3");
        double secondSessionDay3 = calculator.calcFitnessScore(sessionDay3);

        //TODO adding asserts after code for average speed and km/h are implemented.
    }

    @Test
    void calcTotalDistanceTraveledTest(){

    }

    @Test
    void calcAverageDistanceTraveledTest(){

    }

}
