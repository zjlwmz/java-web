package cn.emay.laiqiang.common.utils;

import java.io.IOException;
import java.io.InputStream;

import cn.m.mt.sdk.api.IRes;
import cn.m.mt.sdk.impl.McnRes;

/**
 * 文件上传工具类
 * 图片查看地址
 * http://www1.m.cn/fileservice/server/files/3/code/039a1eb8-5cfc-4391-a127-9ba45c497a04.jpg
 * 
 * 
 * 上传地址
 * http://www1.m.cn/fileservice/server/3/code/039a1eb8-5cfc-4391-a127-9ba45c497a04.jpg
 * 
 */
public class ResourcesdkUtils {

	/**
	 * 正式地址
	 */
	//"http://119.29.237.71:9527/";
	
	/**
	 * 测试地址
	 */
	public static String fileUrl="http://www1.m.cn/fileservice/server/";
	
	
	/**
	 * 文件保存
	 * @param in 保存文件流
	 * @param fileUrl 保存文件服务器地址
	 * @param fileName 保存文件名称  （图片的存储的相对路径）
	 */
	public static void saveFile(InputStream in,String fileUrl,String fileName) {
		IRes mcnRes = new McnRes(3l, fileUrl);
		try {
			mcnRes.put(fileName, in);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}

	}
	
	
	/**
	 * 文件保存
	 * @param in 保存文件流
	 * @param fileUrl 保存文件服务器地址
	 * @param fileName 保存文件名称  （图片的存储的相对路径）
	 */
	public static void saveFile(byte[] data,String fileUrl,String filePath) {
		IRes mcnRes = new McnRes(3l, fileUrl);
		try {
			mcnRes.put(filePath, data);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {

		}

	}
	
	
	
	
	
}
