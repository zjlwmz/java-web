package cn.emay.laiqiang.bo;


/**
 * 
 * @Title 活动表
 * @author zjlwm
 * @date 2017-2-15 下午4:34:51
 *
 */
public class LqActivityBO {

	private Long id;
	 
	/**
	 * 活动类型(1: 首次安装；2：)
	 */
	private String activityType;
	
	
	/**
	 * 是否已发布(1:已发布;0:未发布)
	 */
	private String isPublished;
	
	/**
	 * 发布时间
	 */
	private String publishedTime;
	
	
	/**
	 * 开始时间（单位到天）
	 */
	private String startTime;
	
	
	/**
	 * 结束时间（单位到天）
	 */
	private String endTime;
	
	/**
	 * 奖励类型(1:流量；2：现金)
	 */
	private Integer rewardType;
	
	
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


	public String getActivityType() {
		return activityType;
	}


	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}


	public String getIsPublished() {
		return isPublished;
	}


	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}


	public String getPublishedTime() {
		return publishedTime;
	}


	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Integer getRewardType() {
		return rewardType;
	}


	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}


	public Double getRewardQuantity() {
		return rewardQuantity;
	}


	public void setRewardQuantity(Double rewardQuantity) {
		this.rewardQuantity = rewardQuantity;
	}
	
	
	
	
	
	
}
