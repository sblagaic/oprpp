package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {
	
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return the stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	
}
