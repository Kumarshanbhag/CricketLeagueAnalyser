package com.cricketleagueanalyser.adapter;

import com.cricketleagueanalyser.model.IPLRunsCSV;

import java.util.List;

public class IPLBattingAdapter extends IPLAdapter {
    @Override
    public List loadIPLData(String csvFilePath) {
        return super.loadIPLData(IPLRunsCSV.class, csvFilePath);
    }
}