package com.nexosis.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.IViewClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;

import java.util.Map;

public class ViewClient implements IViewClient {
    private ApiConnection apiConnection;
    private Action<HttpRequest, HttpResponse> httpMessageTransformer = null;

    public Action<HttpRequest, HttpResponse> getHttpMessageTransformer() {
        return httpMessageTransformer;
    }

    public void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer) {
        this.httpMessageTransformer = httpMessageTransformer;
    }

    public ViewClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ViewDefinitionList list(ViewQuery query) throws NexosisClientException {
        Argument.IsNotNull(query, "ViewQuery");
        Map<String,Object> parameters = query.toParameters();

        return apiConnection.get(ViewDefinitionList.class,"views", parameters, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewDetail get(ViewDataQuery query) throws NexosisClientException {
        Argument.IsNotNull(query, "ViewDataQuery");
        Argument.IsNotNullOrEmpty(query.getName(), "ViewDataQuery.Name");

        Map<String,Object> parameters = query.toParameters();
        return apiConnection.get(ViewDetail.class, "views/" + query.getName(), parameters, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewDefinition create(String viewName, ViewInfo view) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName, "viewName");
        Argument.IsNotNull(view, "view");

        return apiConnection.put(ViewDefinition.class, "views/" + viewName, null, view, this.httpMessageTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(ViewDeleteCriteria criteria) throws NexosisClientException {
        Argument.IsNotNull(criteria, "ViewDeleteCriteria");
        Argument.IsNotNullOrEmpty(criteria.getName(), "ViewDeleteCriteria.Name");

        Map<String,Object> parameters = criteria.toParameters();
        apiConnection.delete("views/" + criteria.getName(), parameters, this.httpMessageTransformer);
    }
}
