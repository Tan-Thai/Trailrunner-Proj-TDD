package se.iths.core;

import java.time.LocalDate;

public class Session {

    private final String id;
    private final double distance;
    private final float time;
    private final LocalDate date;
    private int fitnessScore;

    public Session(String id, double distance, float time, LocalDate date) {
        this.id = id;
        this.distance = distance;
        this.time = time;
        this.date = date;
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(int fitnessScore) {
        this.fitnessScore = fitnessScore;
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
