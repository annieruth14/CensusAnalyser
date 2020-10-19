package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodecsv {
	
	 @CsvBindByName(column = "StateName", required = true)
	    public String stateName;
	 
	 @CsvBindByName(column = "StateCode", required = true)
	    public String stateCode;
}
