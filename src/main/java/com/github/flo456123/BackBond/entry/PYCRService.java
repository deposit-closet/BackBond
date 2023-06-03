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

    public long countEntries() {
        return entryRepository.count();
    }

    public void addNewEntries(List<Entry> entries) {
        List<LocalDateTime> existingDates = entryRepository.findAllDates();

        List<Entry> newEntries = entries.stream()
                .filter(entry -> !existingDates.contains(entry.getNewDate()))
                .toList();

        entryRepository.saveAll(newEntries);
    }

    public List<Entry> getColumn(String col) {
        if (col.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be null or empty");
        }

        return entryRepository.findEntriesByColumn(col);
    }

    public List<Entry> getForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date");
        }

        return entryRepository.findEntriesByDateRange(startDate, endDate);
    }

    public List<Entry> getColumnForDateRange(String col, LocalDateTime startDate, LocalDateTime endDate) {
        if (col.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be null or empty");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date");
        }

        return entryRepository.findColumnByDateRange(col, startDate, endDate);
    }
}
