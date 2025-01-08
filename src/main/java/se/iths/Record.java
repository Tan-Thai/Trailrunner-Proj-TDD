package se.iths;

import java.time.LocalDate;

public class Record {

    private final int id;
    private final double distance;
    private final int time;
    private final LocalDate date;

    public Record(int id, double distance, int time, LocalDate date) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

}
