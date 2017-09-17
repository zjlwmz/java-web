package cn.emay.common.security;

import org.nutz.ioc.loader.annotation.IocBean;


/**
 * SHA1证书加密
 * 
 * @author 
 * 
 */
@IocBean(name="credentialsDigest")
public class SHA1CredentialsDigest extends HashCredentialsDigest {
	@Override
	protected byte[] digest(byte[] input, byte[] salt) {
		return Digests.sha1(input, salt, HASH_INTERATIONS);
	}
}
