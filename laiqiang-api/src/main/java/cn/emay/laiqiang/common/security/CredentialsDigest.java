package cn.emay.laiqiang.common.security;

/**
 * ֤�����
 * 
 * @author 
 * 
 */
public interface CredentialsDigest {
	public String digest(String plainCredentials, byte[] salt);

	public boolean matches(String credentials, String plainCredentials,
			byte[] salt);
}
