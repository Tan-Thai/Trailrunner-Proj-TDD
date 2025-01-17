package se.iths.utility;

import se.iths.core.Session;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStorage {
    private final Map<String, Session> sessions = new HashMap<>();

    public void saveRecord(String id, double distance, int time_seconds, LocalDate date) throws IOException {
    }

    public Session loadRecord(String id) throws IOException {
        return null;
    }

    public List<String> getRecordIDs() {
        throw new UnsupportedOperationException();
    }

    public void deleteRecord(String id) throws IOException {
        throw new UnsupportedOperationException();
    }
}