package censusanalyser;

import OpenCSV.ICSVBuilder;
import OpenCSV.OpenCSVBuilder;

public class CSVBuilderFactory {

	public static ICSVBuilder createCSVBuilder() {
		return new OpenCSVBuilder<>();
	}
}
