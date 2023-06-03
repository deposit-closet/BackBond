package com.github.flo456123.BackBond.entry;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * A controller to handle incoming HTTP requests under the `/BackBond/api/v1` request mapping.
 */
@RestController
@RequestMapping(path = "/BackBond/api/v1")
@RequiredArgsConstructor
public class PYCRController {

    private final PYCRService entryService;

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
     * Returns the number of entries in the database.
     *
     * @return the number of entries in the database. (200 OK)
     */
    @GetMapping("/count")
    public long getCount() {
        return entryService.countEntries();
    }

    /**
     * Returns a list of entries depending on the specified variables.
     *
     * @param startDate starting date to capture.
     * @param endDate   ending date to capture.
     * @return a list of entries. (200 OK)
     */
    @GetMapping("/entries/{col}")
    public ResponseEntity<List<Entry>> getEntriesByDateRange(
            @PathVariable(required = false) Optional<String> col,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Optional<LocalDateTime> startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Optional<LocalDateTime> endDate
    ) {
        List<Entry> entries;

        if (col.isPresent()) {
            if (startDate.isPresent() && endDate.isPresent()) {
                // return column for date range
                entries = entryService.getColumnForDateRange(col.get(), startDate.get(), endDate.get());
            } else {
                // return data for specified column
                entries = entryService.getColumn(col.get());
            }
        } else if (startDate.isPresent() && endDate.isPresent()) {
            // return data for specified date range
            entries = entryService.getForDateRange(startDate.get(), endDate.get());
        } else {
            // return all entries normally
            entries = entryService.getEntries();
        }

        return ResponseEntity.ok(entries);
    }
}
