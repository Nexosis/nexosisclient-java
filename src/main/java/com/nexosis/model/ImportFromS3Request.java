package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSetName",
        "bucket",
        "path",
        "region",
        "accessKeyId",
        "secretAccessKey",
        "columns"
})

public class ImportFromS3Request extends ImportRequest  {
    @JsonProperty("bucket")
    private String bucket;
    @JsonProperty("path")
    private String path;
    @JsonProperty("region")
    private String region;
    @JsonProperty("accessKeyId")
    private String accessKeyId;
    @JsonProperty("secretAccessKey")
    private String secretAccessKey;

    /**
     *  The bucket in which the file resides
     */
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * The path (relative to the bucket) to the file
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     *  The AWS Region in which the bucket resides.  Optional.  US-East-1 will be used by default if not specified.
     */
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * The AWS Access Key ID to use when authenticating the file request. Not necessary if the file is public.
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    /**
     * The AWS Secret Access Key to use when authenticating the file request. Not necessary if the file is public.
     */
    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}
