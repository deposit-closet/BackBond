package com.github.flo456123.BackBond.entry.update;

import com.github.flo456123.BackBond.entry.Entry;
import com.github.flo456123.BackBond.entry.PYCRService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Scheduled service used to fetch data from the Daily Treasury Par Yield Curve Rates XML feed and update the database with new entries.
 *
 * <p>
 *     Injects the {@link HttpClientWrapper} and {@link XmlParser} helper classes to fetch
 *     and parse data.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class DataUpdater {

    private static final Logger logger = LoggerFactory.getLogger(DataUpdater.class);

    private final PYCRService dailyTreasuryRatesService;
    private final XmlParser xmlParser;

    private static final String baseUrl = """
            https://home.treasury.gov/resource-center/data-chart-center/interest-rates/pages/xml?data=daily_treasury_yield_curve&field_tdr_date_value=""";

    @Value("${year.range.start}")
    private int yearRangeStart;

    @Value("${year.range.end}")
    private int yearRangeEnd;

    /**
     * Triggers once a day when new data is uploaded to the government
     * XML feed and inserts any new entries into the database.
     *
     * <p>
     *     The entries are fetched for the specified year range in
     *     the Daily Treasury Par Yield Curve Rates XML feed.
     * </p>
     *
     * <p>
     *     This method is scheduled to run at 4:00:14 PM every day. I
     *     made it run at this time because it is when the government
     *     uploads new entries to the XML feed.
     * </p>
     */
    @Scheduled(cron = "14 0 16 * * ?", zone = "EST") // Executes at 16:00:14 every day
    public void updateData() {
        logger.info("Starting auto update service!");

        try (HttpClientWrapper httpClientWrapper = new HttpClientWrapper()) {
            for (int year = yearRangeStart; year <= yearRangeEnd; year++) {
                // fetch XML data from the httpClientWrapper
                HttpClientWrapper.HttpResponse response = httpClientWrapper.fetchXmlData(baseUrl + year);

                try (InputStream inputStream = response.inputStream()) {
                    // parse the XML data using xmlParser
                    List<Entry> entries = xmlParser.parseEntries(inputStream);

                    // update database with any new entries
                    dailyTreasuryRatesService.addNewEntries(entries);
                }
            }
        } catch (SAXException | ParserConfigurationException e) {
            logger.error("Error while parsing XML data...", e);
        } catch (IOException e) {
            logger.error("A network error occurred while fetching XML feed...", e);
        }

        logger.info("Auto update service finished.");
    }
}
