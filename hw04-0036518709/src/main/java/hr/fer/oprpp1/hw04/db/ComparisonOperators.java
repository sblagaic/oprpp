package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {

	public static final IComparisonOperator LESS = (value1, value2) -> {
		return value1.compareTo(value2) < 0;
	};
	
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) <= 0;
	};
	
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return value1.compareTo(value2) > 0;
	};
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) >= 0;
	};
	
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) == 0;
	};
	
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) != 0;
	};
	
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		int index = value2.indexOf('*');
		int len1 = value1.length();
		int len2 = value2.length();
		if (index != value2.lastIndexOf('*')) return false;
		
		if (index == -1) return value1.equals(value2);
		
		return value1.substring(0, index).equals(value2.substring(0, index)) && 
				value1.substring(len1 - (len2 - index - 1), len1).equals(value2.substring(index + 1, len2)) &&
				(len2 - 1) <= len1;
	};
}
