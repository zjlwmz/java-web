package cn.emay.laiqiang.bo;


/**
 * 
 * @Title ���
 * @author zjlwm
 * @date 2017-2-15 ����4:34:51
 *
 */
public class LqActivityBO {

	private Long id;
	 
	/**
	 * �����(1: �״ΰ�װ��2��)
	 */
	private String activityType;
	
	
	/**
	 * �Ƿ��ѷ���(1:�ѷ���;0:δ����)
	 */
	private String isPublished;
	
	/**
	 * ����ʱ��
	 */
	private String publishedTime;
	
	
	/**
	 * ��ʼʱ�䣨��λ���죩
	 */
	private String startTime;
	
	
	/**
	 * ����ʱ�䣨��λ���죩
	 */
	private String endTime;
	
	/**
	 * ��������(1:������2���ֽ�)
	 */
	private Integer rewardType;
	
	
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
