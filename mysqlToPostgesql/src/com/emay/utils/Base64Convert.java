package com.emay.utils;
/**
 * 编码类
 * 实现64位编码加密与解密
 * @author LB
 *
 */
public class Base64Convert {
	//Base64加密解密
	public static String encode64(String xml){
		String result=new String(Base64.encodeBase64(xml.getBytes()));
		return result;
	}
	//Base64解密
	public static String decode64(String xml){
		String result=new String(Base64.decodeBase64(xml));
		return result;
	}
}
