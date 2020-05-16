package com.cricketleagueanalyser.analyser;

import com.cricketleagueanalyser.adapter.IPLAdapterFactory;
import com.cricketleagueanalyser.dao.IPLDAO;
import com.cricketleagueanalyser.enums.BatOrBall;
import com.cricketleagueanalyser.exception.CricketLeagueAnalyserException;
import com.cricketleagueanalyser.enums.SortByField;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CricketLeagueAnalyser {
    private Map<SortByField, Comparator<IPLDAO>> sortMap;

    public CricketLeagueAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortByField.AVG, Comparator.comparing(iplData -> iplData.average));
        this.sortMap.put(SortByField.STRIKINGRATES, Comparator.comparing(iplData -> iplData.strikingRates));
        this.sortMap.put(SortByField.SIXFOURS, Comparator.comparing(iplData -> iplData.sixs + iplData.fours));

        Comparator<IPLDAO> sixFourWithAvg = Comparator.comparing(iplData -> iplData.sixs + iplData.fours);
        this.sortMap.put(SortByField.SIXFOURSAVG, sixFourWithAvg.thenComparing(iplData -> iplData.strikingRates));

        this.sortMap.put(SortByField.AVGWITHSTRIKERATE, Comparator.comparing(iplData -> (iplData.average + iplData.strikingRates) > 100));

        Comparator<IPLDAO> maxRuns = Comparator.comparing(iplData -> iplData.runs);
        this.sortMap.put(SortByField.MAXRUNSWITHBESTAVERAGE, maxRuns.thenComparing(iplData -> iplData.average));

        this.sortMap.put(SortByField.BOWLINGAVG, Comparator.comparing(iplData -> iplData.ballAverage));
        this.sortMap.put(SortByField.BOWLINGSTRIKINGRATES, Comparator.comparing(iplData -> iplData.ballStrikingRates));
        this.sortMap.put(SortByField.ECONOMY, Comparator.comparing(iplData -> iplData.economy));

        Comparator<IPLDAO> fourWfiveWWithStrikingRates = Comparator.comparing(iplData -> iplData.fourWicket + iplData.fiveWicket);
        this.sortMap.put(SortByField.FOURFIVEWICKETWITHSTRIKERATES, fourWfiveWWithStrikingRates.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLDAO> wicket = Comparator.comparing(iplData -> iplData.wickets);
        this.sortMap.put(SortByField.WICKETWITHAVERAGE, wicket.thenComparing(iplData -> iplData.strikingRates));

        Comparator<IPLDAO> battingAverage = Comparator.comparing(iplData -> iplData.average);
        this.sortMap.put(SortByField.ALLROUNDERAVG, battingAverage.thenComparing(iplData -> iplData.ballAverage));

        this.sortMap.put(SortByField.MOSTRUNSWITHWICKETS, Comparator.comparing(iplData -> iplData.runs * iplData.wickets));
    }

    public List analyseIPLData(BatOrBall gameFact, String... csvFilePath) {
        return new IPLAdapterFactory().loadIPLData(gameFact, csvFilePath);
    }

    public String sortListAndConvertJson(SortByField sortField, List iplCricketersList, String... sortOrder) {
        if(iplCricketersList == null || iplCricketersList.size() == 0)
            throw new CricketLeagueAnalyserException("No Cricketers Data Found", CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA);
        List cricketersList = (List) iplCricketersList.stream()
                .sorted(this.sortMap.get(sortField).reversed())
                .collect(Collectors.toList());
        if(sortOrder.length == 1) {
            Collections.reverse(cricketersList);
        }
        return new Gson().toJson(cricketersList);
    }

    public List<IPLDAO> removeZeroValue(List<IPLDAO> cricktersList) {
        for(int i =0; i < cricktersList.size(); i++) {
            if(cricktersList.get(i).ballAverage == 0) {
                cricktersList.remove(i);
                i--;
            }
        }
        return cricktersList;
    }
}
