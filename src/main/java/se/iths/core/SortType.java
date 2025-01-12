package se.iths.core;

import java.util.Comparator;

public enum SortType {
    //The one above is the initial draft of enum sorts, intellisense then gave me the comparator comparing to simplify.
    //BY_DATE_DESC((s1, s2) -> s2.getDate().compareTo(s1.getDate())),
    //Double seems to work with the tests, but I could not find an alternative to Float.

    BY_DATE_DESC(Comparator.comparing(Session::getDate).reversed()),
    BY_DISTANCE_DESC(Comparator.comparingDouble(Session::getDistance).reversed()),
    BY_TIME_DESC(Comparator.comparingDouble(Session::getTime).reversed()),

    BY_DATE_ASC(Comparator.comparing(Session::getDate)),
    BY_DISTANCE_ASC(Comparator.comparingDouble(Session::getDistance)),
    BY_TIME_ASC(Comparator.comparingDouble(Session::getTime));


    private final Comparator<Session> comparator;

    SortType(Comparator<Session> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Session> getComparator() {
        return comparator;
    }
}
