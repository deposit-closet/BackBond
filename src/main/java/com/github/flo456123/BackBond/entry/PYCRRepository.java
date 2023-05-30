package com.github.flo456123.BackBond.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PYCRRepository
        extends JpaRepository<Entry, Integer> {

    @Query("SELECT e FROM Entry e WHERE e.newDate BETWEEN ?1 AND ?2")
    List<Entry> getEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT newDate FROM Entry")
    List<LocalDateTime> findAllDates();

    @Query("SELECT ?1 FROM Entry")
    List<Entry> findEntriesByColumn(String columnName);
}
