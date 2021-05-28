package hr.fer.oprpp1.custom.collections;

public interface Collection<T> {
	
	
	/**
	 * @return true if collection contains no objects and false otherwise
	 */
	public default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/** 
	 * Returns the number of currently stored objects in a collection
	 */
	public int size();
	
	/**
	 * Adds the given object into a collection
	 * @param value object value we wish to add into the collection
	 */
	public void add(T value);
	
	/**
	 * Returns true if the collection contains given value, as determined by equals method,
	 * otherwise returns false
	 * @param value object value we wish to check whether it exists in the collection
	 */
	public boolean contains(Object value);
	
	/**
	 * Returns true if the collection contains given value as determined by equals method and removes 
	 * one occurrence of it 
	 * @param value object value we wish to remove from the collection
	 */
	public boolean remove(Object value);
	
	/**
	 * Allocates new array with size equals to the size of this collection, fills it with collection content and
	 * returns the array. 
	 */
	public Object[] toArray();
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
	 * will be sent is undefined in this class
	 * @param processor
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection
	 * @param other collection whose elements we wish to add to the current collection
	 */
	public default void addAll(Collection<? extends T> other) {
		
		class ProcessorAddAll implements Processor<T> {
			
			public ProcessorAddAll() {
				super();
			}
			
			@Override
			public void process(T value) {
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
	public void clear();
	
	public ElementsGetter<T> createElementsGetter();
	
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			T obj = getter.getNextElement();
			if (tester.test(obj)) {
				this.add(obj);
			}
		}
	}
	
}

