/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package cn.emay.laiqiang.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * ��װ��������Ψһ��ID�㷨�Ĺ�����.
 * @author ThinkGem
 * @version 2013-01-15
 */
public class IdGen {

	private static SecureRandom random = new SecureRandom();
	
	public static String uuidNative() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * ��װJDK�Դ���UUID, ͨ��Random��������, �м���-�ָ�.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * ʹ��SecureRandom�������Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
	}

}
