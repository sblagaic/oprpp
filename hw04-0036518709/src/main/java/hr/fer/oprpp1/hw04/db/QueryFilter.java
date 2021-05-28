package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryFilter implements IFilter {
	
	List<ConditionalExpression> expressions = new ArrayList<>();

	/**
	 * @return true if all conditional expressions are satisfied,
	 * otherwise returns false
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		boolean satisfied = false;
		for (ConditionalExpression expr : expressions) {
			satisfied = expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), expr.getStringLiteral()
					);
			if (!satisfied) return false;
		}
		return true;
	}
	
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

}
