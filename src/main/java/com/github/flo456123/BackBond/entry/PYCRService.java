package com.github.flo456123.BackBond.entry;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PYCRService {

    private final PYCRRepository entryRepository;

    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }

    public List<Entry> findEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("start date and end date cannot be null");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date");
        }

        return entryRepository.getEntriesByDateRange(startDate, endDate);
    }

    public List<Entry> findEntriesByColumn(String colName) {
        if (colName == null || colName.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be null or empty");
        }

        return entryRepository.findEntriesByColumn(colName);
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
