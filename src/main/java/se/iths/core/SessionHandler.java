package se.iths.core;

import se.iths.utility.Calculator;
import se.iths.utility.FileStorage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SessionHandler {
    private final FileStorage fileStorage;
    private Calculator calculator = new Calculator();
    private final Map<String, Session> sessionCollection = new HashMap<>();
    private int totalFitnessScore = 0;

    public SessionHandler(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void createSession(String id, double distance, int time_seconds, LocalDate date) {
        String normalisedId = id.toLowerCase();

        if (sessionCollection.containsKey(normalisedId)) {
            throw new IllegalArgumentException("A recorded session with this ID already exists.");
        }

        Session newSession = new Session(id, distance, time_seconds, date);

        int newFitnessScore = calculator.calcFitnessScore(newSession, this);
        newSession.setFitnessScore(newFitnessScore - totalFitnessScore);
        setTotalFitnessScore(newFitnessScore);

        // Saves this to the local memory
        sessionCollection.put(normalisedId, newSession);

        // Tries to save it to an external file. Throws IOException if failed.
            try {
                fileStorage.saveRecord(id , distance, time_seconds, date);
            } catch (IOException e) {
                System.out.println("Error saving session to external file.\n" + e.getMessage());
                e.printStackTrace();
            }

    }

    //region Getters-Setters
    public int getTotalFitnessScore() {
        return totalFitnessScore;
    }

    public List<String> getSessionIDs() {
        List<String> sessionIDs = new ArrayList<>();

        fileStorage.getRecordIDs();

        for (Session session : sessionCollection.values()) {
            sessionIDs.add(session.getId());
        }

        return sessionIDs;
    }

    public List<String> getSortedSessions(SortType sortType) {

        fileStorage.getRecordIDs();

        return sessionCollection
                .values()
                .stream()
                .sorted(sortType.getComparator())
                .map(Session::getId)
                .collect(Collectors.toList());
    }

    public void setTotalFitnessScore(int totalFitnessScore) {
        this.totalFitnessScore = totalFitnessScore;
    }
    //endregion


    public Session readSession(String id){
        Session session = sessionCollection.get(id.toLowerCase()); // adding to lower so I don't forget.

        try {
            // not normalising the ID here since it would happen within the load-Record method.
            // similar to how this method does it in row 82.
            fileStorage.loadRecord(id);
        } catch (IOException e) {
            System.out.println("Error loading session from external file.\n" + e.getMessage());
            e.printStackTrace();
        }

        if (session == null)
            throw new IllegalArgumentException("No recorded session with this ID exists.");

        return session;
    }

    public void deleteSession(String id) {
        if (!sessionCollection.containsKey(id.toLowerCase())) {
            throw new IllegalArgumentException("No recorded session with this ID exists.");
        }

        try {
            // not normalising the ID here since it would happen within the load-Record method.
            // similar to how this method converts it after taking in the argument.
            fileStorage.deleteRecord(id);
        } catch (IOException e) {
            System.out.println("Error removing session from external file.\n" + e.getMessage());
            e.printStackTrace();
        }

        sessionCollection.remove(id.toLowerCase());
    }

    public SessionHandler searchSessionsByID(String query) {
        if (query == null || query.isEmpty()) {
            return new SessionHandler(fileStorage);
        }

        // could implement this code to actually work its returns into the found session.
        // I'm holding off on it due to the risk of bricking more code for now.
        fileStorage.getRecordIDs();

        List<String> foundSessions = sessionCollection.keySet().stream()
                .filter(id -> id.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        return createSubset(foundSessions);
    }

    // created this to unify the output for searched/non searched sessions so that they can be re-sorted multiple times
    // without dropping its context. The issue was in me passing the list with no data to compare.
    private SessionHandler createSubset(List<String> sessionIDs) {
        SessionHandler curatedCollection = new SessionHandler(fileStorage);
        for (String id : sessionIDs) {
            if (sessionCollection.containsKey(id.toLowerCase())) { //to lower to search for case-insensitive id's
                Session session = sessionCollection.get(id);
                curatedCollection.sessionCollection.put(id, session);
            }
        }
        return curatedCollection;
    }
}