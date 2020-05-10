package com.cricketleagueanalyser.adapter;

import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.cricketleagueanalyser.model.IPLWktsCSV;

import java.util.List;

public class IPLBattingAdapter extends IPLAdapter {
    @Override
    public List loadIPLData(String[] csvFilePath) {
        List cricketersList = super.loadIPLData(IPLRunsCSV.class, csvFilePath[0]);
        if(csvFilePath.length > 1) {
            List list1 = super.loadIPLData(IPLWktsCSV.class, csvFilePath[1]);
            cricketersList.addAll(list1);
        }
        return cricketersList;
    }
}