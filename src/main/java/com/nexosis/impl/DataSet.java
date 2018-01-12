package com.nexosis.impl;

import com.nexosis.model.*;

import java.io.InputStream;

public class DataSet {

    /**
      * Create a Data Source from a DataSetDetail
      * <p>
      * @param name      The name of the DataSet to be created
      * @param data      The data
      * @return An IDataSetSource to be used for creating the data set
      */
    public static IDataSetSource From(String name, DataSetDetail data)
    {
        return new DataSetDetailSource(name, data);
    }

    /**
      * Create a Data Source from a Csv stream
      *
      * @param name      The name of the DataSet to be created
      * @param reader    The stream containing the data
      */
    public static IDataSetSource From(String name, InputStream reader)
    {
        return new DataSetStreamSource(name, reader);
    }

    /**
     * Create a DataSetDataQuery with criteria for getting data from a DataSet
     *
     * @param query     Additional query criteria
     * @param name      The DataSet from which the data should be retrieved
     * @return          A DataSetDataQuery defining the query critera
     */
    public static DataSetDataQuery Get(String name, DataSetDataQuery query)
    {
        DataSetDataQuery queryObject = query;

        if (queryObject == null) {
            queryObject = new DataSetDataQuery(name);
        }

        queryObject.setName(name);
        return queryObject;
    }
}
