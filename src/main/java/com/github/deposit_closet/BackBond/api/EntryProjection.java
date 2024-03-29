package com.github.deposit_closet.BackBond.api;

import java.time.LocalDate;

/**
 * Interface for encapsulating the single {@link Entry}
 * data properties.
 */
public interface EntryProjection {
    LocalDate getDate();
    double getValue();
}
