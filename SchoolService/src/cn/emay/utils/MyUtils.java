package cn.emay.utils;

import javax.servlet.ServletContext;

public class MyUtils {
	private ServletContext sc;      
	
	public String getPath(String path) {         
		return sc.getRealPath(path);     
	} 
}