package com.nexosis;

import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.AccountBalance;
import com.nexosis.util.Action;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

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
     * @return A {@link com.nexosis.model.AccountBalance AccountBalance} object.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    AccountBalance getAccountBalance() throws NexosisClientException;

    /**
     * Gets the current account balance.
     * <P>
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return A {@link com.nexosis.model.AccountBalance AccountBalance} object.
     * @throws NexosisClientException when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    AccountBalance getAccountBalance(Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Access to the Session based operations in the API.
     * <p>
     * @return
     */
    public ISessionClient getSessions();

    /**
     * Access to the Session based operations in the API. (private)
     * <p>
     * @param sessions
     */
    void setSessions(ISessionClient sessions);

    /**
     * Access to the DataSet based operations in the API.
     * <p>
     * @return
     */
    IDataSetClient getDataSets();

    /**
     * Access to the DataSet based operations in the API. (private)
     * <p>
     * @param dataSets
     */
    void setDataSets(IDataSetClient dataSets);

    /**
     * Access to the Imports based operations in the API.
     * <p>
     * @return
     */
    public IImportClient getImports();

    /**
     * Access to the Imports based operations in the API. (private)
     * <p>
     * @param imports
     */
    void setImports(IImportClient imports);

}
