package com.github.flo456123.BackBond.entry;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

@Service
@AllArgsConstructor
public class PYCRService {

    private final PYCRRepository entryRepository;
    private final EntityManager entityManager;

    public List<Entry> getEntries() {
        return entryRepository.findAll();
    }

    public long countEntries() {
        return entryRepository.count();
    }

    public void addNewEntries(Queue<Entry> entries) {
        List<LocalDateTime> existingDates = entryRepository.findAllDates();

        List<Entry> newEntries = entries.stream()
                .filter(entry -> !existingDates.contains(entry.getNewDate()))
                .toList();

        entryRepository.saveAll(newEntries);
    }

    public List<Double> getColumn(String col) {
        if (col.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be empty");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<Entry> root = query.from(Entry.class);

        query.select(root.get(col));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Entry> getForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date");
        }

        return entryRepository.findEntriesByDateRange(startDate, endDate);
    }

    public List<Double> getColumnForDateRange(String col, LocalDateTime startDate, LocalDateTime endDate) {
        if (col.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be empty");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> query = cb.createQuery(Double.class);
        Root<Entry> root = query.from(Entry.class);

        query.select(root.get(col))
                .where(cb.between(root.get("newDate"), startDate, endDate)); // date range filtering

        return entityManager.createQuery(query).getResultList();
    }
}
