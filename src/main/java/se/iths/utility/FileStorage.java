package se.iths.utility;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FileStorage {

    public void SaveRecord(String id, double distance, int time_seconds, LocalDate date)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    public Record LoadRecord(String id) throws IOException {
        throw new UnsupportedOperationException();
    }

    public List<String> getRecordIDs() {
        throw new UnsupportedOperationException();
    }

    public void deleteRecord(String id) throws IOException {
        throw new UnsupportedOperationException();
    }
}