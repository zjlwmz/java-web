package com.emay.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * 百度省、城市、区域接口
 * http://api.map.baidu.com/shangquan/forward/?qt=sub_area_list&ext=1&level=1&areacode=1&business_flag=0&callback=
 * @author Administrator
 *
 */
public class RequestUtils {
	 public static String testPost(Integer areacode) throws IOException {  
	        StringBuffer document = new StringBuffer();
				URL url = new URL("http://api.map.baidu.com/shangquan/forward/?qt=sub_area_list&ext=1&level=1&areacode="+areacode+"&business_flag=0&callback=");//远程url
				URLConnection conn = url.openConnection();
			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    String line = null;
			    while ((line = reader.readLine()) != null)
			       document.append(line + " ");
			    reader.close();
	        return document.toString();
	    } 
	 
	 
	 public static void main(String[] args) {
		 try {
			String data=RequestUtils.testPost(1);
			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
