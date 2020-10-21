package censusanalyser;

public class CSVException extends Exception {
	enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, MISMATCH
    }

    ExceptionType type;

    public CSVException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
