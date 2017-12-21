package com.nexosis;

import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.AccountQuotas;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

/**
 * The primary interface to the Nexosis API.
 */
public interface INexosisClient
{
    /**
     * Gets the current account balance.
     * <P>
     * GET of https://ml.nexosis.com/api/sessions
     * <P>
     * @return A {@link com.nexosis.model.AccountQuotas AccountBalance} object.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    AccountQuotas getAccountQuotas() throws NexosisClientException;

    /**
     * Gets the current account balance.
     * <P>
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link com.nexosis.model.AccountQuotas AccountBalance} object.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    AccountQuotas getAccountQuotas(Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Access to the Session based operations in the API.
     * <p>
     * @return
     */
    ISessionClient getSessions();


    /**
     * Access to the DataSet based operations in the API.
     * <p>
     * @return
     */
    IDataSetClient getDataSets();


    /**
     * Access to the Imports based operations in the API.
     * <p>
     * @return
     */
    IImportClient getImports();


    /**
     * Access to the Views based operations in the API.
     * <p>
     * @return
     */
    IViewClient getViews();


    /**
     * Access to the Models based operations in the API.
     * <p>
     * @return
     */
    IModelClient getModels();


}
