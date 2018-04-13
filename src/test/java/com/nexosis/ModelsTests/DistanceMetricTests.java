package com.nexosis.ModelsTests;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.junit.Assert;
import org.junit.Test;
import com.nexosis.model.DistanceMetricResponse;
import com.nexosis.model.DistanceMetric;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class DistanceMetricTests {

    String testString = "{\"metrics\":{\"percentAnomalies\":0.101123594},\"data\":[{\"anomaly\":\"0.0487072268545709\",\"Ash\":\"2\",\"Hue\":\"0.93\",\"Alcohol\":\"12\",\"ODRatio\":\"3.05\",\"Proline\":\"564\",\"Magnesium\":\"87\",\"MalicAcid\":\"3.43\",\"Aclalinity\":\"19\",\"Flavanoids\":\"1.64\",\"TotalPhenols\":\"2\",\"ColorIntensity\":\"1.28\",\"Proanthocyanins\":\"1.87\",\"NonFlavanoidPhenols\":\"0.37\",\"mahalanobis_distance\":\"143.312589889491\"},{\"anomaly\":\"0.000317797613019206\",\"Ash\":\"2.28\",\"Hue\":\"1.25\",\"Alcohol\":\"12.33\",\"ODRatio\":\"1.67\",\"Proline\":\"680\",\"Magnesium\":\"101\",\"MalicAcid\":\"1.1\",\"Aclalinity\":\"16\",\"Flavanoids\":\"1.09\",\"TotalPhenols\":\"2.05\",\"ColorIntensity\":\"3.27\",\"Proanthocyanins\":\"0.41\",\"NonFlavanoidPhenols\":\"0.63\",\"mahalanobis_distance\":\"156.112291933161\"}],\"pageNumber\":0,\"totalPages\":89,\"pageSize\":2,\"totalCount\":178,\"sessionId\":\"0162b0ff-7adb-4c38-bca0-89794b759f14\",\"type\":\"model\",\"status\":\"completed\",\"predictionDomain\":\"anomalies\",\"supportsFeatureImportance\":false,\"availablePredictionIntervals\":[],\"modelId\":\"5c874326-6b38-4c0e-86ef-c0b50689a1a1\",\"requestedDate\":\"2018-04-10T19:19:15.894879+00:00\",\"statusHistory\":[{\"date\":\"2018-04-10T19:19:15.894879+00:00\",\"status\":\"requested\"},{\"date\":\"2018-04-10T19:19:16.07002+00:00\",\"status\":\"started\"},{\"date\":\"2018-04-10T19:19:33.1405011+00:00\",\"status\":\"completed\"}],\"extraParameters\":{\"containsAnomalies\":true},\"messages\":[{\"severity\":\"informational\",\"message\":\"178 observations were found in the dataset.\"}],\"name\":\"Anomalies on Wine\",\"dataSourceName\":\"Wine\",\"dataSetName\":\"Wine\",\"targetColumn\":\"anomaly\",\"algorithm\":{\"name\":\"Isolation Forest\",\"description\":\"Isolation Forest Outlier Detection\",\"key\":\"\"},\"isEstimate\":false,\"links\":[{\"rel\":\"data\",\"href\":\"https://api.uat.nexosisdev.com/v1/data/Wine\"},{\"rel\":\"first\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b0ff-7adb-4c38-bca0-89794b759f14/results/mahalanobisdistances?pageSize=2&page=0\"},{\"rel\":\"next\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b0ff-7adb-4c38-bca0-89794b759f14/results/mahalanobisdistances?pageSize=2&page=1\"},{\"rel\":\"last\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b0ff-7adb-4c38-bca0-89794b759f14/results/mahalanobisdistances?pageSize=2&page=88\"},{\"rel\":\"self\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b0ff-7adb-4c38-bca0-89794b759f14/results/mahalanobisdistances?pageSize=2\"},{\"rel\":\"model\",\"href\":\"https://api.uat.nexosisdev.com/v1/models/5c874326-6b38-4c0e-86ef-c0b50689a1a1\"},{\"rel\":\"anomalyScores\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b0ff-7adb-4c38-bca0-89794b759f14/results/anomalyscores\"},{\"rel\":\"mahalanobisDistances\",\"href\":\"https://api.uat.nexosisdev.com/v1/sessions/0162b0ff-7adb-4c38-bca0-89794b759f14/results/mahalanobisdistances\"},{\"rel\":\"vocabularies\",\"href\":\"https://api.uat.nexosisdev.com/v1/vocabulary?createdFromSession=0162b0ff-7adb-4c38-bca0-89794b759f14\"}]}";
    
    @Test
    public void canParseDistanceResults() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        DistanceMetricResponse response = mapper.readValue(testString, TypeFactory.defaultInstance().constructType(DistanceMetricResponse.class));
        Assert.assertTrue(response.getData() != null);
        Assert.assertEquals(0.0487072268545709, response.getData()[0].getAnomaly(), 0.00001);
        Assert.assertTrue(response.getData()[0].getData().get("Ash").equals("2"));
    }
}
