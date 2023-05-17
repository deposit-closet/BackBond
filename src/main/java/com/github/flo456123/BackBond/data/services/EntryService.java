package com.github.flo456123.BackBond.data.services;

import com.github.flo456123.BackBond.data.models.Entry;
import com.github.flo456123.BackBond.data.repository.EntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;

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

    public long countEntries() {
        return entryRepository.count();
    }
}
