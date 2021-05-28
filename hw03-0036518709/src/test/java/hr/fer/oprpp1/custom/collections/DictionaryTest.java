package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void testDictionaryDefaultConstructor() {
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		Assertions.assertEquals(0, dictionary.size());
	}
	
	@Test
	public void testSizeMethod() {
		Dictionary<Integer, String> dictionary = fillArray();
		Assertions.assertEquals(5, dictionary.size());
	}
	
	@Test
	public void testPutPairWithNewKey() {
		Dictionary<Integer, String> dictionary = fillArray();
		dictionary.put(10, "ten");
		Assertions.assertEquals("ten", dictionary.get(10));
	}
	
	@Test
	public void testPutMethodPairWithNewKeyReturnsNull() {
		Dictionary<Integer, String> dictionary = fillArray();
		String str = dictionary.put(10, "ten");
		Assertions.assertEquals(null, str);
	}
	
	@Test
	public void testPutPairWithExistingKey() {
		Dictionary<Integer, String> dictionary = fillArray();
		dictionary.put(3, "new value");
		Assertions.assertEquals("new value", dictionary.get(3));
	}
	
	@Test
	public void tesPutPairWithNullKeyThrowsException() {
		Dictionary<Integer, String> dictionary = fillArray();
		Assertions.assertThrows(IllegalArgumentException.class, () -> dictionary.put(null, "value"));
	}
	
	@Test
	public void testGetMethod() {
		Dictionary<Integer, String> dictionary = fillArray();
		Assertions.assertEquals("two", dictionary.get(2));
	}
	
	@Test
	public void testGetMethodReturnsNull() {
		Dictionary<Integer, String> dictionary = fillArray();
		Assertions.assertEquals(null, dictionary.get(7));
	}
	
	@Test
	public void testRemoveMethod() {
		Dictionary<Integer, String> dictionary = fillArray();
		Assertions.assertEquals("three", dictionary.remove(3));
	}
	
	
	@Test
	public void testRemoveMethodReturnsNull() {
		Dictionary<Integer, String> dictionary = fillArray();
		Assertions.assertEquals(null, dictionary.remove(7));
	}
	
	@Test
    public void testAddValueAndReturn(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        Assertions.assertEquals(1,dictionary.get("First"));
    }

    @Test
    public void testAddValueAndRemove(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.remove("First");
        Assertions.assertNull(dictionary.get("First"));
    }

    @Test
    public void testGetSize(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        Assertions.assertEquals(2,dictionary.size());
    }

    @Test
    public void testEmpty(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        Assertions.assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClear(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.clear();

        Assertions.assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testRemoveGetsOldValue(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        Assertions.assertEquals(1,dictionary.remove("First"));
    }

    @Test
    public void testPutOverWrites(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.put("First",3);

        Assertions.assertEquals(3,dictionary.get("First"));
    }

    @Test
    public void testRemove(){
        Dictionary<String, Integer> dictionary = new Dictionary<>();

        dictionary.put("First", 1);
        dictionary.put("Second", 2);

        dictionary.remove("First");

        Assertions.assertEquals(1,dictionary.size());
    }
	
	public Dictionary<Integer, String> fillArray() {
		
		Dictionary<Integer, String> dictionary = new Dictionary<>();
		dictionary.put(1, "one");
		dictionary.put(2, "two");
		dictionary.put(3, "three");
		dictionary.put(4, "four");
		dictionary.put(5, "five");
		return dictionary;
		
	}
	
}
