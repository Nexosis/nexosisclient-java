package com.nexosis.model;

import java.util.List;

public class ContestResponse extends SessionResponse {
    /// The metric used to determine the champion algorithm that should be used for this session
    private String championMetric;
    /// The contestant selected as the champion
    private ChampionContestant champion;
    /// Other contestants that were considered in the selection process
    private List<ChampionContestant> contestants;

    public String getChampionMetric() {
        return this.championMetric;
    }

    public void setChampionMetric(String championMetric) {
        this.championMetric = championMetric;
    }

    public ChampionContestant getChampion() {
        return champion;
    }

    public void setChampion(ChampionContestant champion) {
        this.champion = champion;
    }

    public List<ChampionContestant> getContestants() {
        return contestants;
    }

    public void setContestants(List<ChampionContestant> contestants) {
        this.contestants = contestants;
    }

}
