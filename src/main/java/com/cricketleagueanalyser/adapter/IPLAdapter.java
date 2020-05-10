package com.cricketleagueanalyser.adapter;

import com.cricketleagueanalyser.dao.IPLDAO;
import com.cricketleagueanalyser.model.IPLRunsCSV;
import com.cricketleagueanalyser.model.IPLWktsCSV;
import com.csvparser.CSVBuilderException;
import com.csvparser.CSVBuilderFactory;
import com.csvparser.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public abstract class IPLAdapter {

    public <E> List loadIPLData(Class<E> csvClass, String csvFilePath) {
        List csvFileList = new ArrayList();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, csvClass);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if (csvClass.getName().equals("com.cricketleagueanalyser.model.IPLRunsCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IPLRunsCSV.class::cast)
                        .forEach(censusCSV -> csvFileList.add(new IPLDAO(censusCSV)));
            } else if (csvClass.getName().equals("com.cricketleagueanalyser.model.IPLWktsCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IPLWktsCSV.class::cast)
                        .forEach(censusCSV -> csvFileList.add(new IPLDAO(censusCSV)));
            }
            return csvFileList;
        } catch (IOException e) {
            throw new CSVBuilderException("NO CSV FILE FOUND", CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public abstract List loadIPLData(String csvFilePath);
}
