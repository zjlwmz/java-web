package cn.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import cn.m.mt.sdk.api.IRes;
import cn.m.mt.sdk.impl.McnRes;

public class MchResTest {

	public static void main(String[] args) {
		IRes mcnRes = new McnRes(2,"http://www1.m.cn/fileservice/server/");
		FileInputStream is=null;
		try {
			is = new FileInputStream("c:/arrow.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mcnRes.put("code/039a1eb8-5cfc-4391-a127-9ba45c497a04.jpg", is);
	}
}
