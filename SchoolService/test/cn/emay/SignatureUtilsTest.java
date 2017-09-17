package cn.emay;

import org.junit.Test;

import cn.emay.common.secret.AesException;
import cn.emay.common.secret.SHA1;
import cn.emay.common.util.SignatureUtils;


/***
 * 
 * @author 
 *
 */
public class SignatureUtilsTest {

	@Test
	public void test(){
		String aa=SignatureUtils.getSignature("zjlwm", "aa", "100");
//		System.out.println(aa);
		
		
		try {
			String bb=SHA1.getSHA1("zjlwm", "{\"memberld\":\"f33bc2a8da7c4d699e663677f8a32a3f\"}", "mSgJye4zWmTi1a8l");
			System.out.println(bb);
		} catch (AesException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void bb(){
		boolean b=SignatureUtils.checkSignature("zjlwm", "ada4f832770b68778aaebb1783ae4aac1398da44", "aa", "100");
		System.out.println(b);
	}
}
