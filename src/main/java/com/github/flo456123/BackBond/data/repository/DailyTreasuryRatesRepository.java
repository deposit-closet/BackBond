package com.github.flo456123.BackBond.data.repository;

import com.github.flo456123.BackBond.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyTreasuryRatesRepository
        extends JpaRepository<Entry, Integer> {

    @Query("SELECT e FROM Entry e WHERE e.newDate BETWEEN ?1 AND ?2")
    List<Entry> getEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT newDate FROM Entry")
    List<LocalDateTime> findAllDates();
}
