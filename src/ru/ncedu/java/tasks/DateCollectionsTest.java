package ru.ncedu.java.tasks;

import java.util.Calendar;

/**
 * Created by eschy_000 on 05.09.2015.
 */
public class DateCollectionsTest {
    public static void main (String[] args) {
        DateCollections dateCollections = new DateCollectionsImpl();
        dateCollections.initMainMap(10, Calendar.getInstance());
        dateCollections.getSortedSubMap();
    }
}
