package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> array;
	
	/**
	 * Creates the stack
	 */
	public ObjectStack() {
		this.array = new ArrayIndexedCollection<T>();
	}
	
	/**
	 * @return true if stack contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return this.array.size() == 0;
	}
	
	/**
	 * @return the number of currently stored objects on the stack
	 */
	public int size() {
		return this.array.size();
	}
	
	/** Pushes given value on the stack
	 * @param value object value which is pushed on the stack
	 */
	public void push(T value) {
		if (value == null) {
			throw new NullPointerException("Value must not be null");
		}
		array.add(value);
	}
	
	/** Removes last value pushed on stack from stack and returns it
	 * @return the last value pushed on the stack
	 * @throws EmptyStackException if the stack is empty when the method is called
	 */
	public T pop() {
		if (isEmpty()) {
			throw new EmptyStackException("The stack is empty");
		}
		T item = array.get(array.size() - 1);
		array.remove(array.size() - 1);
		return item;
	}
	
	/** 
	 * @return last element placed on stack but does not delete it from stack
	 * @throws EmptyStackException if the stack is empty when the method is called
	 */
	public T peek() {
		if (isEmpty()) {
			throw new EmptyStackException("The stack is empty");
		}
		return array.get(array.size() - 1);
	}
	
	/**
	 * Removes all elements from stack
	 */
	public void clear() {
		array.clear();
	}
	
}
