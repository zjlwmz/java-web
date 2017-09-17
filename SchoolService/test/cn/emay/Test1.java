package cn.emay;

import java.util.ArrayList;
import java.util.List;


public class Test1 {

	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();    
		list.add("a1");    
		list.add("a2");    
		String[] toBeStored = list.toArray(new String[list.size()]);    
		System.out.println(toBeStored.toString());
		for(String s : toBeStored) {    
		     System.out.println(s);    
		}
		
	}
}
