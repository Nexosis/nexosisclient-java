package com.nexosis;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.AccountQuotas;
import com.nexosis.util.Action;

/**
 * The primary interface to the Nexosis API.
 */
public interface INexosisClient
{
    Action<HttpRequest, HttpResponse> getHttpMessageTransformer();
    void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer);

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
