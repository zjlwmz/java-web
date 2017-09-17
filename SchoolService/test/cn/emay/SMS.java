package cn.emay;

import org.junit.Test;

import example.SingletonClient;

public class SMS {
	@Test
	public void test(){
		String content="【云南网上学校】验证码："+123456;
		try{
			int returnNum = SingletonClient.getClient("9SDK-EMY-0999-JBYUQ","df311175c5040abf3f6d79d5203afeda").sendSMS(new String[] { "13161454672" },content,"",5);
			System.err.println(returnNum);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
