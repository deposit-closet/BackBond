package com.github.flo456123.BackBond.api;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        List<LocalDate> existingDates = entryRepository.findAllDates();

        List<Entry> newEntries = entries.stream()
                .filter(entry -> !existingDates.contains(entry.getNewDate()))
                .toList();

        entryRepository.saveAll(newEntries);
    }

    public List<EntryProjectionImpl> getColumn(String col) {
        if (col.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be empty");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EntryProjectionImpl> query = cb.createQuery(EntryProjectionImpl.class);
        Root<Entry> root = query.from(Entry.class);

        query.select(cb.construct(EntryProjectionImpl.class, root.get("newDate"), root.get(col)));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Entry> getForDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start date cannot be after end date");
        }

        return entryRepository.findEntriesByDateRange(startDate, endDate);
    }

    public List<EntryProjectionImpl> getColumnForDateRange(String col, LocalDate startDate, LocalDate endDate) {
        if (col.isEmpty()) {
            throw new IllegalArgumentException("column name cannot be empty");
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EntryProjectionImpl> query = cb.createQuery(EntryProjectionImpl.class);
        Root<Entry> root = query.from(Entry.class);

        query.select(cb.construct(EntryProjectionImpl.class, root.get("newDate"), root.get(col)))
                .where(cb.between(root.get("newDate"), startDate, endDate)); // date range filtering

        return entityManager.createQuery(query).getResultList();
    }
}
