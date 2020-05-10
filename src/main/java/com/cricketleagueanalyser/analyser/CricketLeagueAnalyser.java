package com.cricketleagueanalyser.analyser;

import com.cricketleagueanalyser.adapter.IPLAdapterFactory;
import com.cricketleagueanalyser.dao.IPLDAO;
import com.cricketleagueanalyser.exception.CricketLeagueAnalyserException;
import com.cricketleagueanalyser.enums.SortByField;
import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CricketLeagueAnalyser {
    private Map<SortByField, Comparator<IPLDAO>> sortMap;

    public enum BatOrBall {
        BATTING, FIELDING, BALLING
    }

    public CricketLeagueAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortByField.AVG, Comparator.comparing(iplData -> iplData.average));
        this.sortMap.put(SortByField.STRIKINGRATES, Comparator.comparing(iplData -> iplData.strikingRates));
        this.sortMap.put(SortByField.SIXFOURS, Comparator.comparing(iplData -> iplData.sixs + iplData.fours));

        Comparator<IPLDAO> sixFourWithAvg = Comparator.comparing(iplData -> iplData.sixs + iplData.fours);
        this.sortMap.put(SortByField.SIXFOURSAVG, sixFourWithAvg.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLDAO> avgWithStrikingRates = Comparator.comparing(iplData -> iplData.average);
        this.sortMap.put(SortByField.AVGWITHSTRIKERATE, avgWithStrikingRates.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLDAO> maxRunsWithBestAverages = Comparator.comparing(iplData -> iplData.runs);
        this.sortMap.put(SortByField.MAXRUNSWITHBESTAVERAGE, maxRunsWithBestAverages.thenComparing(iplData -> iplData.average));

        this.sortMap.put(SortByField.ECONOMY, Comparator.comparing(iplData -> iplData.economy));

        Comparator<IPLDAO> fourWfiveWWithStrikingRates = Comparator.comparing(iplData -> iplData.fourWicket + iplData.fiveWicket);
        this.sortMap.put(SortByField.FOURFIVEWICKETWITHSTRIKERATES, fourWfiveWWithStrikingRates.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLDAO> wicket = Comparator.comparing(iplData -> iplData.wickets);
        this.sortMap.put(SortByField.WICKETWITHAVERAGE, wicket.thenComparing(iplData -> iplData.strikingRates));
    }

    public List analyseIPLData(BatOrBall gameFact, String... csvFilePath) {
        return new IPLAdapterFactory().loadIPLData(gameFact, csvFilePath);
    }

    public String sortListAndConvertJson(SortByField sortField, List iplCricketersList) {
        if(iplCricketersList == null || iplCricketersList.size() == 0)
            throw new CricketLeagueAnalyserException("No Cricketers Data Found", CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA);
        return new Gson().toJson(iplCricketersList.stream()
                .sorted(this.sortMap.get(sortField).reversed())
                .collect(Collectors.toList()));
    }
}
