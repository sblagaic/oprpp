package hr.fer.oprpp1.hw04.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class PrintRecords {
	public static String spaces(int last, int first) {
		String curr = new String();
		int i = 0;
		curr += "+============+";
		while (i < last + 2) {
			curr += "=";
			i++;
		}
		i = 0;
		curr += "+";
		while (i < first + 2) {
			curr += "=";
			i++;
		}
		i = 0;
		curr += "+===+";
		return curr;
	}
	
	public static void print(List<StudentRecord> queriedRecs) {
		int maxLast = 0;
		int maxFirst = 0;
		for (StudentRecord rec : queriedRecs) {
			if (rec.getLastName().length() > maxLast) maxLast = rec.getLastName().length();
			if (rec.getFirstName().length() > maxFirst) maxFirst = rec.getFirstName().length();
		}
		System.out.println(spaces(maxLast, maxFirst));
		for (StudentRecord rec : queriedRecs) {
			String surname = new String();
			String name = new String();
			int i = 0;
			while (i < maxLast - rec.getLastName().length()) {
				surname += " ";
				i++;
			}
			i = 0;
			while (i < maxFirst - rec.getFirstName().length()) {
				name += " ";
				i++;
			}
					
			System.out.println("| " + rec.getJmbag() + " | " + rec.getLastName() + surname
			+ " | " + rec.getFirstName() + name + " | " + rec.getFinalGrade() + " |");
		}
		
		System.out.println(spaces(maxLast, maxFirst));
		System.out.println("Records selected: " + queriedRecs.size());
	}
	
	public static void main(String[] args) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(
					 Paths.get("./src/main/java/database.txt"),
					 StandardCharsets.UTF_8
					);
		} catch(Exception ex) {
			
		}
		
		StudentDatabase stDb = new StudentDatabase(lines);
		
		Scanner sc = new Scanner(System.in);
		QueryParser parser;
		String line = sc.nextLine();
		while (!line.equals("exit")) {
			if (!line.substring(0, 5).equals("query")) {
				throw new IllegalArgumentException();
			}
			line = line.substring(5, line.length());
			parser = new QueryParser(line);
			
			List<ConditionalExpression> expressions = parser.getQuery();
			QueryFilter filter = new QueryFilter(expressions);
			List<StudentRecord> queriedRecs = stDb.filter(filter);
			
			PrintRecords.print(queriedRecs);
			
			line = sc.nextLine();
		}
		sc.close();
		
	}
}
