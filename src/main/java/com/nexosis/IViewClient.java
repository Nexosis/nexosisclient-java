package com.nexosis;

import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.Action;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.List;

public interface IViewClient {
    /**
     * Create a new view.
     * <P>
     * PUT of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param viewName The unique name of the view to create (must be unique across views and datasets)
     * @param dataSetName Name of the dataset to which to add data.
     * @param rightDataSetName Name of the dataset to join to
     * @param columnDef  Optional. Definition of columns to override those from base datasets.
     * @return A {@link ViewDefinition ViewDefinition} The created definition returned from the server
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinition create(String viewName, String dataSetName, String rightDataSetName, Columns columnDef) throws NexosisClientException;

    /**
     * Create a new view.
     * <P>
     * PUT of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param definition  The definition of the view to create
     * @param httpMessageTransformer  A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link ViewDefinition ViewDefinition} The created definition returned from the server
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinition create(ViewDefinition definition, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;


    /**
     * Gets the list of all views that have been saved to the system.
     * <P>
     * GET of https://ml.nexosis.com/api/views
     * <P>
     * @return A {@link ViewDefinitionList ViewDefinitionList} object c
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinitionList list() throws NexosisClientException;

    /**
     * Gets the list of views that have been saved to the system, filtering by partial name match.
     * <P>
     * GET of https://ml.nexosis.com/api/views
     * <P>
     * @param nameFilter Limits results to only those views with names containing the specified value
     * @param dataSetNameFilter Limits results to only those views based on datasets with the name
     * @return The List&lt;T&gt; of {@link ViewDefinitionList ViewDefinitionList} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinitionList list(String nameFilter, String dataSetNameFilter) throws NexosisClientException;

    /**
     * Gets the list of views that have been saved to the system, filtering by partial name match.
     * <P>
     * GET of https://ml.nexosis.com/api/views
     * <P>
     * @param nameFilter Limits results to only those views with names containing the specified value
     * @param dataSetNameFilter Limits results to only those views based on datasets with the name
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return The List&lt;T&gt; of {@link ViewDefinitionList ViewDefinitionList} objects.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinitionList list(String nameFilter, String dataSetNameFilter, int page, int pageSize, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Get the data in the view, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param viewName Name of the dataset for which to retrieve data.
     * @return A {@link ViewData ViewData} object containing the data by name filter.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewData get(String viewName) throws NexosisClientException;

    /**
     * Get the data in the view, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param viewName    Name of the view for which to retrieve data.
     * @param query Additional parameters to limit the data returned
     * @return A {@link ViewData ViewData} object containing filtered data
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewData get(String viewName, ListQuery query) throws NexosisClientException;

    /**
     * Get the data in the view, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param viewName            Name of the dataset for which to retrieve data.
     * @param query Additional parameters to limit the data returned
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link ViewData ViewData} object containing filtered data
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewData get(String viewName, ListQuery query, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Get the data in the view, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param viewName            Name of the dataset for which to retrieve data.
     * @param output                 An output stream to write the data set to
     * @param query Additional parameters to limit the data returned
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void get(String viewName, OutputStream output, ListQuery query) throws NexosisClientException;

    /**
     * Get the data in the set and write it to the output stream, optionally filtering it.
     * <P>
     * GET of https://ml.nexosis.com/api/views/{viewName}
     * <P>
     * @param viewName            Name of the view for which to retrieve data.
     * @param output                 An output stream to write the data to
     * @param query Additional parameters to limit the data returned
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException
     */
    void get(String viewName, OutputStream output, ListQuery query, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Remove the view.
     * <p>
     * DELETE to https://ml.nexosis.com/api/views/{viewName}
     * <p>
     *
     * @param viewName   Name of the view to remove.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    public void remove(String viewName) throws NexosisClientException;

    /**
     * Remove the view.
     * <p>
     * DELETE to https://ml.nexosis.com/api/views/{viewName}
     * <p>
     *
     * @param viewName   Name of the view to remove.
     * @param cascadeSessions  Determine whether all sessions created from the named view are also removed.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    public void remove(String viewName, boolean cascadeSessions, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;
}
