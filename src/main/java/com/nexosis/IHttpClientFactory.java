package com.nexosis;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 */
public interface IHttpClientFactory {
    CloseableHttpClient createClient();
}
