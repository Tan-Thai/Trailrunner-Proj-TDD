package se.iths;

import java.time.LocalDate;

public class Record {

    private final String id;
    private final double distance;
    private final float time;
    private final LocalDate date;

    public Record(String id, double distance, float time, LocalDate date) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public float getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

}
