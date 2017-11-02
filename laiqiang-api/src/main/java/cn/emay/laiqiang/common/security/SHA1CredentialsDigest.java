package cn.emay.laiqiang.common.security;

import org.springframework.stereotype.Service;



/**
 * SHA1÷§ Èº”√‹
 * 
 * @author 
 * 
 */
@Service(value="credentialsDigest")
public class SHA1CredentialsDigest extends HashCredentialsDigest {
	@Override
	protected byte[] digest(byte[] input, byte[] salt) {
		return Digests.sha1(input, salt, HASH_INTERATIONS);
	}
}
