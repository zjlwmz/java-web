package cn.emay.common.response;

import java.util.List;

import cn.emay.model.Classes;

public class ResponeResultClasses extends ResponseResultBase{
	private List<Classes>list;
	
	public List<Classes> getList() {
		return list;
	}

	public void setList(List<Classes> list) {
		this.list = list;
	}
}
