package com.cricketleagueanalyser.analyser;

import com.cricketleagueanalyser.loader.IPLCSVLoader;
import com.cricketleagueanalyser.exception.CricketLeagueAnalyserException;
import com.cricketleagueanalyser.enums.SortByField;
import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CricketLeagueAnalyser {
    private Map <SortByField, Comparator <IPLRunsCSV>> sortMap;

    public CricketLeagueAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortByField.AVG, Comparator.comparing(iplData -> iplData.average));
        this.sortMap.put(SortByField.STRIKINGRATES, Comparator.comparing(iplData -> iplData.strikingRates));
        this.sortMap.put(SortByField.SIXFOURS, Comparator.comparing(iplData -> iplData.sixs + iplData.fours));

        Comparator<IPLRunsCSV> sixFourWithAvg = Comparator.comparing(iplData -> iplData.sixs + iplData.fours);
        this.sortMap.put(SortByField.SIXFOURSAVG, sixFourWithAvg.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLRunsCSV> avgWithStrikingRates = Comparator.comparing(iplData -> iplData.average);
        this.sortMap.put(SortByField.AVGWITHSTRIKERATE, avgWithStrikingRates.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLRunsCSV> maxRunsWithBestAverages = Comparator.comparing(iplData -> iplData.runs);
        this.sortMap.put(SortByField.MAXRUNS_WITH_BESTAVERAGE, maxRunsWithBestAverages.thenComparing(iplData -> iplData.average));

    }

    public String analyseIPLData(SortByField sortByField, String csvFilePath) {
        List iplCricketersList = new IPLCSVLoader().loadIPLData(csvFilePath);
        List sortedList = sortList(sortByField, iplCricketersList);
        String sortedStateCensus = new Gson().toJson(sortedList);
        return sortedStateCensus;
    }

    public List sortList(SortByField sortField, List iplCricketersList) {
        if(iplCricketersList == null || iplCricketersList.size() == 0)
            throw new CricketLeagueAnalyserException("No Cricketers Data Found", CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA);
        return (List) iplCricketersList.stream()
                .sorted(this.sortMap.get(sortField).reversed())
                .collect(Collectors.toList());
    }
}
