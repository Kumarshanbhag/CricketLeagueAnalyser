package com.cricketleagueanalyser.test;

import com.cricketleagueanalyser.analyser.CricketLeagueAnalyser;
import com.cricketleagueanalyser.enums.SortByField;
import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CricketLeagueAnalyserTest {
    private static final String IPL_2019_MOST_RUNS_CSV_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";

    private static CricketLeagueAnalyser cricketLeagueAnalyser;

    @Before
    public void setUp() throws Exception {
        cricketLeagueAnalyser = new CricketLeagueAnalyser();
    }

    @Test
    public void givenIplCSVFile_ShouldReturnTopBattingAverages() {
        String numOfRecords = cricketLeagueAnalyser.analyseIPLData(SortByField.AVG, IPL_2019_MOST_RUNS_CSV_PATH);
        IPLRunsCSV[] censusCSV = new Gson().fromJson(numOfRecords, IPLRunsCSV[].class);
        Assert.assertEquals("MS Dhoni", censusCSV[0].player);
    }


}
