package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.Dictionary;

public class GenerateMethodTest {
	
	@Test
	public void testKochCurveZero() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F+F--F+F");
		String axiom = "F";
		String result = generate(0, axiom, productions);
		String expected = "F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testKochCurveOne() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F+F--F+F");
		String axiom = "F";
		String result = generate(1, axiom, productions);
		String expected = "F+F--F+F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testKochCurveTwo() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F+F--F+F");
		String axiom = "F";
		String result = generate(2, axiom, productions);
		String expected = "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testKochIslandZero() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F-F+F+FF-F-F+F");
		String axiom = "F-F-F-F";
		String result = generate(0, axiom, productions);
		String expected = "F-F-F-F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testKochIslandOne() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F-F+F+FF-F-F+F");
		String axiom = "F-F-F-F";
		String result = generate(1, axiom, productions);
		String expected = "F-F+F+FF-F-F+F-F-F+F+FF-F-F+F-F-F+F+FF-F-F+F-F-F+F+FF-F-F+F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testPlant1Zero() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F[+F]F[-F]F");
		String axiom = "GF";
		String result = generate(0, axiom, productions);
		String expected = "GF";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testPlant1One() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F[+F]F[-F]F");
		String axiom = "GF";
		String result = generate(1, axiom, productions);
		String expected = "GF[+F]F[-F]F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testPlant1Two() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('F', "F[+F]F[-F]F");
		String axiom = "GF";
		String result = generate(2, axiom, productions);
		String expected = "GF[+F]F[-F]F[+F[+F]F[-F]F]F[+F]F[-F]F[-F[+F]F[-F]F]F[+F]F[-F]F";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testSierpinskiZero() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('R', "L-R-L");
		productions.put('L', "R+L+R");
		String axiom = "R";
		String result = generate(0, axiom, productions);
		String expected = "R";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testSierpinskiOne() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('R', "L-R-L");
		productions.put('L', "R+L+R");
		String axiom = "R";
		String result = generate(1, axiom, productions);
		String expected = "L-R-L";
		assertTrue(result.equals(expected));
	}
	
	@Test
	public void testSierpinskiTwo() {
		Dictionary<Character, String> productions = new Dictionary<>();
		productions.put('R', "L-R-L");
		productions.put('L', "R+L+R");
		String axiom = "R";
		String result = generate(2, axiom, productions);
		String expected = "R+L+R-L-R-L-R+L+R";
		assertTrue(result.equals(expected));
	}
	
	
	public String generate(int arg0, String axiom, Dictionary<Character, String> productions) {
		char[] prod = axiom.toCharArray();
		String curr = axiom;
		int ind = 1;
		while (ind <= arg0) {
			curr = "";
			for (int i = 0; i < prod.length; i++) {
				if (productions.get(prod[i]) != null) {
					curr += productions.get(prod[i]);
				} else {
					curr += Character.toString(prod[i]);
				}
			}
			prod = curr.toCharArray();	
			ind++;
		}
		return curr;
	}
}
