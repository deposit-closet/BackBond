package com.github.flo456123.BackBond.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * A {@link RestController} to handle incoming HTTP requests under the ``/BackBond/api/v1`` request mapping.
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
    public ResponseEntity<Long> getCount() {
        long countEntries = entryService.countEntries();
        return ResponseEntity.ok(countEntries);
    }

    /**
     * Endpoint which supports various features, such as querying a response which consists of all the entire stored in
     * the database.
     * In addition to normally querying every entry in the database, this endpoint also supports querying entries from a
     * given date range to reduce the scalability of each query to the database.
     *
     * @param startDate OPTIONAL: the starting date to query from
     * @param endDate   OPTIONAL: the ending date to query from
     * @return a list of all the selected entries
     */
    @GetMapping("/entries")
    public ResponseEntity<List<Entry>> getEntries(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<Entry> entries = handleDateRangeQuery(startDate, endDate,
                entryService::getEntries,
                entryService::getForDateRange
        );

        return ResponseEntity.ok(entries);
    }

    /**
     * Endpoint which core purpose is to return different columns of the database.
     * Much similar to the previous endpoint, this endpoint also supports different features such as querying a column
     * given a specified date range.
     *
     * @param col       the column name to query
     * @param startDate the starting date to query from
     * @param endDate   the ending date to query from
     * @return a list containing the interest rate data for a column
     */
    @GetMapping("/entries/{col}")
    public ResponseEntity<List<EntryProjection>> getColumn(
            @PathVariable() String col,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<EntryProjection> entries = handleDateRangeQuery(startDate, endDate,
                () -> entryService.getColumn(col),
                (sDate, eDate) -> entryService.getColumnForDateRange(col, sDate, eDate)
        );

        return ResponseEntity.ok(entries);
    }

    /**
     * This helper function is used to avoid breaking the "don't repeat yourself" (DRY)
     * coding principle by having the client supplying an operation and solely applying
     * them depending on if the start and end dates are present.
     *
     * @param noDateRangeSupplier the function to call if there is no date present
     * @param dateRangeSupplier the function to call if there is a date range present
     * @return the resulting query
     */
    private <T> List<T> handleDateRangeQuery(LocalDate startDate, LocalDate endDate,
                                             Supplier<List<T>> noDateRangeSupplier,
                                             BiFunction<LocalDate, LocalDate, List<T>> dateRangeSupplier) {
        if (startDate != null && endDate != null) {
            return dateRangeSupplier.apply(startDate, endDate);
        } else if (startDate != null) {
            return dateRangeSupplier.apply(startDate, LocalDate.now());
        } else if (endDate != null) {
            return dateRangeSupplier.apply(LocalDate.of(1990, 1, 2), endDate);
        } else {
            return noDateRangeSupplier.get();
        }
    }

}
