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
	private static final String INDIA_STATE_CSV = "./src/test/resources/IndiaStateCode.csv";
	private static final String WRONG_FILE_TYPE = "./src/test/resources/IndiaStateCode.txt";
	
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
}
