package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node {
	
	@Override
	public String toString() {
		String curr = new String();
		for (int i = 0; i < this.numberOfChildren(); i++) {
			curr += this.getChild(i).toString();
		}
		
		return curr;
	}
}
