package cn.emay.response;

import java.util.List;

import cn.emay.model.Student;

/**
 * 
 * @author zjlWm
 * @version 2015-04-13
 */
public class ResponeResultStudent extends ResponseResultBase{
	private List<Student>list;

	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}
	
}
