package cn.emay.laiqiang.bo;


/**
 * 会员资金账户
 * @author lenovo
 *
 */
public class LqAccountBO {

	/**
	 * 会员ID
	 */
	private Integer memberId;
	
	/**
	 * 账户余额
	 */
	private Double balance;
	
	/**
	 * 创建时间
	 */
	private String createdTime;
	
	/**
	 * 更新时间
	 */
	private String updatedTime;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	
	
}
