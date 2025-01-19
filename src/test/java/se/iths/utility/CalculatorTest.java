package se.iths.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.core.Session;
import se.iths.core.SessionHandler;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculatorTest {
    private Calculator calculator;
    private SessionHandler sessionHandler;

    // TODO All calculations needed for the assignment is in this file.
    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
        sessionHandler = new SessionHandler(new FileStorage());

        sessionHandler.createSession("Bloop", 11.2, 4300, LocalDate.of(2025, 1, 1));
        sessionHandler.createSession("Day3", 6, 2430, LocalDate.of(2025, 1, 3));

    }

    @Test
    void calcKmPerHourTest(){
        Session session = sessionHandler.readSession("Bloop");
        double averageSpeed = calculator.calcKmPerHour(session);

        //assertNotNull(averageSpeed); removed for now due to usage of Double
        assertEquals(9.4, averageSpeed, 0.1 ,"Average speed is incorrect.");
    }

    @Test
    void calcKilometersPerMinuteTest(){
        Session session = sessionHandler.readSession("Bloop");
        double kmPerMin = calculator.calcKilometreTime(session);

        assertEquals(6.4, kmPerMin, 0.1, "KM per minute is incorrect.");
    }

    // TODO Fitness score being applied based on given formula.
    @Test
    void calcFitnessScoreTest(){

        // first session tests that the initial session has a CS+T of 0.
        // second session tests that the 2 scores added together is the same sum as the total fitness score that is stored
        // in SessionHandler.
        // Third session adds a new Session and re-tries all previous tests.
        // TODO check if its smoother to put the total fitness score test as its own thing in SessionHandlerTest

        Session firstSession = sessionHandler.readSession("Bloop");
        int firstFitnessScore = firstSession.getFitnessScore();
        assertEquals(13, firstFitnessScore, "Fitness score for the first session is incorrect.");

        Session secondSession = sessionHandler.readSession("Day3");
        int secondFitnessScore = secondSession.getFitnessScore();
        int secondTotalScore = sessionHandler.getTotalFitnessScore();
        int totalFitnessScore = firstFitnessScore + secondFitnessScore;

        assertEquals(6, secondFitnessScore, "Fitness score for the second session is incorrect.");
        assertEquals(19, secondTotalScore, "Total score for the second session is incorrect.");
        assertEquals(secondTotalScore, totalFitnessScore, "Score sum for second session is incorrect");

        sessionHandler.createSession(
                "Day7",
                8,
                3410,
                LocalDate.of(2025, 1, 9));

        Session thirdSession = sessionHandler.readSession("Day7");
        int thirdFitnessScore = thirdSession.getFitnessScore();
        int thirdTotalScore = sessionHandler.getTotalFitnessScore();
        totalFitnessScore = totalFitnessScore + thirdFitnessScore;

        assertEquals(6, thirdFitnessScore, "Fitness score for the third session is incorrect.");
        assertEquals(25, thirdTotalScore, "Total score for the third session is incorrect.");
        assertEquals(totalFitnessScore, thirdTotalScore, "Score sum for third session is incorrect");

    }

    @Test
    void calcFitnessScoreTest_MinimalScore(){
        SessionHandler sessionHandlerToBeNegative = new SessionHandler(new FileStorage());
        sessionHandlerToBeNegative.createSession("Day1", 1.0, 10000, LocalDate.of(1900, 1, 2));
        sessionHandlerToBeNegative.createSession("100 years in the future", 1.0, 4000, LocalDate.of(2000, 1, 2));

        // in theory, due to how the formula is set up, it should not be possible to reach 0 or get to 0
        int ActualScore = sessionHandlerToBeNegative.getTotalFitnessScore();
        int expectedScore = 2;

        assertEquals(expectedScore, ActualScore, "Total score is incorrect.");

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
