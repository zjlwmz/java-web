package cn.emay.laiqiang.common.security;


/**
 * SHA256÷§ Èº”√‹
 * 
 * @author 
 * 
 */
public class SHA256CredentialsDigest extends HashCredentialsDigest implements
		CredentialsDigest {
	@Override
	protected byte[] digest(byte[] input, byte[] salt) {
		return Digests.sha256(input, salt, HASH_INTERATIONS);
	}
}