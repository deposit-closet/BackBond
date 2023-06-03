package com.github.flo456123.BackBond.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PYCRRepository
        extends JpaRepository<Entry, Integer> {

    @Query("SELECT ?1 FROM Entry")
    List<Entry> findEntriesByColumn(String col);

    @Query("SELECT e FROM Entry e WHERE e.newDate BETWEEN ?1 AND ?2")
    List<Entry> findEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT ?1 FROM Entry e WHERE e.newDate BETWEEN ?2 AND ?3")
    List<Entry> findColumnByDateRange(String col, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Only used by update service to check if entries are
     * new or are previously existing.
     *
     * @return a list of all the dates
     */
    @Query("SELECT newDate FROM Entry")
    List<LocalDateTime> findAllDates();
}
