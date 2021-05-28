package hr.fer.oprpp1.hw08.jnotepadpp;

public class CaseTypes {
	
	public static final ICaseTransform UPPER = c -> {
		return Character.toUpperCase(c);
	};
	
	public static final ICaseTransform LOWER = c -> {
		return Character.toLowerCase(c);
	};
	
	public static final ICaseTransform TOGGLE = c -> {
		if(Character.isLowerCase(c)) {
			c = Character.toUpperCase(c);
			
		} else if(Character.isUpperCase(c)) {
			c = Character.toLowerCase(c);
		}
		
		return c;
	};
}
