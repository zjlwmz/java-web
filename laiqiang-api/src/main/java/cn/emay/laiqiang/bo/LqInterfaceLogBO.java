package cn.emay.laiqiang.bo;

/**
 * @Title �ӿڵ�����־
 * @author zjlwm
 * @date 2016-12-29 ����4:47:05
 *
 */
public class LqInterfaceLogBO {

	/**
	 * �ӿ�����
	 */
	private String interfaceName;
	
	/**
	 * �������� 
	 */
	private String description;
	
	/**
	 * ϵͳ������Ϣ
	 */
	private String errorMessage;
	
	/**
	 * ����ʱ��
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
