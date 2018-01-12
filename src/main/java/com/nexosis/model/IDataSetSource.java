package com.nexosis.model;

/*
* A source of data to be used when creating a dataset.  Use the DataSet.From to get instances of me
*/
public interface IDataSetSource {

    /*
     * @return The name of the DataSet
     */
    String getName();
}
