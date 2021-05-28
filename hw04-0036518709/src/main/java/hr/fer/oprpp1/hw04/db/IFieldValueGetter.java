package hr.fer.oprpp1.hw04.db;

public interface IFieldValueGetter {
	
	/**
	 * Obtains a requested field value from given StudentRecord
	 * @param record StudentRecord used to acquire requested field value
	 * @return requested field value
	 */
	public String get(StudentRecord record);
	
}
