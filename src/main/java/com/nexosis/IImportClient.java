package com.nexosis;

import java.util.UUID;

import com.nexosis.model.ImportDetails;
import org.joda.time.DateTime;

import java.util.List;

import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.ImportDetail;
import com.nexosis.model.Columns;
import com.nexosis.util.Action;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;

public interface IImportClient {
    /**
     * list imports that have been run. This will show information about them such as id and status
     *
     * @return The list of @see ImportDetail objects.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * GET of https://ml.nexosis.com/api/imports
     */
    ImportDetails list(int page, int pageSize) throws NexosisClientException;

    /**
     * list imports that have been run. This will show information about them such as id and status
     *
     * @param dataSetName Limits imports to those with the specified name.
     * @return The list of ImportDetail objects.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     *                                GET of https://ml.nexosis.com/api/imports
     */
    ImportDetails list(String dataSetName, int page, int pageSize) throws NexosisClientException;


    /**
     * list imports that have been run. This will show information about them such as id and status
     *
     * @param dataSetName         Limits imports to those with the specified name.
     * @param requestedAfterDate  Limits imports to those requested on or after the specified date.
     * @param requestedBeforeDate Limits imports to those requested on or before the specified date.
     * @return The list of ImportDetail objects.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * GET of https://ml.nexosis.com/api/imports
     */
    ImportDetails list(String dataSetName, DateTime requestedAfterDate,
                       DateTime requestedBeforeDate, int page, int pageSize) throws NexosisClientException;

    /**
     * list imports that have been run. This will show information about them such as id and status
     *
     * @param dataSetName            Limits imports to those with the specified name.
     * @param requestedAfterDate     Limits imports to those requested on or after the specified date.
     * @param requestedBeforeDate    Limits imports to those requested on or before the specified date.
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return The list of ImportDetail objects.
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * GET of https://ml.nexosis.com/api/imports
     */
    ImportDetails list(String dataSetName, DateTime requestedAfterDate,
                       DateTime requestedBeforeDate, int page, int pageSize, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;


    /**
     * Retrieve an import that has been requested.  This will show information such as id and status
     *
     * @param id The identifier of the import
     * @return [ImportDetail] populated with the import information
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * GET of https://ml.nexosis.com/api/imports/{id}
     */
    ImportDetail get(UUID id) throws NexosisClientException;

    /**
     * Retrieve an import that has been requested.  This will show information such as id and status
     *
     * @param id                     The identifier of the import
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     * @return [ImportDetail] populated with the import information
     * @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * GET of https://ml.nexosis.com/api/imports/{id}
     */
    ImportDetail get(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Import data into the Nexosis Api from a file on AWS S3
     *
     * @param dataSetName The dataset into which the data should be imported
     * @param bucket      The AWS S3 bucket containing the file to be imported
     * @param path        The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.
     * @param region      The AWS region where the bucket is located.  Defaults to us-east-1
     *                    POST of https://ml.nexosis.com/api/imports
     *                    @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return [ImportDetail] populated with the import information
     */
    ImportDetail importFromS3(String dataSetName, String bucket, String path, String region) throws NexosisClientException;


    /**
     * Import data into the Nexosis Api from a file on AWS S3
     *
     * @param dataSetName The dataset into which the data should be imported
     * @param bucket      The AWS S3 bucket containing the file to be imported
     * @param path        The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.
     * @param region      The AWS region where the bucket is located.  Defaults to us-east-1
     * @param columns     Metadata about each column in the dataset
     *                    POST of https://ml.nexosis.com/api/imports
     *                    @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return [ImportDetail] populated with the import information
     */
    ImportDetail importFromS3(String dataSetName, String bucket, String path, String region,
                              Columns columns) throws NexosisClientException;

    /**
     * Import data into the Nexosis Api from a file on AWS S3
     *
     * @param dataSetName            The dataset into which the data should be imported
     * @param bucket                 The AWS S3 bucket containing the file to be imported
     * @param path                   The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.
     * @param region                 The AWS region where the bucket is located.  Defaults to us-east-1
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     *                               POST of https://ml.nexosis.com/api/imports
     *                               @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return [ImportDetail] populated with the import information
     */
    ImportDetail importFromS3(String dataSetName, String bucket, String path, String region,
                              Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;

    /**
     * Import data into the Nexosis Api from a file on AWS S3
     *
     * @param dataSetName            The dataset into which the data should be imported
     * @param bucket                 The AWS S3 bucket containing the file to be imported
     * @param path                   The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.
     * @param region                 The AWS region where the bucket is located.  Defaults to us-east-1
     * @param columns                Metadata about each column in the dataset
     * @param httpMessageTransformer A function that is called immediately before sending the request and after receiving a response which allows for message transformation.
     *                               POST of https://ml.nexosis.com/api/imports
     *                               @throws NexosisClientException Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.
     * @return [ImportDetail] populated with the import information
     */
    ImportDetail importFromS3(String dataSetName, String bucket, String path, String region, Columns columns,
                              Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException;
}
