package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedListIndexedCollection implements List {
	
	private long modificationCount = 0;
	
	private static class GetLinkedListElements implements ElementsGetter {
		
		private LinkedListIndexedCollection collection;
		private long savedModificationCount;
		private ListNode curr;
		
		/** Initialises the collection and sets savedModificationCount variable
		 * to current value of ModificationCount variable
		 * @param col LinkedListIndexedCollection collection
		 */
		public GetLinkedListElements(LinkedListIndexedCollection col) {
			this.collection = col;
			this.savedModificationCount = col.modificationCount;
			this.curr = col.first;
		}

		/** Checks whether the next element exist
		 * @return returns true if there is a next element, false otherwise
		 * @throws ConcurrentModificationException if the collection has been structurally changed
		 */
		@Override
		public boolean hasNextElement() {
			if (this.savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			return this.curr != null;
		}

		/**
		 * @return returns the next element
		 * @throws ConcurrentModificationException if the collection has been structurally changed
		 * @throws NoSuchElementException if there are no more elements to get
		 */
		@Override
		public Object getNextElement() {
			if (!hasNextElement()) {
				throw new NoSuchElementException("No more elements to get");
			}
			
			ListNode tmp = this.curr;
			this.curr = this.curr.next;
			return tmp.data;
		}
		
	}
	
	private static class ListNode {
		Object data = new Object();
		ListNode prev;
		ListNode next;
		
		/** Initialises the storage variable and sets the next pointer to null
		 * @param data data which is stored in list element
		 */
		public ListNode(Object data) {
			this.data = data;
			this.next = null;
		}
	}
	
		private int size;
		private ListNode first;
		private ListNode last;
		
		/**
		 * Sets the first and last nodes to null as well as the size variable
		 */
		public LinkedListIndexedCollection() {
			this.first = this.last = null;
			this.size = 0;
		}
		
		/** Copies all elements from given collection into new collection
		 * @param collection collection whose elements are copied into this newly constructed collection
		 */
		public LinkedListIndexedCollection(Collection collection) {
			addAll(collection);
			this.size = collection.size();
		}
		 
		/** 
		 * @return the number of currently stored objects in this collection
		 */
		@Override
		public int size() {
			return this.size;
		}
		
		/**
		 * @param value object value we wish to check whether it exists in the collection
		 * @return true if the collection contains given value, as determined by equals method,
		 * otherwise returns false
		 */
		@Override
		public boolean contains(Object value) {
			ListNode curr = this.first;
			while(curr != null) {
				if (curr.data.equals(value)) {
					return true;
				}
				curr = curr.next;
			}
			return false;
		}
		 
		/**
		 * Returns true if the collection contains given value as determined by equals method and removes 
		 * one occurrence of it 
		 * @param value object value we wish to remove from the collection
		 * @return true if object is found otherwise returns false
		 */
		@Override
		public boolean remove(Object value) {
			if (this.first == null) {
				return false;
			}
			
			ListNode curr = this.first;
			int index = 0;
			while (curr != null) {
				if (curr.data.equals(value)) {
					this.modificationCount++;
					if (index == 0) {
						this.first = this.first.next;
						this.first.prev = null;
						this.size--;
						return true;
					}
					if (index == this.size - 1) {
						this.last = this.last.prev;
						this.last.next = null;	
						this.size--;
						return true;
					}
					curr.next.prev = curr.prev;
					curr.prev.next = curr.next;
					this.size--;
					return true;
				}
				
				curr = curr.next;
				index++;
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
			Object[] array = new Object[this.size];
			int counter = 0;
			ListNode curr = this.first;
			while(curr != null) {
				array[counter] = curr.data;
				curr = curr.next;
				counter++;
			}
			
			return array;
		}
			
		/**
		 * Adds the given object into the collection at the end of the collection. Newly added element becomes the
		 * element at the biggest index
		 *
		 * @param value object value we wish to add into the collection
		 * @throws NullPointerException if given value is null
		 */
		@Override
		public void add(Object value) {
			if (value == null) {
				throw new NullPointerException("Value must not be null");
			}
			
			this.modificationCount++;
			ListNode node = new ListNode(value);
			if (this.size == 0) {
				this.first = this.last = node;
			} else {
				node.prev = this.last;
				this.last.next = node;
				this.last = node;
			}
			
			this.size++;
		}
		
		/**
		 * Returns the object that is stored in linked list at position index
		 * @param index position of the object we wish to get
		 * @return object at position index
		 * @throws IndexOutOfBoundsException if index is less than zero or larger or equal to array size
		 */
		public Object get(int index) {
			if (index < 0 || index >= this.size) {
				throw new IndexOutOfBoundsException("The index must be greater or equal to zero and smaller than size");
			}
			int counter = 0;
			if (index < this.size / 2) {
				ListNode curr = this.first;
				while (curr != null) {
					if (counter == index) {
						return curr.data;
					}
					counter++;
					curr = curr.next;
				} 
			} else {
				counter = this.size - 1;
				ListNode curr = this.last;
				while (counter >= this.size / 2) {
					if (counter == index) {
						return curr.data;
					}
					counter--;
					curr = curr.prev;
				}
				
			}
			return -1;
		}
		
		/**
		 * Removes all elements from the collection
		 * size variable is set to zero
		 * 
		 */
		public void clear() {
			this.first = this.last = null;
			this.size = 0;
			this.modificationCount++;
		}
		
		
		/**
		 * Inserts, but does not overwrite, the given value at the given position in linked-list. 
		 * Elements starting from this position are shifted one position
		 * @param value object value we wish to insert
		 * @param position position in the linked list at which we wish to insert the value
		 */
		public void insert(Object value, int position) {
			if (position < 0 || position > this.size) {
				throw new IndexOutOfBoundsException();
			}
			int counter = 0;
			ListNode curr = this.first;
			ListNode node = new ListNode(value);
			
			if (position == 0) {
				node.next = this.first;
				this.first.prev = node;	
				this.first = node;
				this.size++;
				return;
			} 
			
			if (position == this.size - 1) {
				curr.next = node;
				node.next = this.last;
				node.prev = this.last.prev;
				this.last.prev = node;
				this.size++;
				return;
			} 
			
			if (position == this.size) {
				node.prev = this.last;
				this.last.next = node;
				this.last = node;
				this.size++;
				return;
			} 	
			
			while (counter < position - 1) {
				counter++;
				curr = curr.next;
			}
			node.next = curr.next;
			node.prev = curr;
			curr.next = node;
			node.next.prev = node;
			this.size++;
		
		}
		
		
		/**
		 * Searches the collection
		 * @param value of the object whose index we wish to find
		 * @return the index of the first occurrence of the given value or -1 if the value is not found
		 */
		public int indexOf(Object value) {
			ListNode curr = this.first;
			int index = 0;
			while (curr != null) {
				if (curr.data.equals(value)) {
					return index;
				}
				curr = curr.next;
				index++;
			}
			
			return -1;
		}
		
		
		/** Removes list element at specified index from collection
		 * @param index index at which we wish to remove the list element
		 * @throws IndexOutOfBoundsException if index is less than zero or greater or equal to size
		 */
		public void remove(int index) {
			if (index < 0 || index >= this.size) {
				throw new IndexOutOfBoundsException("The index must be greater or equal to zero and smaller than size");
			}
			
			ListNode curr = this.first;
			int i = 0;
			if (index == 0) {
				this.first = this.first.next;
				this.first.prev = null;
				
			} else if (index == this.size - 1) {
				this.last = this.last.prev;
				this.last.next = null;
				
			} else {
				while (curr != null) {
					if (i == index - 1) {
						curr.next = curr.next.next;
						curr = curr.next;
						curr.prev = curr.prev.prev;
						break;
					}
					curr = curr.next;
					i++;
				}
			}
			this.modificationCount++;
			this.size--;
		}

		/**
		 * Creates a GetArrayElements object
		 * @return returns reference to the newly 
		 * created GetArrayElements object
		 *
		 */
		@Override
		public ElementsGetter createElementsGetter() {
			return new GetLinkedListElements(this);
		}
		
}
