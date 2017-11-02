package cn.emay.laiqiang.common.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES��ȫ�������
 * 
 * @author wbw
 * @version 1.0
 */
public abstract class DESUtil {
//	static{
//		Security.insertProviderAt(new BouncyCastleProvider(), 1);
//	}
	/**
	 * ��Կ�㷨 <br>
	 * Java 6 ֻ֧��56bit��Կ <br>
	 * Bouncy Castle ֧��64bit��Կ
	 */
	public static final String KEY_ALGORITHM = "DES";

	/**
	 * ����/�����㷨 / ����ģʽ / ��䷽ʽ
	 */
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5PADDING";

	/**
	 * ת����Կ
	 * 
	 * @param key
	 *            ��������Կ
	 * @return Key ��Կ
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {

		// ʵ����DES��Կ����
		DESKeySpec dks = new DESKeySpec(key);

		// ʵ����������Կ����
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);

		// ����������Կ
		SecretKey secretKey = keyFactory.generateSecret(dks);

		return secretKey;
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		// ��ԭ��Կ
		Key k = toKey(key);

		// ʵ����
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.DECRYPT_MODE, k);

		// ִ�в���
		return cipher.doFinal(data);
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		// ��ԭ��Կ
		Key k = toKey(key);

		// ʵ����
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.ENCRYPT_MODE, k);

		// ִ�в���
		return cipher.doFinal(data);
	}

	/**
	 * ������Կ <br>
	 * Java 6 ֻ֧��56bit��Կ <br>
	 * Bouncy Castle ֧��64bit��Կ <br>
	 * 
	 * @return byte[] ��������Կ
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {

		/*
		 * ʵ������Կ������
		 * 
		 * ��Ҫʹ��64bit��Կע���滻 �����������е�KeyGenerator.getInstance(CIPHER_ALGORITHM);
		 * �滻ΪKeyGenerator.getInstance(CIPHER_ALGORITHM, "BC");
		 */
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

		/*
		 * ��ʼ����Կ������ ��Ҫʹ��64bit��Կע���滻 ����������kg.init(56); �滻Ϊkg.init(64);
		 */
		kg.init(56, new SecureRandom());

		// ����������Կ
		SecretKey secretKey = kg.generateKey();

		// �����Կ�Ķ����Ʊ�����ʽ
		return secretKey.getEncoded();
	}
	
	public static byte[] initKey(String seed) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom secureRandom = new SecureRandom(new Base64().decode(seed));  
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
}
