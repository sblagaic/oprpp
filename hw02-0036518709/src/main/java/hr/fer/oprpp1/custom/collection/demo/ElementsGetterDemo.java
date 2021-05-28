package hr.fer.oprpp1.custom.collection.demo;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.List;
import hr.fer.oprpp1.custom.collections.Tester;
import hr.fer.oprpp1.hw02.prob1.Token;
import hr.fer.oprpp1.hw02.prob1.TokenType;

public class ElementsGetterDemo {
	
	public static void main(String[] args) {
		Collection col = new LinkedListIndexedCollection(); // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		
		
		class EvenIntegerTester implements Tester {
			 public boolean test(Object obj) {
			 if(!(obj instanceof Integer)) return false;
			 Integer i = (Integer)obj;
			 return i % 2 == 0;
			 }
		}
		
//		Collection col1 = new LinkedListIndexedCollection();
//		Collection col2 = new ArrayIndexedCollection();
//		col1.add(2);
//		col1.add(3);
//		col1.add(4);
//		col1.add(5);
//		col1.add(6);
//		col2.add(12);
//		col2.addAllSatisfying(col1, new EvenIntegerTester());
//		//System.out.println(col2.size());
//		col2.forEach(System.out::println);
		
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");
		Collection col3 = col1;
		Collection col4 = col2;
		col1.get(0);
		col2.get(0);
		//col3.get(0); // neće se prevesti! Razumijete li zašto?
		//col4.get(0); // neće se prevesti! Razumijete li zašto?
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna		

	}
}
