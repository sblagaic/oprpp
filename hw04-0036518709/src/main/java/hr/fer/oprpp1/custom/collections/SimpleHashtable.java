package hr.fer.oprpp1.custom.collections;

import static java.lang.Math.sqrt;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.pow;
import static java.lang.Math.abs;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	/**
	 * parameterised public static class which represents one hashtable slot
	 * @author sblag
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Creates a hashtable pair with the given parameters
		 * @param key key of the pair we wish to create
		 * @param value value of the pair we wish to create
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * @return returns key of a hashtable pair
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Sets the value of a hashtable pair to the given value 
		 * @param value pair value we wish to set
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * @return returns value of a hashtable pair
		 */
		public V getValue() {
			return this.value;
		}
		
	}
	
	/**
	 * @author sblag
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		private int index;
		private int listPos;
		private TableEntry<K, V> curr;
		private TableEntry<K, V> tmp;
		private int savedModificationCount;
		
		/**
		 * Initialises the variables of IteratorImpl
		 */
		public IteratorImpl() {
			this.index = 0;
			this.listPos = 0;			
			this.curr = table[index];
			this.tmp = table[index];
			this.savedModificationCount = modificationCount;
		}
		
		/**
		 * Determines whether the next element of the hashtable exists or not
		 * @return returns true if there is at least one more pair, false otherwise
		 * @throws ConcurrentModificationException if the hashtable has been structurally 
		 * modified outside of the iterator
		 */
		public boolean hasNext() {
			if (this.savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (curr.next == null && index < table.length) {
				if (table[index] == null) {
					return false;
				}
			} else if (index >= table.length) {
				return false;
			}
			return true;
		}
		
		/**
		 * Retrieves a pair from the hashtable
		 * @return returns the current
		 * @throws ConcurrentModificationException if the hashtable has been structurally 
		 * modified outside of the iterator
		 * @throws NoSuchElementException if there are no more elements left in the hashtable
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (table[index].next != null) { 
				if (listPos == 0) {
					curr = table[index].next;					
					listPos++;
					return tmp = table[index];
				} else {
					if (curr.next != null) {						
						tmp = curr;
						curr = curr.next;						
						listPos++;						
						return tmp;
					} else {						
						listPos = 0;
						index++;						
						return tmp = curr;
					}
				} 
			}
			if (listPos != 0 && curr.next == null) {
				index++;
				return tmp = curr;
			}
			return tmp = curr = table[index++];
		}
			
		
		/**
		 * Removes the current element (the one returned by next() method) from the hashtable
		 * @throws ConcurrentModificationException if the hashtable has been structurally 
		 * modified outside of the iterator
		 * @throws IllegalStateException if the method has been called consecutively without 
		 * calling the next() method first
		 */
		public void remove() {
			if (this.savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (tmp == null) {
				throw new IllegalStateException();
			}
			
			SimpleHashtable.this.remove(tmp.getKey());
			tmp = null;
			
			this.savedModificationCount = modificationCount;
		}
	}
	
	TableEntry<K, V>[] table;
	private int size = 0;
	private int modificationCount = 0;
	
	/**
	 * Initialises the hashtable and creates 16 slots in it
	 */
	public SimpleHashtable() {
		this.table = (TableEntry<K, V>[]) new TableEntry[16];
 	}
	
	
	/**
	 * Initialises the hashtable capacity to the first potention of number 2
	 * which is greater or equal to the given initial capacity
	 * @param initialCapacity parameter used to determine the capacity of the hashtable
	 * @throws IllegalArgumentException if the given parameter is less than 1
	 */
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must be greater than zero");
		}
		
		int cap = (int) sqrt(initialCapacity);
		if (pow(2, cap) >= initialCapacity) {
			this.table = (TableEntry<K, V>[]) new TableEntry[(int) pow(2, cap)];
		} else {
			this.table = (TableEntry<K, V>[]) new TableEntry[(int) pow(2, cap + 1)];
		}
		
	}
	
	/**
	 * Adds the given key - value pair in the hashtable. If the pair key 
	 * already exists then the existing pair value is replaced with the new one.
	 * @param key pair key
	 * @param value pair value
	 * @return returns old pair value or null if the key didn't exist
	 * @throws NullPointerException if the given key is null
	 */
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key must not be null");
		}
		int ind = abs(key.hashCode() % this.table.length);
		if (this.table[ind] != null) {
			TableEntry<K, V> list = this.table[ind];
			while (list.next != null) {
				if (list.getKey().equals(key)) {
					V tmp = list.getValue();
					list.setValue(value);
					return tmp;
				}
				list = list.next;
			}
			this.modificationCount++;
			this.size++;
			list.next = new TableEntry(key, value);
			
		} else {
			if (this.size / table.length >= 0.75) {
				doubleCapacity();
				ind = abs(key.hashCode() % this.table.length);
			}
			this.modificationCount++;
			this.size++;
			if (this.table[ind] != null) {
				TableEntry<K, V> list = this.table[ind];
				while (list.next != null) {
					list = list.next;
				}
				list.next = new TableEntry(key, value);
			} else {
				this.table[ind] = new TableEntry(key, value);
			}
		}
		return null;
	}
	
	/**
	 * Retrieves pair value using the given key
	 * @param key key used to retrieve the pair value
	 * @return returns pair value or null if the key doesn't exist
	 */
	public V get(Object key) {
		int ind = abs(key.hashCode() % this.table.length);
		if (this.table[ind] != null) {
			TableEntry<K, V> list = (TableEntry<K, V>) this.table[ind];
			while (!list.getKey().equals(key)) {
				list = list.next;
			}
			return list.getValue();
		}
		return null;
	}
	
	/**
	 * @return returns the number of pairs in the hashtable
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks whether the hashtable contains a pair with the given key
	 * @param key key that we wish to check whether it exists
	 * @return returns if the pair with the given key exists, false otherwise
	 * @throws NullPointerException if the given key is null
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new NullPointerException("Key must not be null");
		}
		int ind = abs(key.hashCode() % this.table.length);
		TableEntry<K, V> list = (TableEntry<K, V>) this.table[ind];
		if (list != null) {
			while (list != null) {
				if (list.getKey().equals(key)) {
					return true;
				}
				list = list.next;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the hashtable contains a pair with the given value
	 * @param value value that we wish to check whether it exists
	 * @return returns if the pair with the given key exists, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> list = (TableEntry<K, V>) this.table[i];
			if (list != null) {
				while (list != null) {
					if (list.getValue().equals(value)) {
						return true;
					}
					list = list.next;
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes the pair with the given key
	 * @param key key of the pair we wish to remove
	 * @return returns the removed pair value or null if the key doesn't exist
	 */
	public V remove(Object key) {
		int ind = abs(key.hashCode() % this.table.length);
		TableEntry<K, V> list = (TableEntry<K, V>) this.table[ind];		
		if (list != null) {
			if (list.getKey().equals(key)) {
				V tmp = list.getValue();
				this.table[ind] = list.next;			
				this.modificationCount++;
				this.size--;
				return tmp;
			}
			while (list.next != null) {
				if (list.next.key.equals(key)) { 						
					V tmp = list.next.getValue();					
					list.next = list.next.next;					
					this.modificationCount++;
					this.size--;
					return tmp;
				}
				list = list.next;
			}
		}
		return null;
	}
	
	/**
	 * @return returns true if the hashtable is empty, 
	 * false otherwise
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Creates a string of all pairs from the collection 
	 * @return returns a string in format "[key1=value1, key2=value2, key3=value3]"
	 */
	public String toString() {
		if (this.size < 1) {
			return "There are no elements in the hashtable";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i] != null) {
				TableEntry<K, V> list = (TableEntry<K, V>) this.table[i];
				while (list != null) {
					sb.append(list.getKey()).append("=").append(list.getValue()).append(", ");
					list = list.next;
				}
				
			}
		}
		sb.replace(sb.length() - 2, sb.length(), "]");
		return sb.toString();
	}
	
	/**
	 * Creates an array of pair references which are stored in the 
	 * same order as they are in the internal structure of the hashtable
	 * @return returns a new array the size of the number of elements 
	 * currently stored in the hashtable
	 */
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] array = (TableEntry<K,V>[]) new TableEntry[this.table.length];
		for (int i = 0; i < this.table.length; i++) {
			if (this.table[i] != null) {
				TableEntry<K, V> list = (TableEntry<K, V>) this.table[i];
				while (list != null) {
					array[i] = new TableEntry<K, V>(list.getKey(), list.getValue());
					list = list.next;
				}
			}
		}
		return array;
	}
	
	/**
	 * Deletes all pairs from the collection 
	 * but does not change its capacity
	 */
	public void clear() {
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		} 
		this.modificationCount++;
	}
	
	/**
	 * Doubles the capacity of the current table if the ratio between the 
	 * size variable and the capacity is greater or equal than 75% number of slots 
	 */
	private void doubleCapacity() {
		TableEntry<K, V>[] tmp = this.toArray();
		this.table = Arrays.copyOf(this.table, this.table.length * 2);
		this.clear();
		for (TableEntry<K, V> entry : tmp) {
			this.put(entry.getKey(), entry.getValue());
		}
	}


	/**
	 * Creates an instance of Iterator<TableEntry<K, V>> iterator
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
}
