package com.nexosis;
import java.util.UUID;
import org.joda.time.DateTime;
import java.util.List;
import com.nexosis.impl.NexosisClientException;
import com.nexosis.model.ImportDetail;
import com.nexosis.model.Columns;
import com.nexosis.util.Action;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public interface IImportClient {
    /// <summary>
    /// List imports that have been run. This will show information about them such as id and status
    /// </summary>
    /// <returns>The list of <see cref="ImportDetail"/> objects.</returns>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <remarks>GET of https://ml.nexosis.com/api/imports</remarks>
    List<ImportDetail> List();

    /// <summary>
    /// List imports that have been run. This will show information about them such as id and status
    /// </summary>
    /// <param name="dataSetName">Limits imports to those with the specified name.</param>
    /// <returns>The list of <see cref="ImportDetail"/> objects.</returns>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <remarks>GET of https://ml.nexosis.com/api/imports</remarks>
    List<ImportDetail> List(String dataSetName);


    /// <summary>
    /// List imports that have been run. This will show information about them such as id and status
    /// </summary>
    /// <param name="dataSetName">Limits imports to those with the specified name.</param>
    /// <param name="requestedAfterDate">Limits imports to those requested on or after the specified date.</param>
    /// <param name="requestedBeforeDate">Limits imports to those requested on or before the specified date.</param>
    /// <returns>The list of <see cref="ImportDetail"/> objects.</returns>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <remarks>GET of https://ml.nexosis.com/api/imports</remarks>
    List<ImportDetail> List(String dataSetName, DateTime requestedAfterDate,
                                  DateTime requestedBeforeDate);

    /// <summary>
    /// List imports that have been run. This will show information about them such as id and status
    /// </summary>
    /// <param name="dataSetName">Limits imports to those with the specified name.</param>
    /// <param name="requestedAfterDate">Limits imports to those requested on or after the specified date.</param>
    /// <param name="requestedBeforeDate">Limits imports to those requested on or before the specified date.</param>
    /// <param name="httpMessageTransformer">A function that is called immediately before sending the request and after receiving a response which allows for message transformation.</param>
    /// <returns>The list of <see cref="ImportDetail"/> objects.</returns>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <remarks>GET of https://ml.nexosis.com/api/imports</remarks>
    List<ImportDetail> List(String dataSetName, DateTime requestedAfterDate,
                                  DateTime requestedBeforeDate, Action<HttpRequest, HttpResponse> httpMessageTransformer);


    /// <summary>
    /// Retrieve an import that has been requested.  This will show information such as id and status
    /// </summary>
    /// <param name="id">The identifier of the import</param>
    /// <returns>A <see cref="ImportDetail" /> populated with the import information</returns>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <remarks>GET of https://ml.nexosis.com/api/imports/{id}</remarks>
    ImportDetail Get(UUID id);

    /// <summary>
    /// Retrieve an import that has been requested.  This will show information such as id and status
    /// </summary>
    /// <param name="id">The identifier of the import</param>
    /// <param name="httpMessageTransformer">A function that is called immediately before sending the request and after receiving a response which allows for message transformation.</param>
    /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
    /// <returns>A <see cref="ImportDetail" /> populated with the import information</returns>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <remarks>GET of https://ml.nexosis.com/api/imports/{id}</remarks>
    ImportDetail Get(UUID id, Action<HttpRequest, HttpResponse> httpMessageTransformer);

    /// <summary>
    /// Import data into the Nexosis Api from a file on AWS S3
    /// </summary>
    /// <param name="dataSetName">The dataset into which the data should be imported</param>
    /// <param name="bucket">The AWS S3 bucket containing the file to be imported</param>
    /// <param name="path">The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.</param>
    /// <param name="region">The AWS region where the bucket is located.  Defaults to us-east-1</param>
    /// <remarks>POST of https://ml.nexosis.com/api/imports</remarks>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <returns>A <see cref="ImportDetail" /> populated with the import information</returns>
    ImportDetail ImportFromS3(String dataSetName, String bucket, String path, String region);


    /// <summary>
    /// Import data into the Nexosis Api from a file on AWS S3
    /// </summary>
    /// <param name="dataSetName">The dataset into which the data should be imported</param>
    /// <param name="bucket">The AWS S3 bucket containing the file to be imported</param>
    /// <param name="path">The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.</param>
    /// <param name="region">The AWS region where the bucket is located.  Defaults to us-east-1</param>
    /// <param name="columns">Metadata about each column in the dataset</param>
    /// <remarks>POST of https://ml.nexosis.com/api/imports</remarks>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <returns>A <see cref="ImportDetail" /> populated with the import information</returns>
    ImportDetail ImportFromS3(String dataSetName, String bucket, String path, String region,
                                    Columns columns);

    /// <summary>
    /// Import data into the Nexosis Api from a file on AWS S3
    /// </summary>
    /// <param name="dataSetName">The dataset into which the data should be imported</param>
    /// <param name="bucket">The AWS S3 bucket containing the file to be imported</param>
    /// <param name="path">The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.</param>
    /// <param name="region">The AWS region where the bucket is located.  Defaults to us-east-1</param>
    /// <param name="httpMessageTransformer">A function that is called immediately before sending the request and after receiving a response which allows for message transformation.</param>
    /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
    /// <remarks>POST of https://ml.nexosis.com/api/imports</remarks>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <returns>A <see cref="ImportDetail" /> populated with the import information</returns>
    ImportDetail ImportFromS3(String dataSetName, String bucket, String path, String region,
                              Action<HttpRequest, HttpResponse> httpMessageTransformer);

    /// <summary>
    /// Import data into the Nexosis Api from a file on AWS S3
    /// </summary>
    /// <param name="dataSetName">The dataset into which the data should be imported</param>
    /// <param name="bucket">The AWS S3 bucket containing the file to be imported</param>
    /// <param name="path">The path inside the bucket to the file. The Nexosis API can import a single file at a time.  The file can be in either csv or json format, and optionally with gzip compression.</param>
    /// <param name="region">The AWS region where the bucket is located.  Defaults to us-east-1</param>
    /// <param name="columns">Metadata about each column in the dataset</param>
    /// <param name="httpMessageTransformer">A function that is called immediately before sending the request and after receiving a response which allows for message transformation.</param>
    /// <param name="cancellationToken">A cancellation token that can be used by other objects or threads to receive notice of cancellation.</param>
    /// <remarks>POST of https://ml.nexosis.com/api/imports</remarks>
    /// <exception cref="NexosisClientException">Thrown when 4xx or 5xx response is received from server, or errors in parsing the response.</exception>
    /// <returns>A <see cref="ImportDetail" /> populated with the import information</returns>
    ImportDetail ImportFromS3(String dataSetName, String bucket, String path, String region, Columns columns,
                              Action<HttpRequest, HttpResponse> httpMessageTransformer);
}
