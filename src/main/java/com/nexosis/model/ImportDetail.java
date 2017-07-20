package com.nexosis.model;
import java.util.UUID;

public class ImportDetail
    {
        private UUID ImportId;

       /**
        * The <see cref="ImportType">type</see> of import
        */
        private ImportType Type;

       /**
        * The current <see cref="Status">status</see> of the import
        */
        private Status Status;

       /**
        * The DataSet into which the data was imported
        */
        private String DataSetName;

       /**
        * Additional data used as part of configuring the import
        */
        private Dictionary<String, String> Parameters; = new Dictionary<String, String>();

       /**
        * The date and time that the import was initially requested
        */
        private DateTimeOffset RequestedDate;

       /**
        * The history of status changes on the import
        */
        private List<StatusChange> StatusHistory; = new List<StatusChange>();

       /**
        * Messages to the user about the import
        */
        private List<StatusMessage> Messages; = new List<StatusMessage>();

       /**
        * Metadata about which
        */
        private Dictionary<String, ColumnMetadata> Columns;

        /**
         * The unique identifier of the Import
         */
        public UUID getImportId() {
            return ImportId;
        }

        public void setImportId(UUID importId) {
            ImportId = importId;
        }

        public ImportType getType() {
            return Type;
        }

        public void setType(ImportType type) {
            Type = type;
        }

        public Status getStatus() {
            return Status;
        }

        public void setStatus(Status status) {
            Status = status;
        }

        public String getDataSetName() {
            return DataSetName;
        }

        public void setDataSetName(String dataSetName) {
            DataSetName = dataSetName;
        }

        public Dictionary<String, String> getParameters() {
            return Parameters;
        }

        public void setParameters(Dictionary<String, String> parameters) {
            Parameters = parameters;
        }

        public DateTimeOffset getRequestedDate() {
            return RequestedDate;
        }

        public void setRequestedDate(DateTimeOffset requestedDate) {
            RequestedDate = requestedDate;
        }

        public List<StatusChange> getStatusHistory() {
            return StatusHistory;
        }

        public void setStatusHistory(List<StatusChange> statusHistory) {
            StatusHistory = statusHistory;
        }

        public List<StatusMessage> getMessages() {
            return Messages;
        }

        public void setMessages(List<StatusMessage> messages) {
            Messages = messages;
        }

        public Dictionary<String, ColumnMetadata> getColumns() {
            return Columns;
        }

        public void setColumns(Dictionary<String, ColumnMetadata> columns) {
            Columns = columns;
        } =
        new Dictionary<String, ColumnMetadata>(StringComparer.OrdinalIgnoreCase);
    }

