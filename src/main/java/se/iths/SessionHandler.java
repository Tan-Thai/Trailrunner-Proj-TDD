package se.iths;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHandler {
    private Calculator calculator = new Calculator();
    private final Map<String, Session> recordCollection = new HashMap<>();
    private int totalFitnessScore = 0;

    public int getTotalFitnessScore() {
        return totalFitnessScore;
    }

    public void setTotalFitnessScore(int totalFitnessScore) {
        this.totalFitnessScore = totalFitnessScore;
    }

    public void createRecord(String id, double distance, int time_seconds, LocalDate date) {
        if (recordCollection.containsKey(id)) {
            throw new IllegalArgumentException("A recorded session with this ID already exists.");
        }

        Session newSession = new Session(id, distance, time_seconds, date);

        int newFitnessScore = calculator.calcFitnessScore(newSession, this);
        newSession.setFitnessScore(newFitnessScore - totalFitnessScore);
        setTotalFitnessScore(newFitnessScore);

        recordCollection.put(id, newSession);
    }

    // I would keep IOException if we worked with a database. But since we don't im converting it to "Illegal-arg"
    public Session readRecord(String id){
        Session session = recordCollection.get(id);
        if (session == null) {
            throw new IllegalArgumentException("No recorded session with this ID exists.");
        }

        return session;
    }

    public List<String> getRecordIDs() {
        return new ArrayList<>(recordCollection.keySet());
    }
    
    public void deleteRecord(String id) {
        if (!recordCollection.containsKey(id)) {
            throw new IllegalArgumentException("No recorded session with this ID exists.");
        }

        recordCollection.remove(id);
    }
}