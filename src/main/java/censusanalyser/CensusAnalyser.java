package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import OpenCSV.CSVException;
import OpenCSV.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class CensusAnalyser {
	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			return censusCSVList.size();
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} 
		catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		}
		catch (CSVException e) {
			System.out.println("Exception found");
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			
			Iterator<IndiaStateCodecsv> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodecsv.class);
			return getCount(censusCSVIterator);
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		}
		catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}
	
	public static <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int) StreamSupport
				.stream(csvIterable.spliterator(), false)
				.count();
		return numOfEntries;
	}
	
	public int loadCensusDataforCommonCSV(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
		{
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCommonCSVBuilder();
			Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader , IndiaCensusCSV.class);
			return getCount(censusCSVIterator);
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
			this.sortStateName(censusCSVList, censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			return sortedStateCensusJson;
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} 
		catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		}
		catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	private void sortStateName(List<IndiaCensusCSV> censusCSVList, Comparator<IndiaCensusCSV> censusComparator) {
		censusCSVList.sort((IndiaCensusCSV census1 , IndiaCensusCSV census2 ) -> censusComparator.compare(census1, census2) );
	}

	public String getStateCodeSortedData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<IndiaStateCodecsv> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCodecsv.class);
			Comparator<IndiaStateCodecsv> censusComparator = Comparator.comparing(census -> census.stateCode);
			this.sortStateCode(censusCSVList, censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			return sortedStateCensusJson;
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} 
		catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		}
		catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	private void sortStateCode(List<IndiaStateCodecsv> censusCSVList, Comparator<IndiaStateCodecsv> censusComparator) {
		censusCSVList.sort((IndiaStateCodecsv census1 , IndiaStateCodecsv census2 ) -> censusComparator.compare(census1, census2) );
	}

	public String getPopulationWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
			this.sortStatePopulation(censusCSVList, censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			return sortedStateCensusJson;
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} 
		catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		}
		catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}

	private void sortStatePopulation(List<IndiaCensusCSV> censusCSVList, Comparator<IndiaCensusCSV> censusComparator) {
		censusCSVList.sort((IndiaCensusCSV census1 , IndiaCensusCSV census2 ) -> censusComparator.reversed().compare(census1, census2));
	}

	public String getDensityWiseSortedData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
			this.sortStatePopulation(censusCSVList, censusComparator);
			String sortedStateCensusJson = new Gson().toJson(censusCSVList);
			return sortedStateCensusJson;
		} 
		catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} 
		catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.MISMATCH);
		}
		catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
	}
}
