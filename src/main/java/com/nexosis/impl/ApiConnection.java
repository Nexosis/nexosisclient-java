package com.nexosis.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.nexosis.model.ErrorResponse;
import com.nexosis.model.ReturnsCost;
import com.nexosis.model.ReturnsStatus;
import com.nexosis.model.SessionStatus;
import com.nexosis.util.Action;
import com.nexosis.util.HttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.nexosis.IHttpClientFactory;

/**
 *
 */
public class ApiConnection {

    private String key;
    private String endpoint;
    private IHttpClientFactory httpClientFactory;
    private ObjectMapper mapper = new ObjectMapper();

    ObjectMapper getObjectMapper(){
        return mapper;
    }

    /**
     *
     *
     * @param endpoint
     * @param key
     */
    public ApiConnection(String endpoint, String key) {
        this(endpoint, key, new HttpClientFactory());
    }

    /**
     * Internal provided for testing use only
     * <P>
     * @param key The api key from your account.
     * @param endpoint URL of Nexosis API
     * @param httpClientFactory HttpClient to provide mock class for unit tests
     */
    ApiConnection(String endpoint, String key, IHttpClientFactory httpClientFactory) {
        this.endpoint = endpoint;
        this.httpClientFactory = httpClientFactory;
        this.key = key;
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public <T> T get(Class<T> type, String path, List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output) throws NexosisClientException {
        return get(type, path, parameters, httpMessageTransformer, output, null);
    }

    public <T> T get(Class<T> type, String path, List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output, String acceptType) throws NexosisClientException {
        try {

            if (StringUtils.isEmpty(acceptType)) {
                acceptType = "application/json";
            }

            URI uri = prepareURI(path, parameters);
            HttpGet request = new HttpGet();
            request.setURI(uri);

            request.addHeader("accept", acceptType);
            return makeRequest(type, request, httpMessageTransformer, output);
        } catch (URISyntaxException use) {
            throw new NexosisClientException("Internal Error.", use);
        }
    }

    public <T> T get(Class<T> type, String path, List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return get(type, path, parameters, httpMessageTransformer, (String)null);
    }

    public <T> T get(Class<T> type, String path, List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, String acceptType) throws NexosisClientException {
        try {
            if (StringUtils.isEmpty(acceptType)) {
                acceptType = "application/json";
            }

            URI uri = prepareURI(path, parameters);

            HttpGet request = new HttpGet();
            request.setURI(uri);
            request.addHeader("accept", acceptType);

            return makeRequest(type, request, httpMessageTransformer);

        } catch (URISyntaxException use) {
            throw new NexosisClientException("Internal Error.", use);
        }
    }

    public <T> T head(Class<T> type, String path, List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        try {
            URI uri = prepareURI(path, parameters);
            HttpHead request = new HttpHead();
            request.setURI(uri);
            request.addHeader("accept", "application/json");
            return makeRequest(type, request, httpMessageTransformer);
        } catch (URISyntaxException use) {
            throw new NexosisClientException("Invalid URI Syntax: " + use.getMessage());
        }
    }

    public <T> T put(Class<T> type, String path, List<NameValuePair> parameters, Object body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        return sendObjectContent(type, path, parameters, HttpMethod.PUT, body, httpMessageTransformer);
    }

    public <T> T put(Class<T> type, String path, List<NameValuePair> parameters, InputStream body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        return sendStreamContent(type, path, parameters, HttpMethod.PUT, body, httpMessageTransformer);
    }

    public <T> T post(Class<T> type, String path, List<NameValuePair> parameters, Object body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return sendObjectContent(type, path, parameters, HttpMethod.POST, body, httpMessageTransformer);
    }

    public <T> T post(Class<T> type, String path, List<NameValuePair> parameters, InputStream body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return sendStreamContent(type, path, parameters, HttpMethod.POST, body, httpMessageTransformer);
    }

    private <T> T sendObjectContent(Class<T> type, String path, List<NameValuePair> parameters, HttpMethod method, Object body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        try {

            URI uri = prepareURI(path, parameters);
            HttpEntityEnclosingRequestBase request = null;

            switch (method) {
                case PUT:
                    request = new HttpPut();
                    break;
                case POST:
                    request = new HttpPost();
                    break;
            }

            request.setURI(uri);
            request.addHeader("accept", "application/json");
            request.addHeader("Content-Type", "application/json");

            HttpEntity entity = new StringEntity(mapper.writeValueAsString(body));
            request.setEntity(entity);

            return makeRequest(type, request, httpMessageTransformer);
        } catch (URISyntaxException use) {
            throw new NexosisClientException("Invalid URI Syntax: " + use.getMessage());
        } catch (JsonProcessingException jpe) {
            throw new NexosisClientException("Invalid JSON in argument 'body': " + jpe.getMessage());
        } catch (UnsupportedEncodingException usee) {
            throw new NexosisClientException("Invalid encoding in argument 'body': " + usee.getMessage());
        }
    }

    private <T> T sendStreamContent(Class<T> type, String path, List<NameValuePair> parameters, HttpMethod method, InputStream body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        try {
            URI uri = prepareURI(path, parameters);
            HttpEntityEnclosingRequestBase request = null;

            switch (method) {
                case PUT:
                    request = new HttpPut();
                    break;
                case POST:
                    request = new HttpPost();
                    break;
            }

            request.setURI(uri);
            request.addHeader("accept", "application/json");
            request.addHeader("Content-Type", "text/csv");

            HttpEntity entity = new InputStreamEntity(body);
            request.setEntity(entity);

            return makeRequest(type, request, httpMessageTransformer);

        } catch (URISyntaxException use) {
            throw new NexosisClientException("Invalid URI Syntax: " + use.getMessage());
        }
    }


    public void delete(String path, List<NameValuePair> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        try {
            URI uri = prepareURI(path, parameters);

            HttpDelete request = new HttpDelete();
            request.setURI(uri);

            request.addHeader("accept", "application/json");
            makeRequest(request, httpMessageTransformer);
        } catch (URISyntaxException use) {
            throw new NexosisClientException("Internal Error.", use);
        }
    }


    public <T> T makeRequest(Class<T> type, HttpRequestBase request, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        HttpEntity entity = null;
        HttpResponse response;

        try (CloseableHttpClient httpClient = httpClientFactory.createClient()) {

            response = makeRequest(request, httpMessageTransformer, httpClient);
            StatusLine status = response.getStatusLine();

            if (isSuccessStatusCode(status.getStatusCode())) {
                entity = response.getEntity();
                String entityString = EntityUtils.toString(entity);

                T object = mapper.readValue(entityString, type);

                if (ReturnsCost.class.isAssignableFrom(object.getClass())) {
                    ((ReturnsCost) object).AssignCost(response.getAllHeaders());
                }

                return object;
            } else {
                ProcessFailureResponse(response);
                return null;
            }
        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request", ioe);
        } catch (NexosisClientException nce) {
            throw nce;
        } catch (Exception e) {
            throw new NexosisClientException("Error while making HTTP Request: " + e.getMessage(), e);
        }
    }

    private <T> T makeRequest(Class<T> type, HttpRequestBase request, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output) throws NexosisClientException {
        HttpResponse response;
        HttpEntity entity = null;

        try (CloseableHttpClient httpClient = httpClientFactory.createClient()) {

            response = makeRequest(request, httpMessageTransformer, httpClient);
            StatusLine status = response.getStatusLine();

            if (isSuccessStatusCode(status.getStatusCode())) {

                T object = (T) new ReturnsStatus();

                if (ReturnsStatus.class.isAssignableFrom(object.getClass())) {
                    ((ReturnsStatus) object).AssignStatus(response.getAllHeaders());
                    if (((ReturnsStatus) object).getSessionStatus().equals(SessionStatus.COMPLETED)) {
                        // Write content to stream if status is complete
                        entity = response.getEntity();
                        entity.writeTo(output);
                    }
                }

                return object;
            } else {
                ProcessFailureResponse(response);
                return null;
            }
        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request: " + ioe.getMessage());
        }
    }

    private void makeRequest(HttpRequestBase request, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        try (CloseableHttpClient httpClient = httpClientFactory.createClient()) {

            HttpResponse response = makeRequest(request, httpMessageTransformer, httpClient);

            StatusLine statusLine = response.getStatusLine();
            if (!isSuccessStatusCode(statusLine.getStatusCode())) {
                ProcessFailureResponse(response);
            }
        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request: " + ioe.getMessage());
        }
    }

    private HttpResponse makeRequest(HttpRequestBase request, Action<HttpRequest, HttpResponse> httpMessageTransformer,
                                            CloseableHttpClient httpClient) throws NexosisClientException {
        CloseableHttpResponse response;

        request.setHeader("api-key", key);
        request.setHeader("User-Agent", NexosisClient.CLIENT_VERSION);

        try {
            if (httpMessageTransformer != null)
                httpMessageTransformer.invoke(request, null);

            response = httpClient.execute(request);

            if (httpMessageTransformer != null)
                httpMessageTransformer.invoke(request, response);

            return response;

        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request: " + ioe.getMessage());
        } catch (Exception e) {
            throw new NexosisClientException("Error while making HTTP Request: " + e.getMessage());
        }
    }

    URI prepareURI(String path, List<NameValuePair> parameters) throws URISyntaxException {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        URIBuilder builder = null;

        if (parameters == null || parameters.size() == 0) {
            builder = new URIBuilder(endpoint + path);
        } else {
            builder = new URIBuilder(endpoint + path).addParameters(parameters);
        }

        return builder.build();
    }

    private void ProcessFailureResponse(HttpResponse response) throws NexosisClientException {
        String errorResponseContent = null;
        try {
            errorResponseContent = EntityUtils.toString(response.getEntity());
            ErrorResponse errorResponse = mapper.readValue(errorResponseContent, ErrorResponse.class);

            if (errorResponse != null)
                throw new NexosisClientException("API Error: " + errorResponse.getStatusCode() + " - " + errorResponse.getMessage(), errorResponse);
            else
                throw new NexosisClientException("API Error: " + errorResponse.getStatusCode() + " - no details provided.", response.getStatusLine());
        } catch (IOException ioe) {
            NexosisClientException nce = new NexosisClientException("Error processing error response content.", ioe);

            try {
                ErrorResponse e = new ErrorResponse();
                e.setAdditionalProperty("Error Content", errorResponseContent );
                e.setStatusCode(response.getStatusLine().getStatusCode());
                nce.setStatusCode(response.getStatusLine().getStatusCode());
            } catch (Exception ex) {}

            throw nce;
        }
    }

    public static boolean isSuccessStatusCode(int statusCode) {
        switch (statusCode) {
            case HttpStatus.SC_OK:
            case HttpStatus.SC_CREATED:
            case HttpStatus.SC_ACCEPTED:
            case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:
            case HttpStatus.SC_NO_CONTENT:
            case HttpStatus.SC_RESET_CONTENT:
            case HttpStatus.SC_PARTIAL_CONTENT:
            case HttpStatus.SC_MULTI_STATUS:
                return true;
            default:
                return false;
        }
    }
}
