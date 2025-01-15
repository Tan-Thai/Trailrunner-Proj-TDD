package se.iths.utility;

import se.iths.core.Session;
import se.iths.core.SessionHandler;

import java.time.LocalDate;

public class Calculator {


    // These two should technically take the actual values as parameters, but I tried to keep it streamlined.
    public double calcAverageSpeed(Session session) {
        // Formula: Avg speed = distance(km) / (Time(sec) / 3600)
        return session.getDistance() / (session.getTime() / 3600);
    }
    public double calcKilometresPerMinute(Session session) {

        // Formula: Km/h = (Time(sec) / 60) / distance(km)
        return (session.getTime() / 60) / session.getDistance();
    }

    public int calcFitnessScore(Session session, SessionHandler sessionHandler) {
        // getting all the req values, for  both clarity and readability.
        int currentFitnessScore = sessionHandler.getTotalFitnessScore();
        double distance = session.getDistance();
        double averageSpeed = calcAverageSpeed(session);
        double kilometresPerMinute = calcKilometresPerMinute(session);
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

        /* Streams shenanigans, not 100% over this and will make a traditional loop
        for (Session session : sessionHandler.getRecordIDs().stream()
                .map(sessionHandler::readRecord)
                .toList()) {
            totalDistanceTraveled += calcAverageSpeed(session);
        }*/

        for (String id : sessionHandler.getSessionIDs()) {
            Session session = sessionHandler.readSession(id);
            totalDistanceTraveled += session.getDistance();
        }

        return totalDistanceTraveled;
    }

    public double calcAverageDistanceTraveled(SessionHandler sessionHandler) {
        double totalDistanceTraveled = calcTotalDistanceTraveled(sessionHandler);
        int sessionCount = getSessionCount(sessionHandler);


        // rounds it, and keeps 2 decimals.
        // contemplated decimalformat, but I rather not introduce new stuff at this point.
        return (double) Math.round((totalDistanceTraveled / sessionCount) * 100) / 100;
    }

    // TODO: Create a yolo stream variant of this method.
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
                int daysDifference = (int) previousDate.until(sessionDate).getDays();
                minDays = Math.min(minDays, daysDifference);
            }
        }

        // Ternary to default to 0 if no other sessions are found, and we keep Integer.MAX_VALUE until the return.
        return minDays == Integer.MAX_VALUE ? 0 : minDays;
    }

    private int getSessionCount(SessionHandler sessionHandler) {
        return sessionHandler.getSessionIDs().size();
    }
}
