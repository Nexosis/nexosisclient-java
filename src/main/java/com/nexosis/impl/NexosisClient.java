package com.nexosis.impl;

import com.nexosis.IDataSetClient;
import com.nexosis.IHttpClientFactory;
import com.nexosis.INexosisClient;
import com.nexosis.ISessionClient;
import com.nexosis.model.AccountBalance;
import com.nexosis.model.DataSetList;
import com.nexosis.util.Action;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class NexosisClient implements INexosisClient {
    private String key;
    private String configuredUrl;
    private ApiConnection apiConnection;
    private final static int maxPageSize = 100;
    private ISessionClient sessions;
    private IDataSetClient dataSets;

    /**
     * The client id and version sent as the User-Agent header
     */
    public final static String CLIENT_VERSION = "Nexosis-Java-API-Client/1.0";

    /**
     * The default URL of the api endpoint.
     */
    public final static String BASE_URL = "https://ml.nexosis.com/v1";

    /**
     * The currently configured api key used by this instance of the client.
     *
     * @return The currently configured API Key.
     */
    public String getApiKey() {
        return key;
    }

    /// <summary>
    /// The URL endpoint the client will connect to.
    /// </summary>

    /**
     *
     * @return
     */
    public String getConfiguredUrl() {
        return configuredUrl != null ? configuredUrl : BASE_URL;
    }

    public static int getMaxPageSize() {
        return maxPageSize;
    }

    /**
     * Create a new instance of the Api Client loading from Environment Variable
     */
    public NexosisClient() {
        this(System.getenv("NEXOSIS_API_KEY"));
    }

    /**
     * Constructs a instance of the client with the api key as a parameter.
     * <P>
     * @param key The api key from your account.
     */
    public NexosisClient(String key) {
        this(key, BASE_URL, new HttpClientFactory());
    }

    /**
     * Internal provided for testing use only
     * <P>
     * @param key The api key from your account.
     * @param endpoint URL of Nexosis API
     */
    public NexosisClient(String key, String endpoint) {
        this(key, endpoint, new HttpClientFactory());
    }
    /**
     * Internal provided for testing use only
     * <P>
     * @param key The api key from your account.
     * @param endpoint URL of Nexosis API
     * @param httpClientFactory An IHttpClientFactory to provide mock class for unit tests
     */
    public NexosisClient(String key, String endpoint, IHttpClientFactory httpClientFactory) throws IllegalArgumentException {
        this.key = key;

        if(endpoint == null || endpoint.isEmpty())
            throw new IllegalArgumentException("No value was provided for the endpoint. If you do not know the value, use the ctor with the api key only");
        if (!endpoint.endsWith("/")) {
            endpoint = endpoint + "/";
        }

        configuredUrl = endpoint;

        apiConnection = new ApiConnection(endpoint, key, httpClientFactory);

        sessions = new SessionClient(apiConnection);
        dataSets = new DataSetClient(apiConnection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountBalance getAccountBalance() throws NexosisClientException {
        return getAccountBalance(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountBalance getAccountBalance(Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.get(AccountBalance.class, "/data", null, httpMessageTransformer);
    }

    @Override
    public ISessionClient getSessions() {
        return sessions;
    }

    @Override
    public void setSessions(ISessionClient sessions) {
        this.sessions = sessions;
    }

    @Override
    public IDataSetClient getDataSets() {
        return dataSets;
    }

    @Override
    public void setDataSets(IDataSetClient dataSets) {
        this.dataSets = dataSets;
    }
}
