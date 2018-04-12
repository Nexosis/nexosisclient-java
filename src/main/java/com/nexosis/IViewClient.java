package com.nexosis;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.*;
import com.nexosis.util.Action;

public interface IViewClient {
    Action<HttpRequest, HttpResponse> getHttpMessageTransformer();
    void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer);

    /**
     *  Lists views that have been saved to the system
     * @param viewQuery
     * @return A listing of views associated with your compan
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinitionList list(ViewQuery viewQuery) throws NexosisClientException;

    /**
     * Gets a view
     *
     * @param query The query critera for the data returned
     * @return A {@link ViewDetail ViewDetail} with data about the view definition and the view data itself.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDetail get(ViewDataQuery query) throws NexosisClientException;

    /**
     * Creates a view
     *
     * @param viewName  The name of the view
     * @param view      The ViewInfo with the specifics of the view
     * @return          The definition of the created view
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ViewDefinition create(String viewName, ViewInfo view) throws NexosisClientException;

    /**
     * Deletes a view
     *
     * @param criteria The criteria for the view delete as well as which data related to the view to delete
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    void remove(ViewDeleteCriteria criteria) throws NexosisClientException;

}
