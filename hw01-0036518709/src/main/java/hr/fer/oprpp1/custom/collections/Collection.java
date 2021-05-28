package hr.fer.oprpp1.custom.collections;

public class Collection {
	
	protected Collection() {
		
	}
	
	/**
	 * Returns true if collection contains no objects and false otherwise
	 * @return 0
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/** 
	 * Returns the number of currently stored objects in this collection
	 * @return 0
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection
	 * @param value object value we wish to add into the collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Returns true if the collection contains given value, as determined by equals method,
	 * otherwise returns false
	 * @param value object value we wish to check whether it exists in the collection
	 * @return false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns true if the collection contains given value as determined by equals method and removes 
	 * one occurrence of it 
	 * @param value object value we wish to remove from the collection
	 * @return false
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection, fills it with collection content and
	 * returns the array. 
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {		
		//Object[] array = new Object[size()];
		
		throw new UnsupportedOperationException("This operation is not supported yet");
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
	 * will be sent is undefined in this class
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection
	 * @param other collection whose elements we wish to add to the current collection
	 */
	public void addAll(Collection other) {
		
		class ProcessorAddAll extends Processor {
			
			public ProcessorAddAll() {
				super();
			}
			
			@Override
			public void process(Object value) {
				add(value);
			}
			
		}
		
		ProcessorAddAll processor = new ProcessorAddAll();
			other.forEach(processor);

	}
	
	/**
	 * Removes all elements from the collection
	 * 
	 */
	public void clear() {
		
	}
}

