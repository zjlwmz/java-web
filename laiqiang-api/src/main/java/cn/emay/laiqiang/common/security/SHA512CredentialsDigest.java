package cn.emay.laiqiang.common.security;


/**
 * SHA512÷§ Èº”√‹
 * 
 * @author 
 * 
 */
public class SHA512CredentialsDigest extends HashCredentialsDigest implements
		CredentialsDigest {
	@Override
	protected byte[] digest(byte[] input, byte[] salt) {
		return Digests.sha512(input, salt, HASH_INTERATIONS);
	}
}