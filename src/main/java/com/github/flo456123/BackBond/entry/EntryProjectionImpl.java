package com.github.flo456123.BackBond.entry;

import java.time.LocalDate;

/**
 * Implementation of {@link EntryProjection} which defines
 * a constructor and two fields for storing value and date.
 */
public class EntryProjectionImpl implements EntryProjection {
    private final LocalDate date;
    private final double value;

    public EntryProjectionImpl(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    // implement methods from the EntryProjection interface
    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public double getValue() {
        return value;
    }
}
