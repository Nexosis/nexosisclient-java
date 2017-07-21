package com.nexosis.model;
import org.joda.time.DateTime;
import java.util.*;

public class ImportDetail
    {
        private UUID ImportId;

       /**
        * The type of import
        * @see ImportType
        */
        private ImportType Type;

       /**
        * The current status of the import
        * @see SessionStatus
        */
        private SessionStatus Status;

       /**
        * The DataSet into which the data was imported
        */
        private String DataSetName;

       /**
        * Additional data used as part of configuring the import
        */
        private Map<String, String> Parameters = new HashMap<String, String>();

       /**
        * The date and time that the import was initially requested
        */
        private DateTime RequestedDate;

       /**
        * The history of status changes on the import
        */
        private List<SessionStatusHistory> StatusHistory = new ArrayList<SessionStatusHistory>();

       /**
        * Messages to the user about the import
        */
        private List<StatusMessage> Messages = new ArrayList<StatusMessage>();

       /**
        * Metadata about which
        */
        private Columns Columns  = new Columns();

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

        public SessionStatus getStatus() {
            return Status;
        }

        public void setStatus(SessionStatus status) {
            Status = status;
        }

        public String getDataSetName() {
            return DataSetName;
        }

        public void setDataSetName(String dataSetName) {
            DataSetName = dataSetName;
        }

        public Map<String, String> getParameters() {
            return Parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            Parameters = parameters;
        }

        public DateTime getRequestedDate() {
            return RequestedDate;
        }

        public void setRequestedDate(DateTime requestedDate) {
            RequestedDate = requestedDate;
        }

        public List<SessionStatusHistory> getStatusHistory() {
            return StatusHistory;
        }

        public void setStatusHistory(List<SessionStatusHistory> statusHistory) {
            StatusHistory = statusHistory;
        }

        public List<StatusMessage> getMessages() {
            return Messages;
        }

        public void setMessages(List<StatusMessage> messages) {
            Messages = messages;
        }

        public Columns getColumns() {
            return Columns;
        }

        public void setColumns(Columns columns) {
            Columns = columns;
        }
    }

