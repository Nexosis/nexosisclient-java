package com.nexosis.model;

import com.fasterxml.jackson.annotation.*;
import com.sun.tools.internal.ws.wsdl.document.Import;
import org.joda.time.DateTime;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "importId",
        "type",
        "status",
        "dataSetName",
        "parameters",
        "requestedDate",
        "statusHistory",
        "messages",
        "columns",
        "links"
})
// JSON Name Result
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImportDetail.class)
})
public class ImportDetail {
    @JsonProperty("importId")
    private UUID ImportId;

    /**
     * The type of import
     *
     * @see ImportType
     */
    @JsonProperty("type")
    private ImportType Type;

    /**
     * The current status of the import
     *
     * @see SessionStatus
     */
    @JsonProperty("status")
    private SessionStatus Status;

    /**
     * The DataSet into which the data was imported
     */
    @JsonProperty("dataSetName")
    private String DataSetName;

    /**
     * Additional data used as part of configuring the import
     */
    @JsonProperty("parameters")
    private Map<String, String> Parameters = new HashMap<String, String>();

    /**
     * The date and time that the import was initially requested
     */
    @JsonProperty("requestedDate")
    private DateTime RequestedDate;

    /**
     * The history of status changes on the import
     */
    @JsonProperty("statusHistory")
    private List<SessionStatusHistory> StatusHistory = new ArrayList<SessionStatusHistory>();

    /**
     * Messages to the user about the import
     */
    @JsonProperty("messages")
    private List<StatusMessage> Messages = new ArrayList<StatusMessage>();

    /**
     * Metadata about submitted columns
     */
    @JsonProperty("columns")
    private Columns Columns = new Columns();

    @JsonProperty("links")
    private List<Link> Links = new ArrayList<Link>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    /**
     * The unique identifier of the Import
     */
    @JsonProperty("importId")
    public UUID getImportId() {
        return ImportId;
    }

    @JsonProperty("importId")
    public void setImportId(UUID importId) {
        ImportId = importId;
    }

    @JsonProperty("type")
    public ImportType getType() {
        return Type;
    }

    @JsonProperty("type")
    public void setType(ImportType type) {
        Type = type;
    }

    @JsonProperty("status")
    public SessionStatus getStatus() {
        return Status;
    }

    @JsonProperty("status")
    public void setStatus(SessionStatus status) {
        Status = status;
    }

    @JsonProperty("dataSetName")
    public String getDataSetName() {
        return DataSetName;
    }

    @JsonProperty("dataSetName")
    public void setDataSetName(String dataSetName) {
        DataSetName = dataSetName;
    }

    @JsonProperty("parameters")
    public Map<String, String> getParameters() {
        return Parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(Map<String, String> parameters) {
        Parameters = parameters;
    }

    @JsonProperty("requestedDate")
    public DateTime getRequestedDate() {
        return RequestedDate;
    }

    @JsonProperty("requestedDate")
    public void setRequestedDate(DateTime requestedDate) {
        RequestedDate = requestedDate;
    }

    @JsonProperty("statusHistory")
    public List<SessionStatusHistory> getStatusHistory() {
        return StatusHistory;
    }

    @JsonProperty("statusHistory")
    public void setStatusHistory(List<SessionStatusHistory> statusHistory) {
        StatusHistory = statusHistory;
    }

    @JsonProperty("messages")
    public List<StatusMessage> getMessages() {
        return Messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<StatusMessage> messages) {
        Messages = messages;
    }

    @JsonProperty("columns")
    public Columns getColumns() {
        return Columns;
    }

    @JsonProperty("columns")
    public void setColumns(Columns columns) {
        Columns = columns;
    }

    @JsonProperty("links")
    public List<Link> getLinks() { return Links;}

    @JsonProperty("links")
    public void setLinks(List<Link> links){ Links = links;}

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

