package cn.emay.laiqiang.common.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	/**
	 * �ַ�����
	 */
	public final static String ENCODING = "UTF-8";

	/**
	 * Base64����
	 * 
	 * @param data
	 *            ����������
	 * @return String ��������
	 * @throws Exception
	 */
	public static String encode(String data) throws Exception {

		// ִ�б���
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));

		return new String(b, ENCODING);
	}

	/**
	 * Base64��ȫ����<br>
	 * ��ѭRFC 2045ʵ��
	 * 
	 * @param data
	 *            ����������
	 * @return String ��������
	 * 
	 * @throws Exception
	 */
	public static String encodeSafe(String data) throws Exception {

		// ִ�б���
		byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);

		return new String(b, ENCODING);
	}

	/**
	 * Base64����
	 * 
	 * @param data
	 *            ����������
	 * @return String ��������
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception {

		// ִ�н���
		byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));

		return new String(b, ENCODING);
	}
}
