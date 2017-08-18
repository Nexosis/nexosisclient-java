package com.nexosis.impl;

import com.nexosis.IViewClient;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewClient implements IViewClient {
    private ApiConnection apiConnection;

    public ViewClient(ApiConnection apiConnection) {
        this.apiConnection = apiConnection;
    }


    /**
     * Create a new view.
     * <p>
     * PUT of https://ml.nexosis.com/api/data/{dataSetName}
     * <p>
     *
     * @param viewName         The unique name of the view to create (must be unique across views and datasets)
     * @param dataSetName      Name of the dataset to which to add data.
     * @param rightDataSetName Name of the dataset to join to
     * @param columnDef        Optional. Definition of columns to override those from base datasets.
     * @return A {@link ViewDefinition ViewDefinition} The created definition returned from the server
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewDefinition create(String viewName, String dataSetName, String rightDataSetName, Columns columnDef) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        Argument.IsNotNullOrEmpty(dataSetName,"dataSetName");
        Argument.IsNotNullOrEmpty(rightDataSetName,"rightDataSetName");
        ViewDefinition definition = new ViewDefinition();
        definition.setViewName(viewName);
        definition.setDataSetName(dataSetName);
        definition.setColumns(columnDef);
        Join join = new Join();
        join.setDataSetName(rightDataSetName);
        List<Join> joins = new ArrayList<Join>();
        joins.add(join);
        definition.setJoins(joins);
        return create(definition, null);
    }

    /**
     * Create a new view.
     * <p>
     * PUT of https://ml.nexosis.com/api/data/{dataSetName}
     * <p>
     *
     * @param definition             The definition of the view to create
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link ViewDefinition ViewDefinition} The created definition returned from the server
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewDefinition create(ViewDefinition definition, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return apiConnection.put(ViewDefinition.class,"views/" + definition.getViewName(),null,definition,httpMessageTransformer);
    }

    /**
     * Gets the list of all views that have been saved to the system.
     * <p>
     * GET of https://ml.nexosis.com/api/data
     * <p>
     *
     * @return A {@link ViewDefinitionList ViewDefinitionList} object c
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewDefinitionList list() throws NexosisClientException {
        return list(null,null,0,50,null);
    }

    /**
     * Gets the list of views that have been saved to the system, filtering by partial name match.
     * <p>
     * GET of https://ml.nexosis.com/api/data
     * <p>
     *
     * @param nameFilter Limits results to only those views with names containing the specified value
     * @param dataSetNameFilter Limits results to only those views based on datasets with the name
     * @return The List&lt;T&gt; of {@link ViewDefinitionList ViewDefinitionList} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewDefinitionList list(String nameFilter, String dataSetNameFilter) throws NexosisClientException {
        return list(nameFilter, dataSetNameFilter,0, 50, null);
    }

    /**
     * Gets the list of views that have been saved to the system, filtering by partial name match.
     * <p>
     * GET of https://ml.nexosis.com/api/data
     * <p>
     *
     * @param nameFilter Limits results to only those views with names containing the specified value
     * @param dataSetNameFilter Limits results to only those views based on datasets with the name
     * @param page The page of results to return. Defaults to 0.
     * @param pageSize The number of results per page. Defaults to 50. Max 1000.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return The List&lt;T&gt; of {@link ViewDefinitionList ViewDefinitionList} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewDefinitionList list(String nameFilter, String dataSetNameFilter, int page, int pageSize, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        List<NameValuePair> queryParams = new ArrayList<>();

        queryParams.add(new BasicNameValuePair("page", String.valueOf(page)));
        queryParams.add(new BasicNameValuePair("pageSize", String.valueOf(pageSize)));

        if (!StringUtils.isEmpty(nameFilter)) {
            queryParams.add(new BasicNameValuePair("partialName", nameFilter));
        }

        if (!StringUtils.isEmpty(dataSetNameFilter)) {
            queryParams.add(new BasicNameValuePair("dataSetName", dataSetNameFilter));
        }

        return apiConnection.get(ViewDefinitionList.class, "views", queryParams, httpMessageTransformer);
    }

    /**
     * Get the data in the set, optionally filtering it.
     * <p>
     * GET of https://ml.nexosis.com/api/data/{viewName}
     * <p>
     *
     * @param viewName Name of the dataset for which to retrieve data.
     * @return A {@link ViewData ViewData} object containing the data by name filter.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewData get(String viewName) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        return get(viewName,new ListQuery(0,50,null,null),null);
    }

    /**
     * Get the data in the view, optionally filtering it.
     * <p>
     * GET of https://ml.nexosis.com/api/data/{viewName}
     * <p>
     *
     * @param viewName Name of the view for which to retrieve data.
     * @param query    Additional parameters to limit the data returned
     * @return A {@link ViewData ViewData} object containing filtered data
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewData get(String viewName, ListQuery query) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        return get(viewName, query, null);
    }

    /**
     * Get the data in the set, optionally filtering it.
     * <p>
     * GET of https://ml.nexosis.com/api/data/{viewName}
     * <p>
     *
     * @param viewName               Name of the dataset for which to retrieve data.
     * @param query                  Additional parameters to limit the data returned
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link ViewData ViewData} object containing filtered data
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public ViewData get(String viewName, ListQuery query, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        List<NameValuePair> parameters = ProcessDataSetGetParameters(query);
        return apiConnection.get(ViewData.class, "views/" + viewName, parameters, httpMessageTransformer);
    }

    private List<NameValuePair> ProcessDataSetGetParameters(ListQuery query) {
        List<NameValuePair> parameters = new ArrayList<>();

        if(query != null) {
            parameters.add(new BasicNameValuePair("page", Integer.toString(query.getPageNumber())));
            parameters.add(new BasicNameValuePair("pageSize", Integer.toString(query.getPageSize())));
            if (query.getStartDate() != null)
                parameters.add(new BasicNameValuePair("startDate", query.getStartDate().toDateTimeISO().toString()));
            if (query.getEndDate() != null)
                parameters.add(new BasicNameValuePair("endDate", query.getEndDate().toDateTimeISO().toString()));

            // Append includeColums to parameters
            if (query.getIncludeColumns() != null) {
                for (String s : query.getIncludeColumns()) {
                    parameters.add(new BasicNameValuePair("include", s));
                }
            }
        }
        return parameters;
    }

    /**
     * Get the data in the set, optionally filtering it.
     * <p>
     * GET of https://ml.nexosis.com/api/data/{viewName}
     * <p>
     *
     * @param viewName Name of the dataset for which to retrieve data.
     * @param output   An output stream to write the data set to
     * @param query    Additional parameters to limit the data returned
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public void get(String viewName, OutputStream output, ListQuery query) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        get(viewName,output,query,null);
    }

    /**
     * Get the data in the set and write it to the output stream, optionally filtering it.
     * <p>
     * GET of https://ml.nexosis.com/api/data/{viewName}
     * <p>
     *
     * @param viewName               Name of the dataset for which to retrieve data.
     * @param output                 An output stream to write the data set to
     * @param query                  Additional parameters to limit the data returned
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException
     */
    @Override
    public void get(String viewName, OutputStream output, ListQuery query, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        List<NameValuePair> parameters = ProcessDataSetGetParameters(query);
        apiConnection.get(ViewData.class,"views" + viewName, parameters,httpMessageTransformer, output);
    }

    /**
     * Remove the view.
     * <p>
     * DELETE to https://ml.nexosis.com/api/views/{viewName}
     * <p>
     *
     * @param viewName               Name of the dataset from which to remove data.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public void remove(String viewName) throws NexosisClientException {
        remove(viewName,false,null);
    }

    /**
     * Remove the view.
     * <p>
     * DELETE to https://ml.nexosis.com/api/views/{viewName}
     * <p>
     *
     * @param viewName               Name of the dataset from which to remove data.
     * @param cascadeSessions  Determine whether all sessions created from the named view are also removed.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    @Override
    public void remove(String viewName, boolean cascadeSessions, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        Argument.IsNotNullOrEmpty(viewName,"viewName");
        List<NameValuePair> parameters = new ArrayList<>();
        if(cascadeSessions)
            parameters.add( new BasicNameValuePair("cascade", "session"));
        apiConnection.delete("views/" + viewName, parameters, httpMessageTransformer);
    }
}
