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

        // first session tests that the initial session has a CS+T of 0.
        // second session tests that the 2 scores added together is the same sum as the total fitness score that is stored
        // in SessionHandler.
        // Third session adds a new Session and re-tries all previous tests.

        Session firstSession = sessionHandler.readRecord("Bloop");
        int firstFitnessScore = firstSession.getFitnessScore();
        assertEquals(13, firstFitnessScore, "Fitness score for the first session is incorrect.");

        Session secondSession = sessionHandler.readRecord("Day3");
        int secondFitnessScore = secondSession.getFitnessScore();
        int secondTotalScore = sessionHandler.getTotalFitnessScore();
        int totalFitnessScore = firstFitnessScore + secondFitnessScore;
        assertEquals(6, secondFitnessScore, "Fitness score for the second session is incorrect.");
        assertEquals(19, secondTotalScore, "Total score for the second session is incorrect.");
        assertEquals(secondTotalScore, totalFitnessScore, "Score sum for second session is incorrect");

        sessionHandler.createRecord(
                "Day7",
                8,
                3410,
                LocalDate.of(2025, 1, 9));

        Session thirdSession = sessionHandler.readRecord("Day7");
        int thirdFitnessScore = thirdSession.getFitnessScore();
        int thirdTotalScore = sessionHandler.getTotalFitnessScore();
        totalFitnessScore = totalFitnessScore + thirdFitnessScore;
        assertEquals(6, thirdFitnessScore, "Fitness score for the third session is incorrect.");
        assertEquals(25, thirdTotalScore, "Total score for the third session is incorrect.");
        assertEquals(totalFitnessScore, thirdTotalScore, "Score sum for third session is incorrect");

    }

    @Test
    void calcTotalDistanceTraveledTest(){
        double totalDistanceTraveled = calculator.calcTotalDistanceTraveled(sessionHandler);

        assertEquals(17.2, totalDistanceTraveled, 0.1, "Total distance traveled is incorrect.");
    }

    @Test
    void calcAverageDistanceTraveledTest(){
        double averageDistanceTraveled = calculator.calcAverageDistanceTraveled(sessionHandler);

        assertEquals(8.6, averageDistanceTraveled, 0.1, "Average distance traveled is incorrect.");
    }

}
