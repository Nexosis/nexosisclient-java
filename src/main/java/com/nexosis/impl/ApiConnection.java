package com.nexosis.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.nexosis.model.ErrorResponse;
import com.nexosis.model.ReturnsCost;
import com.nexosis.model.ReturnsStatus;
import com.nexosis.model.SessionStatus;
import com.nexosis.util.Action;
import com.nexosis.util.HttpMethod;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import com.google.api.client.http.*;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.util.Map;

public class ApiConnection {

    private String key;
    private String endpoint;
    private HttpTransport httpTransport;
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
        this(endpoint, key, new NetHttpTransport());
    }

    /**
     * Internal provided for testing use only
     * <P>
     * @param key The api key from your account.
     * @param endpoint URL of Nexosis API
     * @param httpTransport HttpTransport to provide mock class for unit tests
     */
    ApiConnection(String endpoint, String key, HttpTransport httpTransport) {
        this.endpoint = endpoint;
        this.httpTransport = httpTransport;
        this.key = key;
        mapper.registerModule(new JodaModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void get(String path, Map<String,Object>parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output) throws NexosisClientException {
        get(path, parameters, httpMessageTransformer, output, null);
    }

    public void get(String path, Map<String,Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output, String acceptType) throws NexosisClientException {
        try {

            if (StringUtils.isEmpty(acceptType)) {
                acceptType = "application/json";
            }

            GenericUrl uri = prepareURI(path, parameters);

            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = requestFactory.buildGetRequest(uri);
            request.setHeaders(new HttpHeaders().setAccept(acceptType));
            makeRequest(request, httpMessageTransformer, output);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }


    public <T> T get(Class<T> type, String path, Map<String,Object>  parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output, String acceptType) throws NexosisClientException {
        try {

            if (StringUtils.isEmpty(acceptType)) {
                acceptType = "application/json";
            }

            GenericUrl uri = prepareURI(path, parameters);

            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = requestFactory.buildGetRequest(uri);
            request.setHeaders(new HttpHeaders().setAccept(acceptType));

            return makeRequest(type, request, httpMessageTransformer, output);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }

    public <T> T get(Class<T> type, String path, Map<String,Object>  parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output) throws NexosisClientException {
        return get(type, path, parameters, httpMessageTransformer, output, null);
    }

    public <T> T get(Class<T> type, String path, Map<String,Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return get(type, path, parameters, httpMessageTransformer, (String)null);
    }

    public <T> T get(Class<T> type, String path, Map<String,Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer, String acceptType) throws NexosisClientException {
        try {
            if (StringUtils.isEmpty(acceptType)) {
                acceptType = "application/json";
            }

            GenericUrl uri = prepareURI(path, parameters);

            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = requestFactory.buildGetRequest(uri);
            request.setHeaders(new HttpHeaders().setAccept(acceptType));

            return makeRequest(type, request, httpMessageTransformer);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }

    public <T> T head(Class<T> type, String path, Map<String,Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        String acceptType = "application/json";
        try {
            GenericUrl uri = prepareURI(path, parameters);

            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = requestFactory.buildHeadRequest(uri);
            request.setHeaders(new HttpHeaders().setAccept(acceptType));

            return makeRequest(type, request, httpMessageTransformer);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }

    public <T> T put(Class<T> type, String path, Map<String, Object> parameters, Object body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        return sendObjectContent(type, path, parameters, HttpMethod.PUT, body, httpMessageTransformer);
    }

    public <T> T put(Class<T> type, String path, Map<String, Object> parameters, InputStream body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        return sendStreamContent(type, path, parameters, HttpMethod.PUT, body, httpMessageTransformer);
    }

    public <T> T post(Class<T> type, String path, Map<String,Object> parameters, Object body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return sendObjectContent(type, path, parameters, HttpMethod.POST, body, httpMessageTransformer);
    }

    public <T> T post(Class<T> type, String path, Map<String, Object> parameters, InputStream body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        return sendStreamContent(type, path, parameters, HttpMethod.POST, body, httpMessageTransformer);
    }

    private <T> T sendObjectContent(Class<T> type, String path, Map<String, Object> parameters, HttpMethod method, Object body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        String acceptType = "application/json";
        try {

            GenericUrl uri = prepareURI(path, parameters);
            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = null;
            HttpContent contentSend = new JsonHttpContent(new JacksonFactory(), body);

            switch (method) {
                case PUT:
                    //mapper.writeValueAsString(body)
                    // TODO Transform InputStream body into Content
                    request = requestFactory.buildPutRequest(uri, contentSend);
                    break;
                case POST:
                    // TODO Transform InputStream body into Content
                    request = requestFactory.buildPostRequest(uri, contentSend);
                    break;
            }

            request.setHeaders(new HttpHeaders().setAccept(acceptType).setContentType(acceptType));

            return makeRequest(type, request, httpMessageTransformer);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }

    private <T> T sendStreamContent(Class<T> type, String path, Map<String, Object> parameters, HttpMethod method, InputStream body, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException
    {
        String acceptType = "application/json";
        try {
            GenericUrl uri = prepareURI(path, parameters);
            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = null;
            InputStreamContent inputStream = new InputStreamContent( "application/json", body);
            switch (method) {
                case PUT:
                    // TODO Transform InputStream body into Content

                    request = requestFactory.buildPutRequest(uri, inputStream);
                    break;
                case POST:
                    // TODO Transform InputStream body into Content
                    request = requestFactory.buildPostRequest(uri, inputStream);
                    break;
            }

            request.setHeaders(new HttpHeaders().setAccept(acceptType).setContentType(acceptType));
            return makeRequest(type, request, httpMessageTransformer);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }


    public void delete(String path, Map<String, Object> parameters, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        String acceptType = "application/json";
        try {
            GenericUrl uri = prepareURI(path, parameters);
            HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

            HttpRequest request = requestFactory.buildDeleteRequest(uri);
            request.setHeaders(new HttpHeaders().setAccept(acceptType));
            makeRequest(request, httpMessageTransformer);
        } catch (IOException ioe) {
            throw new NexosisClientException("Internal Error.", ioe);
        }
    }

    public <T> T makeRequest(Class<T> type, HttpRequest request, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        //HttpEntity entity = null;
        HttpResponse response;


        try {
            response = makeRequest(request, httpMessageTransformer);

            InputStream s = response.getContent();

            T object = mapper.readValue(s, type);
            if (ReturnsCost.class.isAssignableFrom(object.getClass())) {
                ((ReturnsCost) object).AssignCost(response.getHeaders());
            }

            return object;
        } catch (HttpResponseException hre) {
            throw GenerateNexosisException(hre);
        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request", ioe);
        } catch (NexosisClientException nce) {
            throw nce;
        } catch (Exception e) {
            throw new NexosisClientException("Error while making HTTP Request: " + e.getMessage(), e);
        }
    }

    private void makeRequest(HttpRequest request, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output) throws NexosisClientException {
        HttpResponse response;

        try {
            response = makeRequest(request, httpMessageTransformer);
            //if (response.isSuccessStatusCode()) {
                // Write content to stream if status is complete
                response.download(output);
            //} else {
                //ProcessFailureResponse(response);
               // throw new NotImplementedException("FIX ME!");
            //}
        } catch (HttpResponseException hre) {
            throw GenerateNexosisException(hre);
        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request: " + ioe.getMessage());
        }
    }

    private <T> T makeRequest(Class<T> type, HttpRequest request, Action<HttpRequest, HttpResponse> httpMessageTransformer, OutputStream output) throws NexosisClientException {
        HttpResponse response;
        //HttpEntity entity = null;

        try {

            response = makeRequest(request, httpMessageTransformer);

            if (response.isSuccessStatusCode()) {

                T object = (T) new ReturnsStatus();

                if (ReturnsStatus.class.isAssignableFrom(object.getClass())) {
                    ((ReturnsStatus) object).AssignStatus(response.getHeaders());
                    if (((ReturnsStatus) object).getSessionStatus().equals(SessionStatus.COMPLETED)) {
                        // Write content to stream if status is complete
                       response.download(output);
                    }
                }

                return object;
            } else {
                //ProcessFailureResponse(response);
                throw new NotImplementedException("Fix me!");
                //return null;
            }
        } catch (HttpResponseException hre) {
            throw GenerateNexosisException(hre);
        } catch (IOException ioe) {
            throw new NexosisClientException("IO Error while making HTTP Request: " + ioe.getMessage());
        }
    }

    //private void makeRequest(HttpRequest request, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
     //   HttpResponse response = makeRequest(request, httpMessageTransformer);
//
  //      if (!response.isSuccessStatusCode()) {
    //        ProcessFailureResponse(response);
    //    }
   // }

    private HttpResponse makeRequest(HttpRequest request, Action<HttpRequest, HttpResponse> httpMessageTransformer) throws NexosisClientException {
        HttpResponse response;
        request.setHeaders(new HttpHeaders()
                .set("api-key", key)
                .setUserAgent(NexosisClient.CLIENT_VERSION));

        try {
            if (httpMessageTransformer != null)
                httpMessageTransformer.invoke(request, null);

            response = request.execute();

            if (httpMessageTransformer != null)
                httpMessageTransformer.invoke(request, response);

            return response;
        } catch (HttpResponseException hre) {
            throw GenerateNexosisException(hre);
        } catch (IOException ioe) {
                throw new NexosisClientException("IO Error while making HTTP Request: " + ioe.getMessage());
        } catch (Exception e) {
            throw new NexosisClientException("Error while making HTTP Request: " + e.getMessage());
        }
    }

    GenericUrl prepareURI(String path, Map<String,Object> parameters) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        //URIBuilder builder = null;
        GenericUrl url;

        if (parameters == null || parameters.size() == 0) {
            url = new GenericUrl(endpoint + path);
        } else {
            url = new GenericUrl(endpoint + path);
            url.putAll(parameters);
        }

        return url;
    }

    private NexosisClientException GenerateNexosisException(HttpResponseException responseException) throws NexosisClientException {
        String errorResponseContent = null;
        try {
            // map the json error content to ErrorResponse object
            ErrorResponse errorResponse = mapper.readValue(responseException.getContent(), ErrorResponse.class);

            if (errorResponse != null)
                return new NexosisClientException("API Error: " + errorResponse.getStatusCode() + " - " + errorResponse.getMessage(), errorResponse);
            else
                return new NexosisClientException("API Error: " + errorResponse.getStatusCode() + " - no details provided.", responseException.getStatusCode());
        } catch (IOException ioe) {
            NexosisClientException nce = new NexosisClientException("Error processing error response content.", ioe);

            try {
                ErrorResponse e = new ErrorResponse();
                e.setAdditionalProperty("Error Content", errorResponseContent );
                e.setStatusCode(responseException.getStatusCode());
                nce.setStatusCode(responseException.getStatusCode());
            } catch (Exception ex) {}

            return nce;
        }
    }
}
