package cn.emay.laiqiang.dto;

/**
 * ǩ����ȡ����
 * @Title 
 * @author zjlwm
 * @date 2016-12-2 ����2:47:56
 *
 */
public class TaskIncomeDTO {

	
	/**
	 * ׬Ǯ����id
	 */
	private Long taskId;
	
	
	/**
	 * appӦ��ͼ��url
	 */
	private String appIcon;
	
	/**
	 * ��������
	 */
	private String name;
	
	/**
	 * appӦ������
	 */
	private String appName;
	
	/**
	 * app��װ�����(M)(����)
	 */
	private String appVolume;
	
	
	/**
	 * appӦ�ð���
	 */
	private String appPakageName;
	
	
	/**
	 * ��������(1:��ͼ��2�����أ�3������)
	 */
	private String taskType;
	
	
	/**
	 * ��������(1:������2���ֽ�)
	 */
	private Integer rewardType;
	
	
	/**
	 * ��������(�����)
	 */
	private double rewardQuantity;
	
	
	/**
	 * ������ ���
	 */
	private Double totalMoney ;
	
	/**
	 * ����������
	 */
	private Double totalQuantity ;
	
	/**
	 * �Ƿ�ǩ��
	 * 0 δǩ����1��ǩ��
	 */
	private String isSign="0";
	

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public double getRewardQuantity() {
		return rewardQuantity;
	}

	public void setRewardQuantity(double rewardQuantity) {
		this.rewardQuantity = rewardQuantity;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVolume() {
		return appVolume;
	}

	public void setAppVolume(String appVolume) {
		this.appVolume = appVolume;
	}

	public String getAppPakageName() {
		return appPakageName;
	}

	public void setAppPakageName(String appPakageName) {
		this.appPakageName = appPakageName;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	
	
	
	
}
