package cn.emay.laiqiang.bo;


/**
 * 
 * @Title ���־��
 * @author zjlwm
 * @date 2017-2-15 ����4:39:53
 *
 */
public class LqActivityLogBO {

	
	private Long id;
	
	/**
	 * �ID
	 */
	private Long activityId;
	
	/**
	 * �ID
	 */
	private Long memberId;
	
	
	/**
	 * ��������(1:������2���ֽ�)
	 */
	private String rewardType;
	
	
	/**
	 * �������
	 */
	private Double rewardQuantity;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getActivityId() {
		return activityId;
	}


	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public String getRewardType() {
		return rewardType;
	}


	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}


	public Double getRewardQuantity() {
		return rewardQuantity;
	}


	public void setRewardQuantity(Double rewardQuantity) {
		this.rewardQuantity = rewardQuantity;
	}
	
	
	
	
	
}
