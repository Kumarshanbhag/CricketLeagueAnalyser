package com.cricketleagueanalyser.dao;

import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.cricketleagueanalyser.model.IPLWktsCSV;

public class IPLDAO {
    public double economy;
    public String player;
    public double average;
    public double sixs;
    public double fours;
    public double strikingRates;
    public double runs;

    public IPLDAO(IPLRunsCSV iplCSV) {
        player = iplCSV.player;
        average = iplCSV.average;
        sixs=iplCSV.sixs;
        fours=iplCSV.fours;
        strikingRates=iplCSV.strikingRates;
        runs=iplCSV.runs;
    }
    public IPLDAO(IPLWktsCSV iplCSV) {
        player = iplCSV.player;
        average = iplCSV.average;
        strikingRates = iplCSV.strikeRate;
        economy = iplCSV.economy;
    }
}
