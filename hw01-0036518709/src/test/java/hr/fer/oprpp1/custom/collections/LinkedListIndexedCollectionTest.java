package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testIsEmptyMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		Assertions.assertTrue(list.isEmpty());
	}
	
	@Test
	public void testSizeMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("test");
		Assertions.assertEquals(1, list.size());
	}
	
	@Test
	public void testContainsMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("test");
		Assertions.assertTrue(list.contains("test"));
	}
	
	@Test
	public void testBooleanRemoveMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {
			if (i == 3) {
				list.add("test");
			} else {
				list.add(i);
			}
		}
		list.remove(2);
		Assertions.assertTrue(list.remove("test"));
	}
	
	@Test
	public void testToArrayMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Object[] array = list.toArray();
		Assertions.assertEquals(1, array[1]);
	}
	
	@Test
	public void testAddMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Assertions.assertEquals(1, list.get(1));
	}
	
	@Test
	public void testAddMethodThrowsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		Assertions.assertThrows(NullPointerException.class, () -> list.add(null));
	}
	
	@Test
	public void testGetMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Assertions.assertEquals(1, list.get(1));
	}
	
	
	@Test
	public void testGetMethodThrowsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(8));
	}
	
	@Test
	public void testClearMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.clear();
		Assertions.assertEquals(0, list.size());
	}
	
	@Test
	public void testInsertMethodAtFirstPosition() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.insert("test", 0);
		Assertions.assertEquals("test", list.get(0));
	}
	
	@Test
	public void testInsertMethodAtEndOfList() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.insert("test", list.size());
		Assertions.assertEquals("test", list.get(list.size() - 1));
	}
	
	@Test
	public void testInsertMethodAtLastPosition() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.insert("test", list.size() - 1);
		Assertions.assertEquals("test", list.get(list.size() - 2));
	}
	
	@Test
	public void testInsertMethodAtArbitraryPosition() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.insert("test", 3);
		Assertions.assertEquals("test", list.get(3));
	}
	
	@Test
	public void testInsertMethodthrowsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.insert("test", -1));
	}
	
	@Test
	public void testIndexOfMethod() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Assertions.assertEquals(2, list.indexOf(2));
	}
	
	@Test
	public void testRemoveMethodAtFirstPosition() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.remove(0);
		Assertions.assertEquals(1, list.get(0));
	}
	
	@Test
	public void testRemoveMethodAtLastPosition() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.remove(list.size() - 1);
		Assertions.assertEquals(3, list.get(list.size() - 1));
	}
	
	@Test
	public void testRemoveMethodAtArbitraryPosition() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		list.remove(2);
		Assertions.assertEquals(3, list.get(2));
	}
	
	@Test
	public void testRemoveMethodthrowsException() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		for (int i = 0; i < 5; i++) {	
			list.add(i);
		}
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.remove(8));
	}
	
	
	
	
	
}
