package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {

	private ArrayIndexedCollection collection;
	private ElementVariable variable;
	private Element startExpression; 
	private Element endExpression;
	private Element stepExpression;
	
	
	/**
	 * 
	 * Initialises the class props
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * @return value of variable property
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * @return value of startExpression property
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * @return value of endExpression property
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * @return value of stepExpression property
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		String curr = new String();
		int i = 0;
		curr = "{$ FOR" + " " + this.variable.asText() + " " + this.startExpression.asText() 
				+ " " +  this.endExpression.asText() + " "
				+ (this.stepExpression == null ? "" : this.stepExpression.asText())  
				+ " " + "$}";
		while (i < this.numberOfChildren()) {
			curr += this.getChild(i).toString();
			i++;
		}
		curr += "{$END$}";
		
		return curr;
	}
	
	
}
