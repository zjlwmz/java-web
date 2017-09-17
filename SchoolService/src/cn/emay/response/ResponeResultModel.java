package cn.emay.response;

import java.util.Map;

/**
 * 请求数据返回状态
 * @author zjlWm
 *
 */
public class ResponeResultModel {
	private boolean success=false;
	
	
	private Map<String,Object> data;
	
	
	private String message;
	
	
	private Integer total;
	
	private Integer count;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	
}
