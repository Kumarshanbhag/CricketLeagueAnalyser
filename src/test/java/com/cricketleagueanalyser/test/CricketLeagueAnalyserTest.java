package com.cricketleagueanalyser.test;

import com.cricketleagueanalyser.analyser.CricketLeagueAnalyser;
import com.cricketleagueanalyser.dao.IPLDAO;
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
    private static final String IPL_2019_MOST_WKTS_CSV_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";

    private static CricketLeagueAnalyser cricketLeagueAnalyser;

    @Before
    public void setUp() throws Exception {
        cricketLeagueAnalyser = new CricketLeagueAnalyser();
    }

    //UC1
    @Test
    public void givenIplCSVFile_ShouldReturnTopBattingAverages() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVG, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("MS Dhoni", iplDao[0].player);
    }

    @Test
    public void givenIplCSVFile_WithWrongFilePath_ShouldReturnException() {
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, WRONG_IPL_2019_MOST_RUNS_CSV_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIplCSVFile_WithWrongFileType_ShouldReturnException() {
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, WRONG_IPL_2019_MOST_RUNS_CSV_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenIplData_WhenSortedOnBattingAverageAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVG, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC2
    @Test
    public void givenIplCSVFile_ShouldReturnTopStrikingAverages() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.STRIKINGRATES, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Ishant Sharma", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsStrikingRatesAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.STRIKINGRATES, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC3
    @Test
    public void givenIplCSVFile_ShouldReturnMaximumSixAndFour() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.SIXFOURS, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Andre Russell", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsMaximumSixFoursAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.SIXFOURS, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC4
    @Test
    public void givenIplCSVFile_ShouldReturnStrikingRateWithSixAndFour() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.SIXFOURSAVG, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Andre Russell", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnStrikingRateWithSixFoursAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.SIXFOURSAVG, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC5
    @Test
    public void givenIplCSVFile_ShouldReturnAverageWithStrikingRate() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVGWITHSTRIKERATE, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("MS Dhoni", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnAverageWithStrikingRateAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVGWITHSTRIKERATE, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC6
    @Test
    public void givenIplCSVFile_ShouldReturnRunsWithAverage() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.MAXRUNSWITHBESTAVERAGE, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("David Warner", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnRunsWithAverageAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.MAXRUNSWITHBESTAVERAGE, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC7
    @Test
    public void givenIplCSVFile_ShouldReturnTopBowlingAverages() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVG, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Krishnappa Gowtham", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnRunsWithBowlingAverageAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVG, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIplData_WhenSortedOnRunsWithFieldingAverageAndNoDataFound_ShouldReturnException() {
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.FIELDING, IPL_2019_MOST_WKTS_CSV_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    //UC8
    @Test
    public void givenIplCSVFile_ShouldReturnTopStrikingRateOfBowlers() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.STRIKINGRATES, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Krishnappa Gowtham", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsStrikingRatesOfBowlersAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.STRIKINGRATES, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC9
    @Test
    public void givenIplCSVFile_ShouldReturnBestEconomyOfBowlers() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.ECONOMY, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Ben Cutting", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnsEconomyOfBowlersAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.ECONOMY, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC10
    @Test
    public void givenIplCSVFile_ShouldReturnBestStrikingRatesWith4W5WOfBowlers() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.FOURFIVEWICKETWITHSTRIKERATES, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Lasith Malinga", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnBestStrikingRatesWith4W5WOfBowlersAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.FOURFIVEWICKETWITHSTRIKERATES, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC11
    @Test
    public void givenIplCSVFile_ShouldReturnBestBowlingAverageWithStrikingRateOfBowlers() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVGWITHSTRIKERATE, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Krishnappa Gowtham", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnBestBowlingAverageWithStrikingRateOfBowlersAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVGWITHSTRIKERATE, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC12
    @Test
    public void givenIplCSVFile_ShouldReturnMostWicketWithAverageOfBowlers() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.WICKETWITHAVERAGE, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Imran Tahir", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnMostWicketWithAverageOfBowlersAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BALLING, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.WICKETWITHAVERAGE, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    //UC13
    @Test
    public void givenIplCSVFile_ShouldReturnTopBattingAveragesAndBowlingAverages() {
        List cricketersList = cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH, IPL_2019_MOST_WKTS_CSV_PATH);
        String cricketersDataInJson = cricketLeagueAnalyser.sortListAndConvertJson(SortByField.AVG, cricketersList);
        IPLDAO[] iplDao = new Gson().fromJson(cricketersDataInJson, IPLDAO[].class);
        Assert.assertEquals("Krishnappa Gowtham", iplDao[0].player);
    }

    @Test
    public void givenIplData_WhenSortedOnTopBattingAveragesAndBowlingAveragesAndNoDataFound_ShouldReturnException() {
        List emptyList = new ArrayList();
        try {
            cricketLeagueAnalyser.analyseIPLData(CricketLeagueAnalyser.BatOrBall.BATTING, IPL_2019_MOST_RUNS_CSV_PATH, IPL_2019_MOST_WKTS_CSV_PATH);
            cricketLeagueAnalyser.sortListAndConvertJson(SortByField.WICKETWITHAVERAGE, emptyList);
        } catch (CricketLeagueAnalyserException e) {
            Assert.assertEquals(CricketLeagueAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }
}