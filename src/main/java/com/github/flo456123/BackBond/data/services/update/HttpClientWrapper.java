package com.github.flo456123.BackBond.data.services.update;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper around the apache {@link CloseableHttpClient} class which introduces
 * additional features such as limiting the HTTP client to a certain
 * number of connections.
 *
 * <p>
 *     This is necessary because the XML feed is spread throughout 33 different
 *     webpages, and if they were to all be processed at once, there would be a
 *     chance of other services in the API being slowed down because of it.
 * </p>
 *
 * <p>
 *     The wrapper also implements {@link Closeable} to automatically close
 *     any resources that were used to fetch the XML data.
 * </p>
 */
@Component
public class HttpClientWrapper implements Closeable {

    private final CloseableHttpClient httpClient;
    private final PoolingHttpClientConnectionManager connectionManager;

    public HttpClientWrapper() {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10);

        httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
    }

    /**
     * Fetches the XML data from a given URL and returns a {@link HttpResponse}
     * containing the XML data input stream.
     *
     * @param url the url to fetch the XML data from
     * @return a {@link HttpResponse} instance containing the {@link InputStream}
     * @throws IOException if there is an error while fetching the XML data
     */
    public HttpResponse fetchXmlData(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
            return new HttpResponse(response.getEntity().getContent());
    }

    /**
     * Closes resources once the clients are
     * done being used.
     *
     * @throws IOException if resources cannot
     * be closed
     */
    @Override
    public void close() throws IOException {
        httpClient.close();
        connectionManager.close();
    }

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
}
