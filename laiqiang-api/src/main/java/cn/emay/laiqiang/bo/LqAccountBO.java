package cn.emay.laiqiang.bo;


/**
 * ��Ա�ʽ��˻�
 * @author lenovo
 *
 */
public class LqAccountBO {

	/**
	 * ��ԱID
	 */
	private Integer memberId;
	
	/**
	 * �˻����
	 */
	private Double balance;
	
	/**
	 * ����ʱ��
	 */
	private String createdTime;
	
	/**
	 * ����ʱ��
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
