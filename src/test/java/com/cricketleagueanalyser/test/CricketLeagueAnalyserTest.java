package com.cricketleagueanalyser.test;

import com.cricketleagueanalyser.analyser.CricketLeagueAnalyser;
import com.cricketleagueanalyser.enums.SortByField;
import com.cricketleagueanalyser.exception.CricketLeagueAnalyserException;
import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.csvparser.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CricketLeagueAnalyserTest {
    private static final String IPL_2019_MOST_RUNS_CSV_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private static final String WRONG_IPL_2019_MOST_RUNS_CSV_PATH = "./src/MAIN/resources/IPL2019FactsheetMostRuns.csv";
    private static final String WRONG_IPL_2019_MOST_RUNS_CSV_TYPE = "./src/test/resources/IPL2019FactsheetMostRuns.json";

    private static CricketLeagueAnalyser cricketLeagueAnalyser;

    @Before
    public void setUp() throws Exception {
        cricketLeagueAnalyser = new CricketLeagueAnalyser();
    }

    //UC1
    @Test
    public void givenIplCSVFile_ShouldReturnTopBattingAverages() {
        String numOfRecords = cricketLeagueAnalyser.analyseIPLData(SortByField.AVG, IPL_2019_MOST_RUNS_CSV_PATH);
        IPLRunsCSV[] censusCSV = new Gson().fromJson(numOfRecords, IPLRunsCSV[].class);
        Assert.assertEquals("MS Dhoni", censusCSV[0].player);
    }

    @Test
    public void givenIplCSVFile_WithWrongFilePath_ShouldReturnException() {
        try {
            cricketLeagueAnalyser.analyseIPLData(SortByField.AVG, WRONG_IPL_2019_MOST_RUNS_CSV_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIplCSVFile_WithWrongFileType_ShouldReturnException() {
        try {
            cricketLeagueAnalyser.analyseIPLData(SortByField.AVG, WRONG_IPL_2019_MOST_RUNS_CSV_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIplData_WhenSortedOnBattingAverageAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.sortList(SortByField.AVG, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC2
    @Test
    public void givenIplCSVFile_ShouldReturnTopStrikingAverages() {
        String numOfRecords = cricketLeagueAnalyser.analyseIPLData(SortByField.STRIKINGRATES, IPL_2019_MOST_RUNS_CSV_PATH);
        IPLRunsCSV[] censusCSV = new Gson().fromJson(numOfRecords, IPLRunsCSV[].class);
        Assert.assertEquals("Ishant Sharma", censusCSV[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsStrikingRatesAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.sortList(SortByField.STRIKINGRATES, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC3
    @Test
    public void givenIplCSVFile_ShouldReturnMaximumSixAndFour() {
        String numOfRecords = cricketLeagueAnalyser.analyseIPLData(SortByField.SIXFOURS, IPL_2019_MOST_RUNS_CSV_PATH);
        IPLRunsCSV[] censusCSV = new Gson().fromJson(numOfRecords, IPLRunsCSV[].class);
        Assert.assertEquals("Andre Russell", censusCSV[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsMaximumSixFoursAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.sortList(SortByField.SIXFOURS, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC4
    @Test
    public void givenIplCSVFile_ShouldReturnStrikingRateWithSixAndFur() {
        String numOfRecords = cricketLeagueAnalyser.analyseIPLData(SortByField.SIXFOURSAVG, IPL_2019_MOST_RUNS_CSV_PATH);
        IPLRunsCSV[] censusCSV = new Gson().fromJson(numOfRecords, IPLRunsCSV[].class);
        Assert.assertEquals("Andre Russell", censusCSV[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsStrikingRateWithSixFoursAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.sortList(SortByField.SIXFOURSAVG, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }
}
