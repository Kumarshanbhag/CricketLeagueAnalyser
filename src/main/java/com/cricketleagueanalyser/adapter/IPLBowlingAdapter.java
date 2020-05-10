package com.cricketleagueanalyser.adapter;

import com.cricketleagueanalyser.model.IPLWktsCSV;

import java.util.List;

public class IPLBowlingAdapter extends IPLAdapter {
    @Override
    public List loadIPLData(String csvFilePath) {
        return super.loadIPLData(IPLWktsCSV.class, csvFilePath);
    }
}
