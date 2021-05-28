package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentDatabaseDemo {
	
	public static void main(String[] args) {
		List<String> lines = new ArrayList<String>();
		
		try {
			lines = Files.readAllLines(
					 Paths.get("./database.txt"),
					 StandardCharsets.UTF_8
					);
		} catch(Exception ex) {
			
		}
		
		StudentDatabase stDb = new StudentDatabase(lines);
		
		IFilter filterTrue = record -> true;
		IFilter filterFalse = record -> false;
		
		List<StudentRecord> filterT = stDb.filter(filterTrue);
		//List<StudentRecord> filterF = stDb.filter(filterFalse);
		
		for (StudentRecord rec : filterT) {
			System.out.println(rec.getLastName());
		}
		
//		StudentRecord sr = stDb.forJMBAG("0000000054");
//		System.out.println(sr.getLastName());
//		IComparisonOperator oper = ComparisonOperators.LESS;
//		System.out.println(oper.satisfied("Ana", "Jasna")); 
		
//		IComparisonOperator oper = ComparisonOperators.LIKE;
//		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
//		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
//		System.out.println(oper.satisfied("AAAA", "AA*AA"));
		
//		StudentRecord record = filterT.get(0);
//		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
//		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
//		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
		
//		ConditionalExpression expr = new ConditionalExpression(
//				 FieldValueGetters.LAST_NAME,
//				 "Bos*",
//				 ComparisonOperators.LIKE
//				);
//		
//		
//				StudentRecord record = filterT.get(0);
//				boolean recordSatisfies = expr.getComparisonOperator().satisfied(
//				 expr.getFieldGetter().get(record), // returns lastName from given record
//				 expr.getStringLiteral() // returns "Bos*"
//				);
//
//				System.out.println(recordSatisfies);
//				System.out.println(expr.getFieldGetter().get(record));
		
//		QueryLexer lexer = new QueryLexer("query jmbag =\"0123456789\" ");
//		while (lexer.nextToken().getType() != TokenType.EOF) {
//			
//		}
		//QueryParser qp1 = new QueryParser("query jmbag =\"0123456789\" exit");
		QueryParser qp2 = new QueryParser("query jmbag=\"0123456789\" and lastName>\"J\" \n");
		System.out.println("size: " + qp2.getQuery().size()); // 1
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp2.getQueriedJMBAG()); // 0123456789
		
		
	}
}
