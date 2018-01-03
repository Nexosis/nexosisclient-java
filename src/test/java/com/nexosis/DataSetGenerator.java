package com.nexosis;

import com.nexosis.model.*;
import org.joda.time.DateTime;

import java.util.*;

public class DataSetGenerator {
    public static DataSetDetail Run(DateTime startDate, DateTime endDate, String targetKey) {
        Random rand = new Random();
        DataSetDetail data = new DataSetDetail();

        List<Map<String, String>> rows = new ArrayList<>();
        for (DateTime timeStamp = startDate; timeStamp.isBefore(endDate); timeStamp = timeStamp.plusDays(1)) {
            Map<String, String> row = new HashMap<>();
            row.put("timestamp", timeStamp.toDateTimeISO().toString());
            row.put(targetKey, Double.toString(rand.nextDouble() * 100));
            rows.add(row);
        }

        data.setData(rows);
        return data;

    }

    public static DataSetDetail Run(int rowCount, int columns, String targetKey)
    {
        Random rand = new Random();
        DataSetDetail ds = new DataSetDetail();
        List<Map<String, String>> rows = new ArrayList<>(rowCount);
        Columns prop = new Columns();

        // Gen columns
        List<String> columnNames = new ArrayList<>();
        for (int i=0; i < columns; i++) {
            // Create Column Metadata
            prop.setColumnMetadata(Integer.toString(i), DataType.NUMERIC, DataRole.FEATURE);
            ds.setColumns(prop);
            // Add Column name to list
            columnNames.add(Integer.toString(i));
        }
        // add target key if not null
        if (targetKey != null) {
            columnNames.add(targetKey);
            prop.setColumnMetadata(targetKey, DataType.NUMERIC, DataRole.TARGET);
        }

        // Load up data
        for (int i = 0; i < rowCount; i++) {
            Map<String, String> row = new HashMap<>();

            for (String name: columnNames) {
                row.put(name, Double.toString(rand.nextDouble() * 100));
            }
            rows.add(row);
        }

        ds.setData(rows);
        ds.setColumns(prop);

        return ds;
    }
}



