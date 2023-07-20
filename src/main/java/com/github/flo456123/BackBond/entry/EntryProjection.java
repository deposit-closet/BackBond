package com.github.flo456123.BackBond.entry;

import java.time.LocalDateTime;

/**
 * Interface for encapsulating the single {@link Entry}
 * data properties.
 */
public interface EntryProjection {
    LocalDateTime getDate();
    double getValue();
}
