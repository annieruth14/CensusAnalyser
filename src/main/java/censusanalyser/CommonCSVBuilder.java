package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import OpenCSV.CSVException;
import OpenCSV.ICSVBuilder;

public class CommonCSVBuilder<E> implements ICSVBuilder{
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass ) throws CSVException {
		try {
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader());
			Iterator<CSVRecord> csvRecords = csvParser.iterator();
			return (Iterator<E>) csvRecords;
		}
		catch (IllegalStateException | IOException e) {
			throw new CSVException(e.getMessage(), CSVException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
	public List getCSVFileList(Reader reader, Class csvClass ) throws CSVException {
		try {
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
					.withFirstRecordAsHeader());
			List<CSVRecord> csvRecords = csvParser.getRecords();
			return csvRecords;
		}
		catch (IllegalStateException | IOException e) {
			throw new CSVException(e.getMessage(), CSVException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
}
