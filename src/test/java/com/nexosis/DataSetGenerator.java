package com.nexosis;

import com.nexosis.model.DataSetDetail;
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
}