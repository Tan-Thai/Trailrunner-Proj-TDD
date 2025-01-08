package se.iths;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordHandler {
    private final Map<String, Record> recordCollection = new HashMap<>();

    public void createRecord(String id, double distance, int time_seconds, LocalDate date) {
        if (recordCollection.containsKey(id)) {
            throw new IllegalArgumentException("A recorded session with this ID already exists.");
        }

        Record newRecord = new Record(id, distance, time_seconds, date);
        recordCollection.put(id, newRecord);
    }

    // I would keep IOException if we worked with a database. But since we don't im converting it to "Illegal-arg"
    public Record readRecord(String id){
        Record record = recordCollection.get(id);
        if (record == null) {
            throw new IllegalArgumentException("No recorded session with this ID exists.");
        }

        return record;
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