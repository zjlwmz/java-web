package cn.emay.laiqiang.common.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;

import cn.emay.laiqiang.common.utils.Exceptions;


/**
 * ֧��SHA-1/MD5��ϢժҪ�Ĺ�����.
 * 
 * ����ByteSource���ɽ�һ��������ΪHex, Base64��UrlSafeBase64
 * 
 */
public class Digests {

	private static final String SHA1 = "SHA-1";
	private static final String SHA256 = "sha-256";
	private static final String SHA512 = "sha-512";
	private static final String MD5 = "MD5";

	private static SecureRandom random = new SecureRandom();

	/**
	 * �������ַ�������sha1ɢ��.
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}
	
	/**
	 * �������ַ�������sha256ɢ��.
	 */
	public static byte[] sha256(byte[] input) {
		return digest(input, SHA256, null, 1);
	}

	public static byte[] sha256(byte[] input, byte[] salt) {
		return digest(input, SHA256, salt, 1);
	}

	public static byte[] sha256(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA256, salt, iterations);
	}
	
	/**
	 * �������ַ�������sha512ɢ��.
	 */
	public static byte[] sha512(byte[] input) {
		return digest(input, SHA512, null, 1);
	}

	public static byte[] sha512(byte[] input, byte[] salt) {
		return digest(input, SHA512, salt, 1);
	}

	public static byte[] sha512(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA512, salt, iterations);
	}

	/**
	 * ���ַ�������ɢ��, ֧��md5��sha1�㷨.
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt,
			int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * ���������Byte[]��Ϊsalt.
	 * 
	 * @param numBytes
	 *            byte����Ĵ�С
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0,
				"numBytes argument must be a positive integer (1 or larger)",
				numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * ���ļ�����md5ɢ��.
	 */
	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	/**
	 * ���ļ�����sha1ɢ��.
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	private static byte[] digest(InputStream input, String algorithm)
			throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

}