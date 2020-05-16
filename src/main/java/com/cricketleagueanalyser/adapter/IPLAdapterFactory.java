package com.cricketleagueanalyser.adapter;

import com.cricketleagueanalyser.analyser.CricketLeagueAnalyser;
import com.cricketleagueanalyser.enums.BatOrBall;
import com.csvparser.CSVBuilderException;

import java.util.List;

public class IPLAdapterFactory {
    public List loadIPLData(BatOrBall gameFact, String[] csvFilePath) {
        if (gameFact.equals(BatOrBall.BATTING))
            return new IPLBattingAdapter().loadIPLData(csvFilePath);
        else if (gameFact.equals(BatOrBall.BALLING))
            return new IPLBowlingAdapter().loadIPLData(csvFilePath);
        throw new CSVBuilderException("NO CSV FILE FOUND", CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
    }
}

