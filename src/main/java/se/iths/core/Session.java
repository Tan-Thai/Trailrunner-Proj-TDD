package se.iths.core;

import java.time.LocalDate;

public class Session {

    private final String id;
    private final double distance;
    private final double time;
    private final LocalDate date;
    private int fitnessScore;

    public Session(String id, double distance, double time, LocalDate date) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.date = date;
    }

    public Session(String id, double distance, double time) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.date = LocalDate.now();
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    // adding this just in case someone digs manually into this method.
    public void setFitnessScore(int fitnessScore) {
        this.fitnessScore = fitnessScore;
        if (this.fitnessScore < 0)
            this.fitnessScore = 0;
    }

    public double getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public double getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

}
