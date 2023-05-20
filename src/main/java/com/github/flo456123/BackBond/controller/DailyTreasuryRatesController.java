package com.github.flo456123.BackBond.controller;

import com.github.flo456123.BackBond.data.model.Entry;
import com.github.flo456123.BackBond.data.services.DailyTreasuryRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A controller to handle incoming HTTP requests under the `/BackBond/api/v1` request mapping.
 */
@RestController
@RequestMapping(path = "/BackBond/api/v1")
@RequiredArgsConstructor
public class DailyTreasuryRatesController {

    private final DailyTreasuryRatesService entryService;

    /**
     * Returns the status of the API.
     *
     * @return the status of the API. (200 OK)
     */
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("OK");
    }

    /**
     * Returns a JSON list of all the entries in the database to the client.
     *
     * @return the entries stored in the database. (200 OK)
     */
    @GetMapping
    public ResponseEntity<List<Entry>> getEntries() {
        return ResponseEntity.ok(entryService.getEntries());
    }

    /**
     * Retrieves an entry from the database based on the provided entry ID.
     *
     * @param entryId the ID of the entry to retrieve.
     * @return the entry with the specified ID. (200 OK)
     */
    @GetMapping(path = "{entryId}")
    public Entry getEntryById(
            @PathVariable Integer entryId
    ) {
        return entryService.findEntryById(entryId);
    }

    /**
     * Returns the latest entry to the client.
     *
     * @return the latest entry stored in the database. (200 OK)
     */
    @GetMapping("/latest")
    public Entry getLatestEntry() {
        return entryService.getLatestEntry();
    }

    /**
     * Returns the number of entries in the database.
     *
     * @return the number of entries in the database. (200 OK)
     */
    @GetMapping("/count")
    public long getCount() {
        return entryService.countEntries();
    }


    /**
     * Returns a list of entries in a given date range.
     * <p>
     * // TODO: Work in progress
     *
     * @param startDate starting date to capture.
     * @param endDate   ending date to capture.
     * @return a list of entries in the range [startDate, endDate). (200 OK)
     */
    @GetMapping(path = "/date-range")
    public ResponseEntity<List<Entry>> getEntriesByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")LocalDateTime endDate
            ) {
        List<Entry> entries = entryService.findEntriesByDateRange(startDate, endDate);
        return ResponseEntity.ok(entries);
    }
}
