package cn.emay.laiqiang.bo;

/**
 * @Title 接口调用日志
 * @author zjlwm
 * @date 2016-12-29 下午4:47:05
 *
 */
public class LqInterfaceLogBO {

	/**
	 * 接口名称
	 */
	private String interfaceName;
	
	/**
	 * 错误描述 
	 */
	private String description;
	
	/**
	 * 系统错误信息
	 */
	private String errorMessage;
	
	/**
	 * 创建时间
	 */
	private String createdTime;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
	
	
}
