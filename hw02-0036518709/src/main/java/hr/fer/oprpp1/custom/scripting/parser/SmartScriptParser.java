package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.LexerScript;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

 

public class SmartScriptParser {
	
	private DocumentNode document;
	private ObjectStack stack;
	
	/** Appends the given node to the last Node at the stack
	 * @param node which is appended to the last Node at the stack
	 */
	public void appendNode(Node node) {
		Node stackNode = (Node) stack.peek();
		stackNode.addChildNode(node);
	}
	
	/** 
	 * Determines which type of Element is the given ForLoopNode parameter 
	 * @param lexerF lexer through which the necessary parameter is acquired
	 * @return variable of the appropriate Element inherited type
	 * @throws SmartScriptParserException if the parameter is invalid
	 */
	public Element getElement(LexerScript lexerF) {
		String curr = lexerF.getToken().getValue().toString();
		char[] data = curr.toCharArray();
		//is digit
		if (data[0] == '-' || Character.isDigit(data[0])) {
			boolean isInt = true;
			for (int i = 0; i < data.length; i++) {
				//System.out.println(Character.toString(data[i]));
				 if (!Character.isDigit(data[i]) && data[i] != '.' && data[i] != '-') {
					 throw new SmartScriptParserException("Invalid number");
				 } else {
					 if (data[i] == '.') {
						 isInt = false;
					 }
				 }
			}
			if (isInt) {
				return new ElementConstantInteger(Integer.parseInt(curr));
			} else {
				return new ElementConstantDouble(Double.parseDouble(curr));
			} 
			
		//is variable
		 } else if (Character.isLetter(data[0])) {
			 for (int i = 0; i < data.length; i++) {
				 if (!Character.isLetter(data[i]) && !Character.isDigit(data[i]) && data[i] != '_') {
					 throw new SmartScriptParserException("Invalid variable name");
				 }
			 }
			 return new ElementVariable(curr);
			 
		//is string
		 } else if (data[0] == '\"' && data[data.length - 1] == '\"') {
			 return new ElementString(curr);
			 
		 } else {
			 throw new SmartScriptParserException("Invalid parameter");
		 }
		
	}
	
	public void checkText(String text) {
		char[] data = text.toCharArray();
		int index = text.indexOf('\"');
		if (index == -1) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] == '\\' && Character.isLetter(data[i + 1])) {
					throw new SmartScriptParserException("Invalid backslash position");
				}
			}
		}
		
	}
	
	/** 
	 * Determines which type of Element is the given EchoNode parameter 
	 * @param lexerF lexer through which the necessary parameter is acquired
	 * @return variable of the appropriate Element inherited type
	 * @throws SmartScriptParserException if the parameter is invalid 
	 */
	public Element getEchoElement(LexerScript lexerF) {
		String curr = lexerF.getToken().getValue().toString();
		char[] data = curr.toCharArray();
		if (data.length == 1 && (data[0] == '+' || data[0] == '-' 
				|| data[0] == '*' || data[0] == '/' || data[0] == '^')) {
			return new ElementOperator(curr);
		} else if (data[0] == '@' && Character.isLetter(data[1])) {
			for (int i = 2; i < data.length; i++) {
				 if (!Character.isLetter(data[i]) && !Character.isDigit(data[i]) && data[i] != '_') {
					 throw new SmartScriptParserException("Invalid function name");
				 } else {
					 return new ElementFunction(curr);
				 }
			 }
		} else {
			return getElement(lexerF);
		}
		return null;
	}
	

	/** Constructor which accepts a string that contains document body,
	 * creates an instance of lexer and initialises it with obtained text
	 * @param documentBody string that contains document body
	 */
	public SmartScriptParser(String documentBody) {
		document = new DocumentNode();
		LexerScript lexer = new LexerScript(documentBody);
		parse(lexer);
		
	}
	
	/**
	 * Parses the given tokens and creates the appropriate document model
	 * @param lexer lexer which produces the necessary tokens
	 */
	public void parse(LexerScript lexer) {
		TokenScript token;
		//0 for documentNode, 1 for forNode
		int state = 0;
		stack = new ObjectStack();
		stack.push(document);
		
		while (lexer.nextToken().getType() != TypeOfToken.EOF) {
			token = lexer.getToken();
			if (token.getType() == TypeOfToken.TEXT) {
				checkText(token.getValue().toString());
				appendNode(new TextNode(token.getValue().toString()));
			} else if (token.getType() == TypeOfToken.FOR) {
				 state++;
				 ForLoopNode forLoopNode;
				 lexer.nextToken();
				 ElementVariable variable = (ElementVariable) getElement(lexer);
				 lexer.nextToken();
				 Element startExpression = getElement(lexer);
				 lexer.nextToken();
				 Element endExpression = getElement(lexer);
				 Element stepExpression;
				 if (lexer.nextToken().getType() == TypeOfToken.CONTENT) {
					 stepExpression = getElement(lexer);
					 forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
					 stack.push(forLoopNode);
					 
				 } else if (lexer.getToken().getType() == TypeOfToken.TEXT) {
					 forLoopNode = new ForLoopNode(variable, startExpression, endExpression, null);
					 stack.push(forLoopNode);
					 checkText(token.getValue().toString());
					 appendNode(new TextNode(lexer.getToken().toString()));
					 
				 }
				 continue;
			}
			
			
			 if (lexer.getToken().getType() == TypeOfToken.EQUALS) {
				 if (state == 0) {
					 throw new SmartScriptParserException("");
				 }
				 Element[] elements = new Element[20];
				 int counter = 0;
				 while (lexer.nextToken().getType() != TypeOfToken.TEXT && lexer.getToken().getType() != TypeOfToken.END) {
					 elements[counter++] = getEchoElement(lexer);
				 }
				 appendNode(new EchoNode(elements));
				 if (lexer.getToken().getType() == TypeOfToken.TEXT) {
					 checkText(token.getValue().toString());
					 appendNode(new TextNode(lexer.getToken().getValue().toString()));
				 }
			 }
			
			 if (lexer.getToken().getType() == TypeOfToken.END) {
				 state--;
				 ForLoopNode forLoop = (ForLoopNode) stack.pop();
				 DocumentNode docNode = (DocumentNode) stack.pop();
				 docNode.addChildNode(forLoop);
				 stack.push(docNode);
			 }			
				 
		}
		
	}
	
	/**
	 * @return documentNode documentNode of the parser
	 */
	public DocumentNode getDocumentNode() {
		return this.document;
	}
	
}
