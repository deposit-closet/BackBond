package com.github.flo456123.BackBond.api.update;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper around the {@link InputStream} class to reduce verbosity.
 *
 * @param inputStream the input stream containing the XML data
 */
public record HttpResponse(InputStream inputStream) implements Closeable {

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
