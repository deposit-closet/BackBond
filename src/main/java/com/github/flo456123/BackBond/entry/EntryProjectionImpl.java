package com.github.flo456123.BackBond.entry;

import java.time.LocalDateTime;

/**
 * Implementation of {@link EntryProjection} which defines
 * a constructor and two fields for storing value and date.
 */
public class EntryProjectionImpl implements EntryProjection {
    private LocalDateTime date;
    private double value;

    public EntryProjectionImpl(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }

    // implement methods from the EntryProjection interface
    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public double getValue() {
        return value;
    }
}
