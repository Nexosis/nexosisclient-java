package com.nexosis.impl;

import com.nexosis.model.*;
import org.joda.time.DateTime;

public class Sessions {

    public static ModelSessionRequest TrainModel(String dataSourceName, PredictionDomain domain,
                                                 String targetColumn, ModelSessionRequest options) {
        ModelSessionRequest request = options;
        if (request == null)
            request = new ModelSessionRequest();

        request.setDataSourceName(dataSourceName);
        if (targetColumn != null) {
            request.setTargetColumn(targetColumn);
        }

        request.setPredictionDomain(domain);
        return request;
    }

    public static ForecastSessionRequest Forecast(String dataSourceName, DateTime startDate, DateTime endDate,
                                                  ResultInterval resultInterval, String targetColumn, ForecastSessionRequest options) {
        ForecastSessionRequest request = options;
        if (request == null)
            request = new ForecastSessionRequest();

        request.setDataSourceName(dataSourceName);
        if (targetColumn != null) {
            request.setTargetColumn(targetColumn);
        }
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setResultInterval(resultInterval);
        return request;
    }

    public static ImpactSessionRequest Impact(String dataSourceName, DateTime startDate,
                                              DateTime endDate, ResultInterval resultInterval, String eventName, String targetColumn,
                                              ImpactSessionRequest options) {

        ImpactSessionRequest request = options;
        if (request == null)
            request = new ImpactSessionRequest();

        request.setDataSourceName(dataSourceName);

        if (targetColumn != null) {
            request.setTargetColumn(targetColumn);
        }
        request.setEventName(eventName);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setResultInterval(resultInterval);
        return request;
    }

    public static SessionQuery Where(final String dataSourceName, DateTime requestedAfterDate, DateTime requestedBeforeDate, SessionQuery query) {
        SessionQuery theQuery = query;
        if (theQuery == null)
            theQuery = new SessionQuery() {{
                setDataSourceName(dataSourceName);
            }};

        theQuery.setDataSourceName(dataSourceName);
        if (requestedAfterDate != null) {
            theQuery.setRequestedAfterDate(requestedAfterDate);
        }
        if (requestedBeforeDate != null) {
            theQuery.setRequestedBeforeDate(requestedBeforeDate);
        }

        return theQuery;
    }
}
