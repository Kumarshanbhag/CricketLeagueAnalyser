package com.cricketleagueanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IPLWktsCSV {
    @CsvBindByName(column = "PLAYER", required = true)
    public String player;

    @CsvBindByName(column = "Avg", required = true)
    public double ballAverage;

    @CsvBindByName(column = "SR", required = true)
    public double strikeRate;

    @CsvBindByName(column = "Econ", required = true)
    public double economy;

    @CsvBindByName(column = "4w", required = true)
    public int fourWicket;

    @CsvBindByName(column = "5w", required = true)
    public int fiveWicket;

    @CsvBindByName(column = "Wkts", required = true)
    public int wickets;
}
