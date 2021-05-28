package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter {

	/** Checks whether the next element exist
	 * @return returns true if there is a next element, false otherwise
	 */
	public boolean hasNextElement();
	
	/**
	 * @return returns the next element
	 */
	public Object getNextElement();
	
	default void processRemaining(Processor p) {
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
