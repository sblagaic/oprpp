package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
	
	private List<StudentRecord> studentRecords = new ArrayList<StudentRecord>();
	private Map<String, Integer> jmbagMap = new HashMap<String, Integer>();
	
	/**
	 * Checks if there are duplicate jmbags, or if 
	 * finalGrade is not a number between 1 and 5
	 * @param jmbag
	 * @param finalGrade
	 * @throws IllegalArgumentException
	 */
	private void isValid(String jmbag, String finalGrade) {
		if (jmbagMap.get(jmbag) != null || Integer.parseInt(finalGrade) < 1 || Integer.parseInt(finalGrade) > 5) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Creates an internal list of student records. It also creates an index 
	 * for fast retrieval of student records when jmbag is known
	 * @param rows the content of database.txt, each
	 * string represents one row of the database file
	 */
	public StudentDatabase(List<String> rows) {
		int noOfRecords = 0;
		StudentRecord studentRecord;
		for (String row : rows) {
			String[] records = row.split("\\s+");
			if (records.length == 5) {
				isValid(records[0], records[4]);
				studentRecord = new StudentRecord(records[0], records[1]  + " " + records[2], records[3], records[4]);
			} else {
				isValid(records[0], records[3]);
				studentRecord = new StudentRecord(records[0], records[1], records[2], records[3]);
			}
			studentRecords.add(studentRecord);
			jmbagMap.put(records[0], noOfRecords++);
		}
	}
	
	/**
	 * Uses index to obtain requested record in O(1)
	 * @param jmbag key used to retrieve list index of requested jmbag
	 * @return StudentRecord for given jmbag, if the record does not exist, the method returns null
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return jmbagMap.get(jmbag) != null ? studentRecords.get(jmbagMap.get(jmbag)) : null;
	}
	
	/**
	 * Loops through all student records in its internal list, calls 
	 * accepts method on given filter-object with current record, 
	 * each record for which accepts returns true is added to temporary list
	 * @param filter instance of functional interface IFilter
	 * @return list of student records which were accepted by filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tmp = new ArrayList<StudentRecord>(); 
		for (StudentRecord record : studentRecords) {
			if (filter.accepts(record)) {
				tmp.add(record);
			}
		}
		return tmp;
	}
	
}
