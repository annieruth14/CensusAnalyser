package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import OpenCSV.CSVException;
import OpenCSV.ICSVBuilder;

public class CensusAnalyserCommonCSV {
	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) 
		{
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
			return CensusAnalyser.getCount(censusCSVIterator);
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}
}
