package hr.fer.oprpp1.custom.collections;

public class Dictionary<K, V> {
	
	ArrayIndexedCollection<Pair<K, V>> array;
	
	public Dictionary() {
		this.array = new ArrayIndexedCollection<Dictionary<K,V>.Pair<K,V>>();
	}
	
	private class Pair<K, V> {
		private K key;
		private V value;
		
		/**
		 * Initialises one key - value pair with given values
		 * @param key pair key (must not be null)
		 * @param value pair value
		 * @throws IllegalArgumentException if the given key is null
		 */
		private Pair(K key, V value) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			this.key = key;
			this.value = value;
		
		}
		
		public K getKey() {
	        return this.key;
	    }

	    public V getValue() {
	        return this.value;
	    }

	    public void setValue(V value) {
	        this.value = value;
	    }
	}
	
	/**
	 * @return returns true if dictionary contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * @return returns the number of currently stored objects in the dictionary
	 */
	public int size() {
		return this.array.size();
	}
	
	/**
	 * Removes all elements from dictionary
	 */
	public void clear() {
		this.array.clear();
	}
	
	/**
	 * Adds the given key - value pair in the dictionary. If the pair key 
	 * already exists then the existing pair value is replaced with the new one.
	 * @param key pair key
	 * @param value pair value
	 * @return returns old pair value or null if the key didn't exist
	 */
	public V put(K key, V value) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).getKey().equals(key)) { 
				V tmp = array.get(i).getValue();
				array.get(i).setValue(value);
				return tmp;
			}
		}
		array.add(new Pair<K, V>(key, value));
		return null;
	}
	
	/**
	 * Retrieves pair value from dictionary based on the given pair key
	 * @param key key of the pair we wish to retrieve the value
	 * @return returns value of the pair based on the given key
	 * or null if the key doesn't exist
	 */
	public V get(K key) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).getKey().equals(key)) {
				return array.get(i).getValue();
			}
		}
		return null;
	}
	
	/**
	 * Removes a pair from the dictionary based on the given key
	 * @param key key of the pair we wish to remove
	 * @return returns value of the removed pair or null if the pair didn't exist
	 */
	public V remove(K key) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).getKey().equals(key)) {
				V tmp = array.get(i).getValue();
				array.remove(i);
				return tmp;
			}
		}
		return null;
	}
	
	
	
}
