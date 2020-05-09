package com.cricketleagueanalyser.loader;

import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.csvparser.CSVBuilderException;
import com.csvparser.CSVBuilderFactory;
import com.csvparser.ICSVBuilder;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class IPLCSVLoader {
    public List loadIPLData(String csvFilePath)  {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            return csvBuilder.getCSVFileList(reader, IPLRunsCSV.class);
        } catch (IOException e) {
            throw new CSVBuilderException("NO CSV FILE FOUND", CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
