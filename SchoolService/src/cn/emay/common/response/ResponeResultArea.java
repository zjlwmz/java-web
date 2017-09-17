package cn.emay.common.response;

import java.util.List;
import java.util.Map;


/**
 * 区域列表返回数据
 * @author Administrator
 *
 */
public class ResponeResultArea  extends ResponseResultBase{
	
	private List<Map<String,Object>>list;
	
	public List<Map<String,Object>> getList() {
		return list;
	}

	public void setList(List<Map<String,Object>> list) {
		this.list = list;
	}

}
