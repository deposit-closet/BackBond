package com.github.flo456123.BackBond.data.repository;

import com.github.flo456123.BackBond.data.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Integer> {
}
