package se.iths.core;

public enum InputLimit {
    SESSION_NAME (50),
    USERNAME(20),
    DATE (10),
    DISTANCE (1000),
    TIME (100000);


    public int getLimit() {
        return limit;
    }

    private final int limit;

    InputLimit(int limit) {
        this.limit = limit;
    }


}
