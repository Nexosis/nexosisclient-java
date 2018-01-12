package com.nexosis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dataSourceName",
        "targetColumn",
        "columns",
        "callbackUrl",
        "startDate",
        "endDate",
        "resultInterval"

})
public class ForecastSessionRequest extends TimeSeriesSessionRequest {

}
