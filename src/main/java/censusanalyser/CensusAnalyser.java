package censusanalyser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import OpenCSV.CSVException;
import OpenCSV.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class CensusAnalyser<E> {
	private static final String JSON_FILE_PATH = "/home/annie/eclipse-workspace/CensusAnalyser-20201017T033040Z-001/CensusAnalyser/src/test/resources/StateCensusData.json";

	public List createObject(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			return censusCSVList;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		} catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		List<IndiaCensusCSV> censusCSVList = createObject(csvFilePath);
		return censusCSVList.size();
	}

	public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			Iterator<IndiaStateCodecsv> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodecsv.class);
			return getCount(censusCSVIterator);
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		} catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	public static <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}

	public int loadCensusDataforCommonCSV(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCommonCSVBuilder();
			Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
			return getCount(censusCSVIterator);
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	public String getStateCodeSortedData(String csvFilePath) throws CensusAnalyserException {
		List<IndiaStateCodecsv> censusCSVList = createObject(csvFilePath);
		Comparator<IndiaStateCodecsv> censusComparator = Comparator.comparing(census -> census.stateCode);
		this.sort(censusCSVList, censusComparator);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}

	public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		List<IndiaCensusCSV> censusCSVlist = createObject(csvFilePath);
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusCSVlist, censusComparator);
		String sortedStateCensusJson = new Gson().toJson(censusCSVlist);
		return sortedStateCensusJson;
	}

	public String getAreaWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(JSON_FILE_PATH));
			List<IndiaCensusCSV> censusCSVList = createObject(csvFilePath);
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
			this.sortReverse(censusCSVList, censusComparator);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(censusCSVList, writer);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			return sortedStateCensusJson;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	public String getDensityWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		List<IndiaCensusCSV> censusCSVList = createObject(csvFilePath);
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
		this.sortReverse(censusCSVList, censusComparator);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}

	public String getPopulationWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		List<IndiaCensusCSV> censusCSVList = createObject(csvFilePath);
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
		this.sortReverse(censusCSVList, censusComparator);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}

	private <E> void sort(List<E> censusCSVList, Comparator<E> censusComparator) {
		censusCSVList.sort((E census1, E census2) -> censusComparator.compare(census1, census2));
	}

	private void sortReverse(List<IndiaCensusCSV> censusCSVList, Comparator<IndiaCensusCSV> censusComparator) {
		censusCSVList.sort((IndiaCensusCSV census1, IndiaCensusCSV census2) -> censusComparator.reversed()
				.compare(census1, census2));
	}
}
