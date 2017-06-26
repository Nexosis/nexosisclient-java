package com.nexosis.impl;

import com.nexosis.IHttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientFactory implements IHttpClientFactory {
    private CloseableHttpClient client = null;

    public HttpClientFactory() { }

    public HttpClientFactory(CloseableHttpClient client)
    {
        this.client = client;
    }

    public CloseableHttpClient createClient()
    {
        if (client != null)
        {
            return client;
        }

        return HttpClients.createDefault();
    }
}
