package hr.fer.oprpp1.custom.scripting.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class ParserTest {
	
	@Test
	public void testExample1() {
		String s = this.readExample(1);
		SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode doc = parser.getDocumentNode();
		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
	}
	
	@Test
	public void testExample2() {
		String s = this.readExample(2);
		SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode doc = parser.getDocumentNode();
		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
	}
	
	@Test
	public void testExample3() {
		String s = this.readExample(3);
		SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode doc = parser.getDocumentNode();
		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
	}
	
	@Test
	public void testExample4() {
		String s = this.readExample(4);
		
		Assertions.assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser(s));
	}

//	@Test
//	public void testExample5() {
//		String s = this.readExample(5);
//		SmartScriptParser parser = new SmartScriptParser(s);
//		DocumentNode doc = parser.getDocumentNode();
//		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
//	}
//	
	@Test
	public void testExample6() {
		String s = this.readExample(6);
		SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode doc = parser.getDocumentNode();
		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
	}
	
	@Test
	public void testExample7() {
		String s = this.readExample(7);
		SmartScriptParser parser = new SmartScriptParser(s);
		DocumentNode doc = parser.getDocumentNode();
		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
	}

//	@Test
//	public void testExample8() {
//		String s = this.readExample(8);
//		SmartScriptParser parser = new SmartScriptParser(s);
//		DocumentNode doc = parser.getDocumentNode();
//		Assertions.assertEquals(doc.getChild(0).getClass(), TextNode.class);
//	}
//	
	@Test
	public void testExample9() {
		String s = this.readExample(9);
		
		Assertions.assertThrows(SmartScriptParserException.class, 
				() -> {SmartScriptParser parser = new SmartScriptParser(s);
						//DocumentNode doc = parser.getDocumentNode();
				});
	}
	
	@Test
	public void testInvalidVariableNameThrowsException() {
		String docBody = "This is sample text.\r\n"
				+ "{$ FOR i_+ 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n";
		Assertions.assertThrows(SmartScriptParserException.class, 
				() -> {SmartScriptParser parser = new SmartScriptParser(docBody);
						//DocumentNode doc = parser.getDocumentNode();
				});
		
	}
	
	@Test
	public void testInvalidFunctionNameThrowsException() {
		String docBody = "This is sample text.\r\n"
				+ "{$FOR i 0 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @1sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";
		Assertions.assertThrows(SmartScriptParserException.class, 
				() -> {SmartScriptParser parser = new SmartScriptParser(docBody);
						//DocumentNode doc = parser.getDocumentNode();
				});
		
	}
	
	@Test
	public void testInvalidEqualsTagThrowsException() {
		String docBody = "This is sample text.\r\n"
				+ "{$FOR i = 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @1sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";
		Assertions.assertThrows(SmartScriptParserException.class, 
				() -> {SmartScriptParser parser = new SmartScriptParser(docBody);
						//DocumentNode doc = parser.getDocumentNode();
				});
		
	}
	
	@Test
	public void testInvalidOperatorThrowsException() {
		String docBody = "This is sample text.\r\n"
				+ "{$FOR i +- 10 2 $}\r\n"
				+ " sin({$=i$}^2) = {$= i i * @1sin \"0.000\" @decfmt $}\r\n"
				+ "{$END$}";
		Assertions.assertThrows(SmartScriptParserException.class, 
				() -> {SmartScriptParser parser = new SmartScriptParser(docBody);
						//DocumentNode doc = parser.getDocumentNode();
				});
		
	}
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
	}
}	
