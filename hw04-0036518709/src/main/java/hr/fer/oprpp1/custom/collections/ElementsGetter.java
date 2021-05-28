package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter<T> {

	/** Checks whether the next element exist
	 * @return returns true if there is a next element, false otherwise
	 */
	public boolean hasNextElement();
	
	/**
	 * @return returns the next element
	 */
	public T getNextElement();
	
	default void processRemaining(Processor<? super T> p) {
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
