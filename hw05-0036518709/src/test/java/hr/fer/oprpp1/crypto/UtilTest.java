package hr.fer.oprpp1.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void testhextobyte() {
		byte[] expected = {1, -82, 34};
		assertArrayEquals(expected, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void testbytetohex() {
		byte[] actual = {1, -82, 34};
		String actualString = Util.bytetohex(actual);
		String expected = "01aE22";
		assertEquals(expected.toLowerCase(), actualString);
	}
}
