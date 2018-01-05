package com.nexosis.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Search criteria for determining the data to remove from a dataset
 */
public class DataSetRemoveCriteria {
    private String name;
    private DateTime startDate;
    private DateTime endDate;
    private EnumSet<DataSetDeleteOptions> option;

    public DataSetRemoveCriteria(String name)
    {
        this.setName(name);
    }

    /**
     * Gets the name of the dataset
     *
     * @return The name of the dataset
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the dataset
     *
     * @param name The name of the dataset
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get the start date
     *
     * @return The start date (for Time Series DataSets) after which data should be removed
     */
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * Set the start date
     *
     * @param startDate The start date (for Time Series DataSets) after which data should be removed
     */
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the enddate
     *
     * @return The end date (for Time Series DataSets) before whcih data should be removed
     */
    public DateTime getEndDate() {
        return endDate;
    }

    /**
     * Set the enddate
     *
     * @param endDate The end date (for Time Series DataSets) before whcih data should be removed
     */
    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Set Delete Options
     *
     * @return Options for deleting additional data related to the DataSet
     */
    public EnumSet<DataSetDeleteOptions> getOption() {
        return option;
    }

    /**
     * Get Delete Options
     *
     * @param option Options for deleting additional data related to the DataSet
     */
    public void setOption(EnumSet<DataSetDeleteOptions> option) {
        this.option = option;
    }

    /**
     *
     * @return
     */
    public Map<String,Object> toParameters() {
        Map<String,Object> parameters = new HashMap<>();

        if (startDate != null) {
            parameters.put("startDate", startDate.toDateTimeISO().toString());
        }

        if (endDate != null) {
            parameters.put("endDate", endDate.toDateTimeISO().toString());
        }

        if (option != null) {
            ArrayList<String> set = new ArrayList<>();
            if (option.contains(DataSetDeleteOptions.CASCADE_MODEL))
                set.add(DataSetDeleteOptions.CASCADE_MODEL.value());
            if (option.contains(DataSetDeleteOptions.CASCADE_SESSION))
                set.add(DataSetDeleteOptions.CASCADE_SESSION.value());
            if (option.contains(DataSetDeleteOptions.CASCADE_VIEW))
                set.add(DataSetDeleteOptions.CASCADE_VIEW.value());

            if (set.size() > 0) {
                parameters.put("cascade",set);
            }
        }

        return parameters;
    }
}
