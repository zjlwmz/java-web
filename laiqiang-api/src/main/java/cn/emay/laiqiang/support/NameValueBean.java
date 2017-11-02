package cn.emay.laiqiang.support;

public class NameValueBean {
	String name;
	Integer value;
	
	public NameValueBean(int value,String name) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}	
}
