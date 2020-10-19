package censusanalyser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

	private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
	private static final String WRONG_PATH = "./src/test/resources/WrongIndiaStateCensusData.csv";
	private static final String WRONG_HEADER_PATH = "./src/test/resources/IndiaStateCensusDataMismatchHeader.csv";
	private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
	private static final String WRONG_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
	private static final String INDIA_STATE_CSV = "./src/test/resources/IndiaStateCode.csv";
	private static final String WRONG_FILE_PATH_STATE = "./src/main/resources/IndiaStateCode.csv";
	private static final String WRONG_FILE_TYPE_STATE = "./src/test/resources/IndiaStateCode.txt";
	private static final String WRONG_DELIMETER_STATE = "./src/test/resources/WrongIndiaStateCode.csv"; 
	
	CensusAnalyser censusAnalyser = new CensusAnalyser();
	
	@Before
	public void initialize() {
		ExpectedException exceptionRule = ExpectedException.none();
		exceptionRule.expect(CensusAnalyserException.class);
	}

	@Test
	public void givenIndianCensusCSVFileReturnsCorrectRecords() {
		try {
			int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			Assert.assertEquals(29, numOfRecords);
		} catch (CensusAnalyserException e) {
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenIndiaCensusData_WithIncorrectType_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.MISMATCH, e.type);
		}
	}
	
	@Test
	public void givenIndiaCensusData_WithMismatchHeader_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_HEADER_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.MISMATCH, e.type);
		}
	}
	
	@Test
	public void givenIndianStateCSV_shouldReturnExactCount() {
		try {
			int numOfState = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV);
			Assert.assertEquals(37, numOfState);
		} catch (Exception e) {
			//
		}
	}
	
	@Test
	public void givenIndianStateCSV_WithWrongFilePath_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_FILE_PATH_STATE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenIndianStateCSV_WithWrongFileType_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE_STATE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenIndianStateCSV_WithWrongDelimeter_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_DELIMETER_STATE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.MISMATCH, e.type);
		}
	}
}
