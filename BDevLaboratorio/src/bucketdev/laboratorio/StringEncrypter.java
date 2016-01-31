package bucketdev.laboratorio;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

//KEY SPECIFICATIONS
import java.security.spec.KeySpec;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

//EXCEPTIONS
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

public class StringEncrypter {
	private static Cipher ecipher;
	private static Cipher dcipher;
	private static final String passPhrase = "BDEV_LABORATORIO";

	static {
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3,
				(byte) 0x03 };

		int iterationCount = 19;

		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

		} catch (InvalidAlgorithmParameterException e) {
			System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
		} catch (InvalidKeySpecException e) {
			System.out.println("EXCEPTION: InvalidKeySpecException");
		} catch (NoSuchPaddingException e) {
			System.out.println("EXCEPTION: NoSuchPaddingException");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("EXCEPTION: NoSuchAlgorithmException");
		} catch (InvalidKeyException e) {
			System.out.println("EXCEPTION: InvalidKeyException");
		}
	}

	public static String encrypt(String str) {
		try {
			byte[] utf8 = str.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);

			return new sun.misc.BASE64Encoder().encode(enc);

		} catch (BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public static String decrypt(String str) {

		try {
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF8");

		} catch (BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		return null;
	}
}
