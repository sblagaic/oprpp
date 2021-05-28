package hr.fer.zemris.java.gui.layouts;

import java.util.ArrayList;
import java.util.List;

public class Operators {

	public static List<String> EQUALS() {
		List<String> opers = new ArrayList<>();
		opers.add("=");
		return opers;
	}
	
	public static List<String> COMMON() {
		List<String> opers = new ArrayList<>();
		opers.add("/");
		opers.add("*");
		opers.add("-");
		opers.add("+");
		return opers;
	}
	
	public static List<String> GEOM() {
		List<String> opers = new ArrayList<>();
		opers.add("1/x");
		opers.add("sin");
		opers.add("log");
		opers.add("cos");
		opers.add("ln");
		opers.add("tan");
		opers.add("ctg");
		return opers;
	}
	
	public static List<String> GEOM_INV() {
		List<String> opers = new ArrayList<>();
		opers.add("1/x");
		opers.add("arcsin");
		opers.add("10^x");
		opers.add("arccos");
		opers.add("e^x");
		opers.add("arctan");
		opers.add("arcctg");
		return opers;
	}
	
	
}
