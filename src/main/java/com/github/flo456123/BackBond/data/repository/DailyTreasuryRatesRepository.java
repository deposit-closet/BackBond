package com.github.flo456123.BackBond.data.repository;

import com.github.flo456123.BackBond.data.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyTreasuryRatesRepository
        extends JpaRepository<Entry, Integer> {

    @Query("SELECT e FROM Entry e WHERE e.id = ?1")
    Optional<Entry> getEntryById(Integer aInteger);

    @Query("SELECT e FROM Entry e WHERE e.newDate = ?1")
    Optional<Entry> getEntryByDate(LocalDateTime aLocalDateTime);

    @Query("SELECT e FROM Entry e WHERE e.newDate BETWEEN ?1 AND ?2")
    List<Entry> getEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM Entry e ORDER BY e.newDate DESC")
    Optional<Entry> getLatestEntry();
}
