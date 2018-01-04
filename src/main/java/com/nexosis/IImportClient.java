package com.nexosis;

import java.util.UUID;

import com.nexosis.model.*;
import org.joda.time.DateTime;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

public interface IImportClient {
    Action<HttpRequest, HttpResponse> getHttpMessageTransformer();
    void setHttpMessageTransformer(Action<HttpRequest, HttpResponse> httpMessageTransformer);
    /**
     * list imports that have been run. This will show information about them such as id and status
     *
     * @param query
     * @return The list of @see ImportDetail objects.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * GET of https://ml.nexosis.com/api/imports
     */
    ImportDetails list(ImportDetailQuery query) throws NexosisClientException;
    // ImportDetails list(int page, int pageSize) throws NexosisClientException;

    //ImportDetails list(String dataSetName, int page, int pageSize) throws NexosisClientException;


    //ImportDetails list(String dataSetName, DateTime requestedAfterDate,
    //                   DateTime requestedBeforeDate, int page, int pageSize) throws NexosisClientException;

    /**
     * Retrieve an import that has been requested.  This will show information such as id and status
     * GET of https://ml.nexosis.com/api/imports/{id}
     *
     * @param id The identifier of the import
     * @return [ImportDetail] populated with the import information
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     */
    ImportDetail get(UUID id) throws NexosisClientException;

    /**
     * Import data into the Nexosis Api from a file on AWS S3
     *
     * @param detail      The details required to import from a file on S3
     *                    POST of https://ml.nexosis.com/api/imports
     *                    @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return [ImportDetail] populated with the import information
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     *                                GET of https://ml.nexosis.com/api/imports
     */
    ImportDetail importFromS3(ImportFromS3Request detail) throws NexosisClientException;

    /**
     * Import data into the Nexosis Api by issuing an HTTP GET to a url
     *
     * @param detail
     * @return  [ImportDetail] populated with the import information
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     *                                GET of https://ml.nexosis.com/api/imports
     */
    ImportDetail ImportFromUrl(ImportFromUrlRequest detail) throws NexosisClientException;

    /**
     * Import data into the Nexosis Api from a file in Azure Blob storage
     *
     * @param detail
     * @return  [ImportDetail] populated with the import information
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     *                                GET of https://ml.nexosis.com/api/imports
     */
    ImportDetail ImportFromAzure(ImportFromAzureRequest detail) throws NexosisClientException;
}
