package com.nexosis.model;

import java.util.Map;

public class ContestantResponse extends ChampionContestant {
    private Map<String, String>[] data;

    /// The test data used when scoring the contestant
    public Map<String, String>[] getData() {
        return this.data;
    }

    public void setData(Map<String, String>[]  data) {
        this.data = data;
    }
}
