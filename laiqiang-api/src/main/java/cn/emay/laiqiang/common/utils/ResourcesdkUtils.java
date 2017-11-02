package cn.emay.laiqiang.common.utils;

import java.io.IOException;
import java.io.InputStream;

import cn.m.mt.sdk.api.IRes;
import cn.m.mt.sdk.impl.McnRes;

/**
 * �ļ��ϴ�������
 * ͼƬ�鿴��ַ
 * http://www1.m.cn/fileservice/server/files/3/code/039a1eb8-5cfc-4391-a127-9ba45c497a04.jpg
 * 
 * 
 * �ϴ���ַ
 * http://www1.m.cn/fileservice/server/3/code/039a1eb8-5cfc-4391-a127-9ba45c497a04.jpg
 * 
 */
public class ResourcesdkUtils {

	/**
	 * ��ʽ��ַ
	 */
	//"http://119.29.237.71:9527/";
	
	/**
	 * ���Ե�ַ
	 */
	public static String fileUrl="http://www1.m.cn/fileservice/server/";
	
	
	/**
	 * �ļ�����
	 * @param in �����ļ���
	 * @param fileUrl �����ļ���������ַ
	 * @param fileName �����ļ�����  ��ͼƬ�Ĵ洢�����·����
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
	 * �ļ�����
	 * @param in �����ļ���
	 * @param fileUrl �����ļ���������ַ
	 * @param fileName �����ļ�����  ��ͼƬ�Ĵ洢�����·����
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
