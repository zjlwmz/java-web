package cn.emay.common.security;

/**
 * 证书加密
 * 
 * @author 
 * 
 */
public interface CredentialsDigest {
	public String digest(String plainCredentials, byte[] salt);

	public boolean matches(String credentials, String plainCredentials,
			byte[] salt);
}
