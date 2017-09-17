package cn.emay.common.response;

import java.util.List;

import cn.emay.model.School;

public class ResponeResultSchool  extends ResponseResultBase{

	private List<School>list;
	
	public List<School> getList() {
		return list;
	}

	public void setList(List<School> list) {
		this.list = list;
	}
}
