package cn.emay.laiqiang.dto;

/**
 * @Title  订单DTO
 * @author zjlwm
 * @date 2016-12-18 下午4:04:44
 */
public class LqOrderDTO {

	
	/**
	 * 手机号
	 */
	private String mdn;
	
	
	/**
	 * 金额
	 */
	private Integer money;
	
	
	/**
	 * 用户充值流量
	 */
	private Integer userflow;
	
	/**
	 * 充值成功流量
	 */
	private Integer suflow;
	
	
	/**
	 * 运营商
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
