package cn.emay;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MyTest {

	public void test1(){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher matcher=pattern.matcher("13161454672"); 
		if(matcher.matches()){
			System.out.println("是");
		}else{
			System.out.println("不是");
		}
	}
	
	
	
	
	@Test
	public void test2(){
		 List<String> list = new ArrayList<String>();
		  list.add("草莓");         //向列表中添加数据
		  list.add("香蕉");        //向列表中添加数据
		  list.add("菠萝");        //向列表中添加数据
		  for (int i = 0; i < list.size(); i++) {    //通过循环输出列表中的内容
		  System.out.println(i + ":" + list.get(i));
		  }
		  String o = "苹果";
		  if(!list.contains(o)){
			  list.add(o);
		  }
		  System.out.println("list对象中是否包含元素" + o + ":" + list.contains(o));
		//判断字符串中是否包含指定字符串对象
		  
		  System.out.println("list对象中是否包含元素" + o + ":" + list.contains("草莓"));
	}
	
	
	@Test
	public void test3() {
		String str1 = new String("abcde");
		String str2 = new String("abcde");
		String str3 = new String("abcde");
		String str4 = new String("abcde");
		String str5 = new String("abcde");
		List list = new ArrayList();

		list.add(str1);
		list.add(str2);
		list.add(str3);
		list.add(str4);
		list.add(str5);

		System.out.println("list.size()=" + list.size());
		for (int i = 0; i < list.size(); i++) {
			if (((String) list.get(i)).startsWith("abcde")) {
				list.remove(i);
			}
		}

		
		System.out.println("after remove:list.size()=" + list.size());
	}
	
	
	
	@Test
	public void test4() {
		String str1 = new String("abcde");
		String str2 = new String("abcde");
		String str3 = new String("abcde");
		String str4 = new String("abcde");
		String str5 = new String("abcde");
		List list = new ArrayList();

		list.add(str1);
		list.add(str2);
		list.add(str3);
		list.add(str4);
		list.add(str5);


		
		System.out.println("after remove:list.size()=" + list.size());
	}
}
