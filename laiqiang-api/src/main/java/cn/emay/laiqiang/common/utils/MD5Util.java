package cn.emay.laiqiang.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5�������
 * 
 * @author wbw
 * @version 1.0
 * @since 1.0
 */
public abstract class MD5Util {

	/**
	 * MD5����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * 
	 * @throws Exception
	 */
	public static byte[] encodeMD5(String data) throws Exception {

		// ִ����ϢժҪ
		return DigestUtils.md5(data);
	}

	/**
	 * MD5����
	 * 
	 * @param data
	 *            ����������
	 * @return byte[] ��ϢժҪ
	 * 
	 * @throws Exception
	 */
	public static String encodeMD5Hex(String data) {
		// ִ����ϢժҪ
		return DigestUtils.md5Hex(data);
	}
}

