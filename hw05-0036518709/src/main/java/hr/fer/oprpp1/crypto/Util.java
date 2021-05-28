package hr.fer.oprpp1.crypto;

public class Util {
	
	/**
	 * Takes hex-encoded String and returns appropriate byte array
	 * @param keyText hex-encoded String 
	 * @return byte array of hex-encoded String
	 * @throws if hex-encoded string has an odd number of characters
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
	    byte[] data = new byte[keyText.length() / 2];
	    for (int i = 0; i < keyText.length(); i += 2) {
	        data[i / 2] = (byte) (Util.getDigit(new String(keyText.substring(i, i + 2))));
	    }
	    return data;
	}
	
	
	/**
	 * For each byte of given array, two characters are returned in string, 
	 * in big-endian notation
	 * @param bytearray array of bytes
	 * @return hex-encoded String of byte array
	 */
	public static String bytetohex(byte[] bytearray) {
		String hex = new String();
		for (byte b : bytearray) {
			hex += String.format("%02x", b);
		}
		return hex;
	}
	
	/**
	 * Takes two hex characters and returns appropriate string
	 * @param s two hex-encoded characters
	 * @return digit representation of hex string
	 */
	private static int getDigit(String s) {
		char[] hex = s.toCharArray();
		int len = hex.length - 1;
		int value = 0;
		int number = 0;
 		for (int i = 0; i < hex.length; i++) {
 			if (hex[i] >= '0' && hex[i] <= '9') {
 				value = hex[i] - 48 ;
 				
 			} else if (hex[i] >= 'a' && hex[i] <= 'f') {			
 				value = hex[i] - 97 + 10;
 				
 			} else if (hex[i] >= 'A' && hex[i] <= 'F') {
 				value = hex[i] - 65 + 10;
 			}
 			
 			number += value * Math.pow(16, len);
 			len--;
 		}
		
		return number;
	}
}
