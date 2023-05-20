package com.github.flo456123.BackBond.data.service;

import com.github.flo456123.BackBond.data.model.Entry;
import com.github.flo456123.BackBond.data.repository.DailyTreasuryRatesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DailyTreasuryRatesService {

    private final DailyTreasuryRatesRepository entryRepository;

    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }

    public Entry findEntryById(Integer id) {
        Optional<Entry> entryByDate = entryRepository
                .getEntryById(id);

        if (entryByDate.isEmpty()) {
            throw new IllegalStateException("date is not present in database");
        }

        return entryByDate.get();
    }

    public Entry findEntryByDate(LocalDateTime localDateTime) {
        Optional<Entry> entryByDate = entryRepository
                .getEntryByDate(localDateTime);

        if (entryByDate.isEmpty()) {
            throw new IllegalStateException("date is not present in database");
        }

        return entryByDate.get();
    }

    public List<Entry> findEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return entryRepository.getEntriesByDateRange(startDate, endDate);
    }

    public Entry getLatestEntry() {
        Optional<Entry> latestEntry = entryRepository
                .getLatestEntry();

        if (latestEntry.isEmpty()) {
            throw new IllegalStateException("no entries are present in database");
        }

        return latestEntry.get();
    }

    public void addNewEntry(Entry entry) {
        Optional<Entry> entryByDate = entryRepository
                .getEntryByDate(entry.getNewDate());

        if (entryByDate.isPresent()) {
            throw new IllegalStateException("date is already taken");
        }

        entryRepository.save(entry);
    }

    public void addNewEntries(List<Entry> entries) {
        List<LocalDateTime> existingDates = entryRepository.findAllDates();

        List<Entry> newEntries = entries.stream()
                .filter(entry -> !existingDates.contains(entry.getNewDate()))
                .toList();

        entryRepository.saveAll(newEntries);
    }

    public long countEntries() {
        return entryRepository.count();
    }
}
