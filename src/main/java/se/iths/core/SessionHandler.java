package se.iths.core;

import se.iths.utility.Calculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SessionHandler {
    private Calculator calculator = new Calculator();
    private final Map<String, Session> sessionCollection = new HashMap<>();
    private int totalFitnessScore = 0;

    public int getTotalFitnessScore() {
        return totalFitnessScore;
    }

    public void setTotalFitnessScore(int totalFitnessScore) {
        this.totalFitnessScore = totalFitnessScore;
    }

    public void createSession(String id, double distance, int time_seconds, LocalDate date) {
        String normalizedId = id.toLowerCase();

        if (sessionCollection.containsKey(normalizedId)) {
            throw new IllegalArgumentException("A recorded session with this ID already exists.");
        }

        Session newSession = new Session(id, distance, time_seconds, date);

        int newFitnessScore = calculator.calcFitnessScore(newSession, this);
        newSession.setFitnessScore(newFitnessScore - totalFitnessScore);
        setTotalFitnessScore(newFitnessScore);

        sessionCollection.put(normalizedId, newSession);
    }

    // I would keep IOException if we worked with a database. But since we don't im converting it to "Illegal-arg"
    public Session readSession(String id){
        Session session = sessionCollection.get(id.toLowerCase()); // adding to lower so I don't forget.
        if (session == null) {
            throw new IllegalArgumentException("No recorded session with this ID exists.");
        }

        return session;
    }

    public List<String> getSessionIDs() {
        List<String> sessionIDs = new ArrayList<>();

        for (Session session : sessionCollection.values()) {
            sessionIDs.add(session.getId());
        }

        return sessionIDs;
    }
    
    public void deleteSession(String id) {
        if (!sessionCollection.containsKey(id.toLowerCase())) {
            throw new IllegalArgumentException("No recorded session with this ID exists.");
        }

        sessionCollection.remove(id.toLowerCase());
    }


    public SessionHandler searchSessionByID(String query) {
        if (query == null || query.isEmpty()) {
            return new SessionHandler();
        }

        List<String> foundSessions = sessionCollection.keySet().stream()
                .filter(id -> id.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        return createSubset(foundSessions);
    }

    public List<String> getSortedSessions(SortType sortType) {

        return sessionCollection
                .values()
                .stream()
                .sorted(sortType.getComparator())
                .map(Session::getId)
                .collect(Collectors.toList());
    }

    // created this to unify the output for searched/non searched sessions so that they can be re-sorted multiple times
    // without dropping its context. The issue was in me passing the list with no data to compare.
    private SessionHandler createSubset(List<String> sessionIDs) {
        SessionHandler curatedCollection = new SessionHandler();
        for (String id : sessionIDs) {
            if (sessionCollection.containsKey(id.toLowerCase())) { //to lower to search for case-insensitive id's
                Session session = sessionCollection.get(id);
                curatedCollection.sessionCollection.put(id, session);
            }
        }
        return curatedCollection;
    }
}