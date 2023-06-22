package com.github.flo456123.BackBond.entry.update;

import com.github.flo456123.BackBond.entry.Entry;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Helper class to {@link DataUpdater} which is used for parsing XML data from
 * an input stream into an {@link Entry}.
 */
@Component
@NoArgsConstructor
public class XmlParser {

    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    /**
     * Main method used for parsing XML data input streams into lists of entries.
     *
     * @param content the input stream containing the XML data
     * @return a list of entries that were parsed from the input stream
     * @throws IOException if a file access error occurs
     * @throws SAXException if parser is not configured correctly for the {@link Entry} format
     * @throws ParserConfigurationException if {@link DocumentBuilderFactory} cannot create a new {@link DocumentBuilder}
     */
    public Queue<Entry> parseEntries(InputStream content)
            throws IOException, SAXException, ParserConfigurationException
    {
        Queue<Entry> entries = new LinkedList<>();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(content);

        NodeList nodeList = document.getElementsByTagName("entry");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element entryElement = (Element) nodeList.item(i);
            Entry entry = parseEntryElement(entryElement);
            entries.offer(entry);
        }

        return entries;
    }

    /**
     * Parses an {@link Element} into an {@link Entry} using the entry format from home.treasury.gov.
     *
     * @param entryElement the element to parse into an entry
     * @return the parsed {@link Entry} object
     */
    private Entry parseEntryElement(Element entryElement)
    {
        Entry entry = new Entry();
        entry.setNewDate(LocalDateTime.parse(entryElement.getElementsByTagName("d:NEW_DATE").item(0).getTextContent()));

        Field[] fields = Entry.class.getDeclaredFields();
        // iterate through each field of the Entry class, and set it to the double value
        // of its field name
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.startsWith("BC_")) {
                String tagName = "d:" + fieldName;
                Double value = parseElementDouble(entryElement, tagName);
                field.setAccessible(true);
                try {
                    field.setDouble(entry, value);
                } catch (IllegalAccessException e) {
                    logger.error("Unable to set field " + fieldName);
                }
            }
        }

        return entry;
    }

    final double DEFAULT_VALUE = 0.0;

    /**
     * Parses a specific tag value from the {@link Element} and parsers it into a double value.
     *
     * @param element the {@link Element} to parse from
     * @param tagName the tag name to parse
     * @return the double value parsed from the tag, or {@code DEFAULT_VALUE} if the tag is empty
     */
    private Double parseElementDouble(Element element, String tagName)
    {
        try {
            return Double.parseDouble(element.getElementsByTagName(tagName).item(0).getTextContent());
        } catch (NullPointerException ignored) {
            return DEFAULT_VALUE;
        }
    }
}
