package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	@Test
	public void testArrayIndexedCollectionDefaultConstructor() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Assertions.assertEquals(0, array.size());
	}
	
	@Test
	public void testArrayIndexedCollectionConstructorWithInitialCapacityParameter() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(10);
		Assertions.assertEquals(0, array.size());
	}
	
	@Test
	public void testArrayIndexedCollectionConstructorThrowsException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testArrayIndexedCollectionConstructorWithTwoArguments() {
		Collection col = new Collection();
		ArrayIndexedCollection array = new ArrayIndexedCollection(col, 10);
		Assertions.assertEquals(0, array.size());
	}
	
	@Test
	public void testArrayIndexedCollectionConstructorWithTwoArgumentsThrowsException() {
		Collection col = null;
		Assertions.assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(col, 10));
	}
	
	@Test
	public void testIsEmptyMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Assertions.assertTrue(array.isEmpty());
	}
	
	@Test
	public void testSizeMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(0);
		}
		Assertions.assertEquals(5, array.size());
	}
	
	@Test
	public void testContainsMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertTrue(array.contains(2));
		
	}
	
	@Test
	public void testToArrayMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Object[] expected = array.toArray();
		Assertions.assertEquals(expected[2], array.get(2));
	}
	
	@Test
	public void testAddMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(5);
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		array.add(5);
		Assertions.assertEquals(5, array.get(5));
	}
	
	@Test
	public void testAddMethodNullException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Assertions.assertThrows(NullPointerException.class, () -> array.add(null));
	}
	
	@Test
	public void testGetMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertEquals(2, array.get(2));
	}
	
	@Test
	public void testGetMethodThrowsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> array.get(7));
	}
	
	@Test
	public void testClearMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		array.clear();
		Assertions.assertEquals(0, array.size());
	}
	
	@Test
	public void testInsertMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		array.insert("test", 3);
		Assertions.assertEquals(array.get(3), "test");
	}
	
	@Test
	public void testInsertMethodThrowsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> array.insert("test", 7));
	}
	
	@Test
	public void testIndexOfMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertEquals(3, array.indexOf(3));
	}
	
	@Test
	public void testIndexOfMethodNullValue() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertEquals(-1, array.indexOf(null));
	}
	
	@Test
	public void testBooleanRemoveMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			if (i == 3) {
				array.add("test");
			} else {
				array.add(i);
			}
		}
		array.remove("test");
		assertEquals(4, array.get(3));
	}
	
	@Test
	public void testRemoveByIndexMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		array.remove(3);
		assertEquals(4, array.get(3));
	}
	
	@Test
	public void testRemoveByIndexMethodThrowsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		for (int i = 0; i < 5; i++) {
			array.add(i);
		}
		Assertions.assertThrows(IndexOutOfBoundsException.class, () -> array.remove(7));
	}
	
	
	@Test
	public void testAddMethodThrowsException() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Assertions.assertThrows(NullPointerException.class, () -> array.add(null));
		
	}
}
