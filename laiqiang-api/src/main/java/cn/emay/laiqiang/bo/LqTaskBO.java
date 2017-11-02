package cn.emay.laiqiang.bo;

/**
 * 赚钱任务
 * @author zjlwm
 * @date 2016-11-25
 */
public class LqTaskBO {

	private String id;
	
	/**
	 * 任务名称
	 */
	private String name;
	
	
	/**
	 * app应用图标url
	 */
	private String appIcon;
	
	/**
	 * 简介
	 */
	private String briefIntro;
	
	/**
	 * 任务类型(1:截图；2：下载；3：分享)
	 */
	private Integer taskType;
	
	
	/**
	 * 奖励类型(1:流量；2：现金)
	 */
	private Integer rewardType;
	
	
	/**
	 * 截图个数
	 */
	private Integer screenshotCount;
	
	
	/**
	 * 奖励数额(奖金额)
	 */
	private Double rewardQuantity;

	/**
	 * 发布时间
	 */
	private String publishedTime;
	
	/**
	 * 发布结束日期
	 */
	private String endDate;

	
	/**
	 * 审核周期(天)
	 */
	private Integer auditPeriod;
	
	/**
	 * 任务完成期限(分钟)
	 */
	private Integer taskDuration;
	
	
	/**
	 * 难度等级(1:容易；2：一般；3：困难)
	 */
	private String difficultyGrade;
	
	
	/**
	 * 
	 * 可申请总量次数
	 */
	private Integer totalQuantity;
	
	
	/**
	 * 余量
	 * 可申请剩余次数
	 */
	private Integer surplusQuantity;
	
	
	/**
	 * 第三方应用注册Url
	 */
	private String registerUrl;
	
	
	/**
	 * 注册方式(1:下载app注册；2：跳转至Html5网页注册)
	 */
	private String registerMode;
	
	
	
	
	/**
	 * 任务完成时限(小时)
	 */
	private Integer timeLimit;
	
	
	
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
	 * 注册奖励(下载
	 */
	private String registerReward;
	
	/**
	 * 签到奖励
	 */
	private Double signinReward;
	
	/**
	 * app下载地址
	 */
	private String appDownloadUrl;
	
	/**
	 * 合作伙伴id
	 */
	private Long cpId;
	
	
	/**
	 * 有效签到期限(下载后多少天内)
	 */
	private Integer signinDays;
	
	/**
	 * 分享链接
	 */
	private String shareLink;
	
	
	
	
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getBriefIntro() {
		return briefIntro;
	}


	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}


	public Integer getTaskType() {
		return taskType;
	}


	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
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


	public String getPublishedTime() {
		return publishedTime;
	}


	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Integer getAuditPeriod() {
		return auditPeriod;
	}


	public void setAuditPeriod(Integer auditPeriod) {
		this.auditPeriod = auditPeriod;
	}


	public String getDifficultyGrade() {
		return difficultyGrade;
	}


	public void setDifficultyGrade(String difficultyGrade) {
		this.difficultyGrade = difficultyGrade;
	}


	public Integer getTotalQuantity() {
		return totalQuantity;
	}


	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}


	public Integer getSurplusQuantity() {
		return surplusQuantity;
	}


	public void setSurplusQuantity(Integer surplusQuantity) {
		this.surplusQuantity = surplusQuantity;
	}


	public String getRegisterUrl() {
		return registerUrl;
	}


	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}


	public Integer getTimeLimit() {
		return timeLimit;
	}


	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
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


	public String getRegisterReward() {
		return registerReward;
	}


	public void setRegisterReward(String registerReward) {
		this.registerReward = registerReward;
	}


	public Double getSigninReward() {
		return signinReward;
	}


	public void setSigninReward(Double signinReward) {
		this.signinReward = signinReward;
	}


	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}


	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}


	public Long getCpId() {
		return cpId;
	}


	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}


	public Integer getSigninDays() {
		return signinDays;
	}


	public void setSigninDays(Integer signinDays) {
		this.signinDays = signinDays;
	}


	public Integer getTaskDuration() {
		return taskDuration;
	}


	public void setTaskDuration(Integer taskDuration) {
		this.taskDuration = taskDuration;
	}


	public String getRegisterMode() {
		return registerMode;
	}


	public void setRegisterMode(String registerMode) {
		this.registerMode = registerMode;
	}


	public String getShareLink() {
		return shareLink;
	}


	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}


	public Integer getScreenshotCount() {
		return screenshotCount;
	}


	public void setScreenshotCount(Integer screenshotCount) {
		this.screenshotCount = screenshotCount;
	}


	
	
	
}
