package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	/**
	 * Parses the given string into RCPosition
	 * @param text panel coordinates 
	 * @return RCPosition
	 */
	public static RCPosition parse(String text) {
		int ind = text.indexOf(",");
		if (ind == 0 || ind == text.length() - 1 || ind == -1) {
			throw new IllegalArgumentException();
		}
		
		return new RCPosition(Integer.parseInt(text.substring(0, ind).trim()), 
				Integer.parseInt(text.substring(ind + 1).trim()));
	}
	
	public static void main(String[] args) {
		String str = "1,7";
		
		RCPosition rcp = RCPosition.parse(str);
		System.out.println(rcp.getRow() + " " + rcp.getColumn());
	}
}
