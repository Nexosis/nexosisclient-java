package com.nexosis.model;

/**
 *  Put data into a DataSet from a DataSetDetail
 */
public class DataSetDetailSource implements IDataSetSource {
    private DataSetDetail data;
    private String name;

    public DataSetDetailSource(String name, DataSetDetail data) {
        this.setData(data);
        this.setName(name);
    }

    /**
     * Get the data
     *
     * @return  The data
     */
    public DataSetDetail getData() {
        return data;
    }

    /**
     * Set the Data
     * @param data  The data
     */
    public void setData(DataSetDetail data) {
        this.data = data;
    }

    /**
     * Get the DataSet name
     * @return The DataSet name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the DataSet name
     * @param name The DataSet name
     */
    public void setName(String name) {
        this.name = name;
    }
}
