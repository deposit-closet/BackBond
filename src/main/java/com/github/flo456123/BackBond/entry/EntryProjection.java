package com.github.flo456123.BackBond.entry;

import java.time.LocalDate;

/**
 * Interface for encapsulating the single {@link Entry}
 * data properties.
 */
public interface EntryProjection {
    LocalDate getDate();
    double getValue();
}
