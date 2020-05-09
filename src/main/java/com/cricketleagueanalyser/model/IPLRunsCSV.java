package com.cricketleagueanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IPLRunsCSV {
    @CsvBindByName(column = "PLAYER", required = true)
    public String player;

    @CsvBindByName(column = "Avg", required = true)
    public double average;

    @CsvBindByName(column = "SR", required = true)
    public double strikingRates;

    @CsvBindByName(column = "6s", required = true)
    public double sixs;

    @CsvBindByName(column = "4s", required = true)
    public double fours;

    @CsvBindByName(column = "Runs", required = true)
    public double runs;
}
