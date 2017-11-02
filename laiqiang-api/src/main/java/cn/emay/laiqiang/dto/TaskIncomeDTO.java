package cn.emay.laiqiang.dto;

/**
 * 签到领取收益
 * @Title 
 * @author zjlwm
 * @date 2016-12-2 下午2:47:56
 *
 */
public class TaskIncomeDTO {

	
	/**
	 * 赚钱任务id
	 */
	private Long taskId;
	
	
	/**
	 * app应用图标url
	 */
	private String appIcon;
	
	/**
	 * 任务名称
	 */
	private String name;
	
	/**
	 * app应用名称
	 */
	private String appName;
	
	/**
	 * app安装包体积(M)(下载)
	 */
	private String appVolume;
	
	
	/**
	 * app应用包名
	 */
	private String appPakageName;
	
	
	/**
	 * 任务类型(1:截图；2：下载；3：分享)
	 */
	private String taskType;
	
	
	/**
	 * 奖励类型(1:流量；2：现金)
	 */
	private Integer rewardType;
	
	
	/**
	 * 奖励数额(奖金额)
	 */
	private double rewardQuantity;
	
	
	/**
	 * 总收益 金额
	 */
	private Double totalMoney ;
	
	/**
	 * 总流量收益
	 */
	private Double totalQuantity ;
	
	/**
	 * 是否签到
	 * 0 未签到、1已签到
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
