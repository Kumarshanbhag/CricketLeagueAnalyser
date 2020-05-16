package com.cricketleagueanalyser.adapter;

import com.cricketleagueanalyser.dao.IPLDAO;
import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.cricketleagueanalyser.model.IPLWktsCSV;

import java.util.List;

public class IPLBattingAdapter extends IPLAdapter {
    @Override
    public List loadIPLData(String[] csvFilePath) {
        List<IPLDAO> battingList = super.loadIPLData(IPLRunsCSV.class, csvFilePath[0]);
        if (csvFilePath.length > 1) {
            List<IPLDAO> bowlingList = super.loadIPLData(IPLWktsCSV.class, csvFilePath[1]);
            battingList = mergeList(battingList, bowlingList);
        }
        return battingList;
    }

    private List<IPLDAO> mergeList(List<IPLDAO> battingList, List<IPLDAO> bowlingList) {
        for (int bat = 0; bat < battingList.size(); bat++) {
            for (int ball = 0; ball < bowlingList.size(); ball++) {
                if (battingList.get(bat).player.equals(bowlingList.get(ball).player)) {
                    battingList.get(bat).ballAverage = bowlingList.get(ball).ballAverage;
                    battingList.get(bat).ballStrikingRates = bowlingList.get(ball).ballStrikingRates;
                    battingList.get(bat).economy = bowlingList.get(ball).economy;
                    battingList.get(bat).fourWicket = bowlingList.get(ball).fourWicket;
                    battingList.get(bat).fiveWicket = bowlingList.get(ball).fiveWicket;
                    battingList.get(bat).wickets = bowlingList.get(ball).wickets;
                    bowlingList.remove(ball);
                    ball--;
                }
            }
        }
        battingList.addAll(bowlingList);
        return battingList;
    }
}