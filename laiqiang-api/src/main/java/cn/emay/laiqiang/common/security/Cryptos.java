package cn.emay.laiqiang.common.security;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.emay.laiqiang.common.utils.Exceptions;


/**
 * ֧��HMAC-SHA1��Ϣǩ�� �� DES/AES�ԳƼ��ܵĹ�����.
 * 
 * ֧��Hex��Base64���ֱ��뷽ʽ.
 * 
 */
public abstract class Cryptos {

	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; //RFC2401
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;

	private static SecureRandom random = new SecureRandom();

	//-- HMAC-SHA1 funciton --//
	/**
	 * ʹ��HMAC-SHA1������Ϣǩ��, �����ֽ�����,����Ϊ20�ֽ�.
	 * 
	 * @param input ԭʼ�����ַ�����
	 * @param key HMAC-SHA1��Կ
	 */
	public static byte[] hmacSha1(byte[] input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * У��HMAC-SHA1ǩ���Ƿ���ȷ.
	 * 
	 * @param expected �Ѵ��ڵ�ǩ��
	 * @param input ԭʼ�����ַ���
	 * @param key ��Կ
	 */
	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	/**
	 * ����HMAC-SHA1��Կ,�����ֽ�����,����Ϊ160λ(20�ֽ�).
	 * HMAC-SHA1�㷨����Կ������Ҫ��, RFC2401�������ٳ���Ϊ160λ(20�ֽ�).
	 */
	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	//-- AES funciton --//
	/**
	 * ʹ��AES����ԭʼ�ַ���.
	 * 
	 * @param input ԭʼ�����ַ�����
	 * @param key ����AESҪ�����Կ
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * ʹ��AES����ԭʼ�ַ���.
	 * 
	 * @param input ԭʼ�����ַ�����
	 * @param key ����AESҪ�����Կ
	 * @param iv ��ʼ����
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * ʹ��AES�����ַ���, ����ԭʼ�ַ���.
	 * 
	 * @param input Hex����ļ����ַ���
	 * @param key ����AESҪ�����Կ
	 */
	public static String aesDecrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * ʹ��AES�����ַ���, ����ԭʼ�ַ���.
	 * 
	 * @param input Hex����ļ����ַ���
	 * @param key ����AESҪ�����Կ
	 * @param iv ��ʼ����
	 */
	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * ʹ��AES���ܻ�����ޱ����ԭʼ�ֽ�����, �����ޱ�����ֽ�������.
	 * 
	 * @param input ԭʼ�ֽ�����
	 * @param key ����AESҪ�����Կ
	 * @param mode Cipher.ENCRYPT_MODE �� Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * ʹ��AES���ܻ�����ޱ����ԭʼ�ֽ�����, �����ޱ�����ֽ�������.
	 * 
	 * @param input ԭʼ�ֽ�����
	 * @param key ����AESҪ�����Կ
	 * @param iv ��ʼ����
	 * @param mode Cipher.ENCRYPT_MODE �� Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * ����AES��Կ,�����ֽ�����, Ĭ�ϳ���Ϊ128λ(16�ֽ�).
	 */
	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	/**
	 * ����AES��Կ,��ѡ����Ϊ128,192,256λ.
	 */
	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * �����������,Ĭ�ϴ�СΪcipher.getBlockSize(), 16�ֽ�.
	 */
	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IVSIZE];
		random.nextBytes(bytes);
		return bytes;
	}
	
    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * Get the bytes of the String in UTF-8 encoded form.
     */
    public static byte[] utf8encode(CharSequence string) {
        try {
            ByteBuffer bytes = CHARSET.newEncoder().encode(CharBuffer.wrap(string));
            byte[] bytesCopy = new byte[bytes.limit()];
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());

            return bytesCopy;
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Encoding failed", e);
        }
    }

    /**
     * Decode the bytes in UTF-8 form into a String.
     */
    public static String utf8decode(byte[] bytes) {
        try {
            return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException e) {
            throw new IllegalArgumentException("Decoding failed", e);
        }
    }
}