package com.cricketleagueanalyser.exception;

public class CricketLeagueAnalyserException extends RuntimeException {
    public enum ExceptionType {
        NO_CENSUS_DATA;
    }

    public String message;
    public ExceptionType type;

    public CricketLeagueAnalyserException(String message, ExceptionType type) {
        this.message =message;
        this.type = type;
    }
}
