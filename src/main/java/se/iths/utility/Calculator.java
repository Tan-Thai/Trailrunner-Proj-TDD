package se.iths.utility;

import se.iths.core.Session;
import se.iths.core.SessionHandler;

import java.time.LocalDate;

public class Calculator {


    // These two should technically take the actual values as parameters, but I tried to keep it streamlined.
    public double calcKmPerHour(Session session) {
        // Formula: Avg speed = distance(km) / (Time(sec) / 3600)
        return session.getDistance() / (session.getTime() / 3600);
    }
    public double calcKilometreTime(Session session) {
        // Formula: KilometerTime = (Time(sec) / 60) / distance(km)
        return (session.getTime() / 60) / session.getDistance();
    }

    // TODO method that ensures that fitness score starts at 0 for first run and cannot go below 0
    public int calcFitnessScore(Session session, SessionHandler sessionHandler) {
        // getting all the req values, for  both clarity and readability.
        int currentFitnessScore = sessionHandler.getTotalFitnessScore();
        double distance = session.getDistance();
        double averageSpeed = calcKmPerHour(session);
        double kilometresPerMinute = calcKilometreTime(session);
        int daysSinceLastRun = calcDaysSinceLastRun(session, sessionHandler);

        double finalFitnessScore =
                currentFitnessScore +
                (distance + (averageSpeed / kilometresPerMinute)) -
                (daysSinceLastRun / 2.0);

        // math.max to ensure that it will default to 0 if we ever go into the negatives.
        return Math.max(0, (int) Math.round(finalFitnessScore));
    }

    public double calcTotalDistanceTraveled(SessionHandler sessionHandler) {
        double totalDistanceTraveled = 0;

        for (String id : sessionHandler.getSessionIDs()) {
            Session session = sessionHandler.readSession(id);
            totalDistanceTraveled += session.getDistance();
        }

        return totalDistanceTraveled;
    }

    public double calcAverageDistanceTraveled(SessionHandler sessionHandler) {
        double totalDistanceTraveled = calcTotalDistanceTraveled(sessionHandler);
        int sessionCount = sessionHandler.getSessionIDs().size();
        // rounds it, and keeps 2 decimals.
        // contemplated decimalformat, but I rather not introduce new stuff at this point.
        return (double) Math.round((totalDistanceTraveled / sessionCount) * 100) / 100;
    }

    // TODO: Create a yolo stream variant of this method. // decided not to do it.
    private int calcDaysSinceLastRun(Session session, SessionHandler sessionHandler) {
        // Method is currently very fragile when it comes to larger scale collections due to sequential execution.
        // Could prolly do some .stream shenanigans here to improve it, but I'll leave that for later!

        LocalDate sessionDate = session.getDate();
        int minDays = Integer.MAX_VALUE;
        for (String id : sessionHandler.getSessionIDs()) {
            Session previousSession = sessionHandler.readSession(id);
            LocalDate previousDate = previousSession.getDate();

            // Goes into if-statement if prev-date is *before* the new session date.
            // Then counts from the previous date all the way to the date of the new session.
            // Compares them between the current "minimum" date and picks the lower.
            if (previousDate.isBefore(sessionDate)) {
                int daysDifference = previousDate.until(sessionDate).getDays();
                minDays = Math.min(minDays, daysDifference);
            }
        }

        // Ternary to default to 0 if no other sessions are found, and we start with Integer.MAX_VALUE.
        return minDays == Integer.MAX_VALUE ? 0 : minDays;
    }

    public double calcSecToMin(double timeSec) {
        double secondsToMinutes = timeSec / 60;
        return (double) Math.round(secondsToMinutes * 10) / 10;
    }

    public double calcMinToSec(double timeSec) {
        return timeSec * 60;
    }
}
