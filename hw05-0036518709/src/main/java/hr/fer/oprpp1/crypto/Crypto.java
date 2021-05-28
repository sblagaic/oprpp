package hr.fer.oprpp1.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	
	/**
	 * Calculates the digest on the file and 
	 * compares it with the given the digest
	 * @param file input file on which the digest is calculated
	 */
	public static void check(String file) {
		System.out.println("Please provide expected sha-256 digest for hw05test.bin: ");
		Scanner sc = new Scanner(System.in);
		String sha = sc.nextLine();
		sc.close();
		String hex = new String();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int len;
 
            while ((len = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);     
            }
 
            hex = Util.bytetohex(md.digest());
      
            inputStream.close();
	            
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
		
			if (sha.equals(hex)) {
				System.out.println("Digesting completed. Digest of hw05test.bin matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of hw05test.bin does not match the expected digest. Digest\r\n"
						+ "was: " + sha);
			}
	}
	
	public static void crypt(String fileInput, String fileOutput, boolean encrypt) {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		Scanner sc = new Scanner(System.in);
		String keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		String ivText = sc.nextLine();
		sc.close();
		
		try {	
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			InputStream inputStream = new FileInputStream(fileInput);
			OutputStream outputStream = new FileOutputStream(fileOutput);
            byte[] buffer = new byte[4096];
            int len;
 
            while ((len = inputStream.read(buffer)) != -1) {
                	outputStream.write(cipher.update(buffer, 0, len));               
            }
                      
            outputStream.write(cipher.doFinal());
            
            inputStream.close();
            outputStream.close();
	            
	        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException |
	        		InvalidKeyException | InvalidAlgorithmParameterException | 
	        		IllegalBlockSizeException | BadPaddingException ex) {
	            ex.printStackTrace();
	            
	        }
		
			System.out.println((encrypt ? "Encryption" : "Decryption") + " completed. Generated file " + fileOutput + " based on file " + fileInput);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] input = line.split("\\s+");
		
		switch(input[0]) {
			case "checksha":Crypto.check(input[1]);
							break;
			case "encrypt": Crypto.crypt(input[1], input[2], true);
							break;
			case "decrypt": Crypto.crypt(input[1], input[2], false);
							break;
		}
		
		sc.close();
	}
}






















//public static void crypt(String fileOutput, String address, String password, boolean encrypt) {
//
//	String keyText = new String();
//
//	try {
//		keyText = new String(Files.readAllBytes(Paths.get("masterPassword.txt")));
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//
//	// System.out.println(keyText);
//	try {
//		// SecretKeySpec keySpec = new SecretKeySpec(keyText.getBytes("UTF-8"), "AES");
//		
//		KeySpec spec = new PBEKeySpec(keyText.toCharArray(), "12345678".getBytes(), 65536, 256); // AES-256
//		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//		byte[] key = f.generateSecret(spec).getEncoded();
//		SecretKeySpec keySpec = new SecretKeySpec(Arrays.copyOf(key, 16), "AES");
//
//		byte[] iv = new byte[16];
//		new SecureRandom().nextBytes(iv);
//		IvParameterSpec ivParameter = new IvParameterSpec(iv);
//
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, ivParameter);
//
//		// OutputStream outputStream = new FileOutputStream(fileOutput);
//		// byte[] buffer = password.getBytes();
//
////		outputStream.write(cipher.doFinal(buffer));
////		outputStream.close();
//		byte[] encrypted = cipher.doFinal(password.getBytes());
//		setPassword(new File("passwords.txt"), getSecretKey(address), Base64.getEncoder().encode(encrypted));
//
//	} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
//			| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
//			| InvalidKeySpecException ex) {
//		ex.printStackTrace();
//
//	}
//}
//
//public static String decrypt(String password) {
//
//	String keyText = new String();
//	String decryptedPass = null;
//
//	try {
//		keyText = new String(Files.readAllBytes(Paths.get("masterPassword.txt")));
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//
//	byte[] decodedKey = Base64.getDecoder().decode(keyText);
//	keyText = new String(decodedKey);
//
//	try {
//
//		KeySpec spec = new PBEKeySpec(keyText.toCharArray(), "12345678".getBytes(), 65536, 256); // AES-256
//		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//		byte[] key = f.generateSecret(spec).getEncoded();
//		SecretKeySpec keySpec = new SecretKeySpec(Arrays.copyOf(key, 16), "AES");
//
//		byte[] iv = new byte[16];
//		new SecureRandom().nextBytes(iv);
//		IvParameterSpec ivParameter = new IvParameterSpec(iv);
//
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameter);
//
//		// OutputStream outputStream = new FileOutputStream(fileOutput);
//		// byte[] buffer = password.getBytes();
//
//		//		outputStream.write(cipher.doFinal(buffer));
//		//		outputStream.close();
//		byte[] base64Password = Base64.getMimeDecoder().decode(password.getBytes());
//		byte[] encrypted = cipher.doFinal(base64Password);
//		return new String(encrypted);
//
//
//	} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
//			| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
//			| InvalidKeySpecException ex) {
//		ex.printStackTrace();
//
//	}
//
//	return decryptedPass;
//}
