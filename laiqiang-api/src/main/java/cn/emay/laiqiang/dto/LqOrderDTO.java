package cn.emay.laiqiang.dto;

/**
 * @Title  ����DTO
 * @author zjlwm
 * @date 2016-12-18 ����4:04:44
 */
public class LqOrderDTO {

	
	/**
	 * �ֻ���
	 */
	private String mdn;
	
	
	/**
	 * ���
	 */
	private Integer money;
	
	
	/**
	 * �û���ֵ����
	 */
	private Integer userflow;
	
	/**
	 * ��ֵ�ɹ�����
	 */
	private Integer suflow;
	
	
	/**
	 * ��Ӫ��
	 */
	private String operator;


	public String getMdn() {
		return mdn;
	}


	public void setMdn(String mdn) {
		this.mdn = mdn;
	}


	public Integer getMoney() {
		return money;
	}


	public void setMoney(Integer money) {
		this.money = money;
	}


	public Integer getUserflow() {
		return userflow;
	}


	public void setUserflow(Integer userflow) {
		this.userflow = userflow;
	}


	public Integer getSuflow() {
		return suflow;
	}


	public void setSuflow(Integer suflow) {
		this.suflow = suflow;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	
	
	
}
