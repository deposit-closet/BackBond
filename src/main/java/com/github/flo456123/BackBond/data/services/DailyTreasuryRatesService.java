package com.github.flo456123.BackBond.data.services;

import com.github.flo456123.BackBond.data.model.Entry;
import com.github.flo456123.BackBond.data.repository.DailyTreasuryRatesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DailyTreasuryRatesService {

    private final DailyTreasuryRatesRepository entryRepository;

    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }

    public List<Entry> findEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return entryRepository.getEntriesByDateRange(startDate, endDate);
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
