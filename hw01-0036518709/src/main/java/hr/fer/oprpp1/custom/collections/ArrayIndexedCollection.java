package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

public class ArrayIndexedCollection extends Collection {
	
	private int size;
	private Object[] elements;
	int capacity;
 	
	/**
	 * Creates an instance with capacity set to 16
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/** Sets the capacity to given value and preallocates the elements array of that size
	 * @param initialCapacity sets the array to given capacity
	 * @throws IllegalArgumentException if initial capacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this.size = 0;
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must not be less than 1");
		} else {
			this.capacity = initialCapacity;
			this.elements = new Object[this.capacity];
		}
	}
	
	/**
	 * Copies elements into newly constructed collection
	 * @param collection a non-null reference to some other Collection
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, 16);
	}
	
	/**
	 * Copies elements into newly constructed collection and sets the capacity to given value
	 * @param collection a non-null reference to some other Collection
	 * @param initialCapacity sets the array to given capacity
	 * @throws NullPointerException if given collection is null
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this.size = 0;
		this.capacity = initialCapacity;
		if (collection == null) {
			throw new NullPointerException("Collection must not be null");
		}
		if (this.capacity < collection.size()) {
			this.elements = new Object[collection.size()];
		} else {
			this.elements = new Object[this.capacity];
		}
		addAll(collection);
	}
	
	/**
	 * @return true if collection contains no objects and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/** 
	 * @return the number of currently stored objects in this collection
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Method calls processor.process(.) for each element of this collection. The order in which elements
	 * will be sent is undefined in this class
	 * @param processor
	 */
	@Override
	public void forEach(Processor processor) {
		for (Object obj : elements) {
			processor.process(obj);
		}
	}
	
	/**
	 * @param value object value we wish to check whether it exists in the collection
	 * @return true if the collection contains given value, as determined by equals method,
	 * otherwise returns false
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection, fills it with collection content and
	 * returns the array. 
	 * @throws UnsupportedOperationException if size is less than 1
	 * @return new array which is filled with collection content
	 */
	@Override
	public Object[] toArray() {
		if (this.size < 1) {
			throw new UnsupportedOperationException("Size must be greater than zero");
		}
		Object[] newArray = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			newArray[i] = this.elements[i];
		}
		return newArray;
	}
	
	/**
	 * Adds the given object into the collection - reference is added into first empty place in the elements array
	 * if the elements array is full it is reallocated by doubling its size
	 * @param value object value we wish to add into the collection
	 * @throws NullPointerException if given value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Value must not be null");
		} 
		
		if (this.size < this.capacity) {	
			this.elements[this.size] = value;
		} else {
			this.elements = Arrays.copyOf(this.elements, this.capacity *= 2);
			this.elements[this.size] = value;
		}
		
		this.size++;
	}
	
	
	/**
	 * Returns the object that is stored in backing array at position index
	 * @param index position of the object we wish to get
	 * @return object at position index
	 * @throws IndexOutOfBoundsException if index is less than zero or larger or equal to array size
	 */
	public Object get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index must be greater than or equal to zero and smaller than array size");
		}
		
		return this.elements[index];
	}
	
	/**
	 * Removes all elements from the collection
	 * size variable is set to zero
	 * 
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	
	
	/**
	 * Inserts (does not overwrite) the given value at the given position in array. 
	 * Elements at position and at greater positions are shifted one place toward the end.
	 * 
	 * @param value value of the object we wish to insert
	 * @param position in the collection at which we wish to insert the object
	 * @throws IndexOutOfBoundsException if Index is less than zero or larger than array size"
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index must be greater than or equal to zero and smaller or equal to array size");
		}
		
		if (this.elements[position] == null) {
			this.elements[position] = value;
		} else {
			this.capacity += 1;
			this.elements = Arrays.copyOf(this.elements, this.capacity);
			for (int i = this.capacity - 1; i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
			this.elements[position] = value;
		}
		this.size++;
	}
	
	
	/**
	 * Searches the collection and returns
	 * @param value object value of which we wish to find the index
	 * @return the index of the first occurrence 
	 * of the given value or -1 if the value is not found
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Returns true if the collection contains given value as determined by equals method and removes 
	 * one occurrence of it 
	 * @param value object value we wish to remove from the collection
	 * @return true if object is found otherwise returns false
	 */
	@Override
	public boolean remove(Object value) {
		
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Removes element at specified index from collection
	 * @param index position in the collection at which we wish to remove the object
	 * @throws IndexOutOfBoundsException if index is less than zero or greater or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index must be greater than or equal to zero and smaller than array size");
		}
		
		for (int i = index; i < this.size - 1; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		
		this.capacity -= 1;
		this.elements = Arrays.copyOf(this.elements, this.capacity);
		
		this.size--;
	}
}
