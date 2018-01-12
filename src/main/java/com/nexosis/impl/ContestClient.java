package com.nexosis.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.IContestClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;

import java.util.Map;
import java.util.UUID;

public class ContestClient implements IContestClient {
    private ApiConnection apiConnection;
    private Action<HttpRequest, HttpResponse> httpMessageTransformer = null;

    public Action<HttpRequest, HttpResponse> getHttpMessageTransformer() {
        return httpMessageTransformer;
    }

    public void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer) {
        this.httpMessageTransformer = httpMessageTransformer;
    }

    public ContestClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ContestResponse getContest(UUID sessionId) throws NexosisClientException {
        return apiConnection.get(ContestResponse.class, "/sessions/" + sessionId.toString() + "/contest", null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContestantResponse getChampion(UUID sessionId, ChampionQueryOptions options) throws NexosisClientException {
        Map<String,Object> queryParams = options.ToParamters();
        return apiConnection.get(ContestantResponse.class, "/sessions/" + sessionId.toString() + "/contest/champion", queryParams, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContestSelectionResponse getSelection(UUID sessionId) throws NexosisClientException {
        return apiConnection.get(ContestSelectionResponse.class, "/sessions/" + sessionId.toString() + "/contest/selection", null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChampionContestantList listContestants(UUID sessionId) throws NexosisClientException {
        return apiConnection.get(ChampionContestantList.class, "/sessions/" + sessionId.toString() + "/contest/contestants", null, httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ContestantResponse getContestant(UUID sessionId, String contestantId, ChampionQueryOptions options) throws NexosisClientException {
        Map<String,Object> queryParams = options.ToParamters();
        return apiConnection.get(ContestantResponse.class, "/sessions/" + sessionId.toString() + "/contest/contestants/" + contestantId, queryParams, httpMessageTransformer);
    }
}
