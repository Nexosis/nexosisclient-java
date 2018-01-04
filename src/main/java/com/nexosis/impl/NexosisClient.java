package com.nexosis.impl;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.nexosis.*;
import com.nexosis.model.AccountQuotas;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

public class NexosisClient implements INexosisClient {
    private String key;
    private String configuredUrl;
    private ApiConnection apiConnection;
    private final static int maxPageSize = 1000;
    private final static int defaultPageSize = 1000;
    private ISessionClient sessions;
    private IDataSetClient dataSets;
    private IImportClient imports;
    private IViewClient views;
    private IModelClient models;
    private Action<HttpRequest, HttpResponse> httpMessageTransformer;

    /**
     * The client id and version sent as the User-Agent header
     */
    public final static String CLIENT_VERSION = "Nexosis-Java-API-Client/1.3";

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


    /**
     * The URL endpoint the client will connect to.
     *
     * @return
     */
    public String getConfiguredUrl() {
        return configuredUrl != null ? configuredUrl : BASE_URL;
    }

    public static int getMaxPageSize() {
        return maxPageSize;
    }

    public static int getDefaultPageSize() {
        return defaultPageSize;
    }

    /**
     * Create a new instance of the Api Client loading from Environment Variable
     */
    public NexosisClient() {
        this(System.getenv("NEXOSIS_API_KEY"));
    }

    /**
     * Constructs a instance of the client with the api key as a parameter.
     * <p>
     *
     * @param key The api key from your account.
     */
    public NexosisClient(String key) {
        this(key, BASE_URL, new NetHttpTransport());
    }

    /**
     * Internal provided for testing use only
     * <p>
     *
     * @param key      The api key from your account.
     * @param endpoint URL of Nexosis API
     */
    public NexosisClient(String key, String endpoint) {
        this(key, endpoint, new NetHttpTransport());
    }

    /**
     * Internal provided for testing use only
     * <p>
     *
     * @param key               The api key from your account.
     * @param endpoint          URL of Nexosis API
     * @param httpTransport     The Hto provide mock class for unit tests
     */
    public NexosisClient(String key, String endpoint, HttpTransport httpTransport) throws IllegalArgumentException {
        this.key = key;

        if (endpoint == null || endpoint.isEmpty())
            throw new IllegalArgumentException("No value was provided for the endpoint. If you do not know the value, use the ctor with the api key only");
        if (!endpoint.endsWith("/")) {
            endpoint = endpoint + "/";
        }

        configuredUrl = endpoint;

        apiConnection = new ApiConnection(endpoint, key, httpTransport);

        sessions = new SessionClient(apiConnection);
        dataSets = new DataSetClient(apiConnection);
        imports = new ImportClient(apiConnection);
        views = new ViewClient(apiConnection);
        models = new ModelClient(apiConnection);
    }

    /**
     * {@inheritDoc}
     */
    public Action<HttpRequest, HttpResponse> getHttpMessageTransformer() {
        return httpMessageTransformer;
    }

    /**
     * {@inheritDoc}
     */
    public void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer) {
        this.httpMessageTransformer = httpMessageTransformer;
        UpdateChildClientTransformers();
    }

    private void UpdateChildClientTransformers()
    {
        //update them if not explicitly set on the child client
        if (getSessions().getHttpMessageTransformer() == null) {
            getSessions().setHttpMessageTransformer(httpMessageTransformer);
        }

        if (getDataSets().getHttpMessageTransformer() == null) {
            getDataSets().setHttpMessageTransformer( httpMessageTransformer);
        }

        if (getImports().getHttpMessageTransformer() == null) {
            getImports().setHttpMessageTransformer(httpMessageTransformer);
        }

        if (getViews().getHttpMessageTransformer() == null) {
            getViews().setHttpMessageTransformer(httpMessageTransformer);
        }

        if (getModels().getHttpMessageTransformer() == null) {
            getModels().setHttpMessageTransformer(httpMessageTransformer);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountQuotas getAccountQuotas() throws NexosisClientException {
        return apiConnection.get(AccountQuotas.class, "/data", null, httpMessageTransformer);
    }

    @Override
    public ISessionClient getSessions() {
        return sessions;
    }



    @Override
    public IDataSetClient getDataSets() {
        return dataSets;
    }



    @Override
    public IImportClient getImports() {
        return imports;
    }



    @Override
    public IViewClient getViews() {
        return views;
    }



    @Override
    public IModelClient getModels() { return models; }

}
