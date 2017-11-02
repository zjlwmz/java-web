package cn.emay.laiqiang.bo;


/**
 * 
 * @Title 活动日志表
 * @author zjlwm
 * @date 2017-2-15 下午4:39:53
 *
 */
public class LqActivityLogBO {

	
	private Long id;
	
	/**
	 * 活动ID
	 */
	private Long activityId;
	
	/**
	 * 活动ID
	 */
	private Long memberId;
	
	
	/**
	 * 奖励类型(1:流量；2：现金)
	 */
	private String rewardType;
	
	
	/**
	 * 奖励额度
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
