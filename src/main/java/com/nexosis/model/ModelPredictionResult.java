package com.nexosis.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "data",
            "messages",
            "modelId",
            "predictionDomain",
            "dataSourceName",
            "createdDate",
            "algorithm",
            "columns",
            "metrics"
    })
    public class ModelPredictionResult extends ModelSummary implements Serializable {

        @JsonProperty("data")
        private  List<Map<String, String>> data = null;
        @JsonProperty("messages")
        private List<StatusMessage> messages = null;
        @JsonProperty("modelId")

        private final static long serialVersionUID = -7590856468357416865L;

        @JsonProperty("data")
        public  List<Map<String, String>>  getData() {
            return data;
        }

        @JsonProperty("data")
        public void setData( List<Map<String, String>> data) {
            this.data = data;
        }

        @JsonProperty("messages")
        public List<StatusMessage> getMessages() {
            return messages;
        }

        @JsonProperty("messages")
        public void setMessages(List<StatusMessage> messages) {
            this.messages = messages;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).appendSuper(super.toString()).append("data", data).append("messages", messages).toString();
        }

        @Override
        public int hashCode() {
            int hash = super.hashCode();
            HashCodeBuilder cs = new HashCodeBuilder();
            return new HashCodeBuilder().appendSuper(super.hashCode()).append(data).append(messages).toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof ModelPredictionResult) == false) {
                return false;
            }
            ModelPredictionResult rhs = ((ModelPredictionResult) other);
            return new EqualsBuilder().appendSuper(super.equals(other)).append(data, rhs.data).append(messages, rhs.messages).isEquals();
        }

    }

