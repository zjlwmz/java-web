package cn.emay.laiqiang.common.security;

import org.apache.commons.lang3.StringUtils;

import cn.emay.laiqiang.common.utils.Encodes;

/**
 * Hash֤�����
 * 
 * @author 
 * 
 */
public abstract class HashCredentialsDigest implements CredentialsDigest {
	public static final int HASH_INTERATIONS = 1024;

	/**
	 * @param plainCredentials ����
	 */
	public String digest(String plainCredentials, byte[] salt) {
		if (StringUtils.isBlank(plainCredentials)) {
			return null;
		}
		byte[] hashPassword = digest(Cryptos.utf8encode(plainCredentials), salt);
		return Encodes.encodeHex(hashPassword);
	}

	/**
	 * @param plainCredentials ����
	 * @param credentials ����
	 */
	public boolean matches(String credentials, String plainCredentials,
			byte[] salt) {
		if (StringUtils.isBlank(credentials)
				&& StringUtils.isBlank(plainCredentials)) {
			return true;
		}
		return StringUtils.equals(credentials, digest(plainCredentials, salt));
	}

	protected abstract byte[] digest(byte[] input, byte[] salt);
}
