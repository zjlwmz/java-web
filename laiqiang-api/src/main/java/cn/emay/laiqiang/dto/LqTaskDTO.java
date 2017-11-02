package cn.emay.laiqiang.dto;

import java.util.List;

import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;

/**
 * 赚钱任务
 * 
 * @author lenovo
 * 
 */
public class LqTaskDTO{

	/**
	 * 任务id
	 */
	private String taskId;
	
	/**
	 * 任务名称
	 */
	private String name;
	
	
	/**
	 * app应用名称
	 */
	private String appName;
	
	/**
	 * app应用包名
	 */
	private String appPakageName;
	
	/**
	 * app安装包体积(M)(下载)
	 */
	private String appVolume;
	
	/**
	 * 注册奖励(下载
	 */
	private String registerReward;
	
	/**
	 * 签到奖励
	 */
	private String signinReward;
	
	/**
	 * app下载地址
	 */
	private String appDownloadUrl;
	
	
	/**
	 * 分享内容转url</br>
	 * 分享链接
	 */
	private String shareUrl;
	
	
	
	
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
	 * 第三方应用注册Url(截图)
	 */
	private String registerUrl;
	
	
	
	/**
	 * 注册方式(1:下载app注册；2：跳转至Html5网页注册)
	 */
	private String registerMode;
	
	/**
	 * 体验时间
	 * 签到任务完成时限(秒)
	 */
	private Integer timeLimit;
	
	
	
	
	/*--------会员赚钱任务信息------------*/
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 申请时间(任务开始时间)
	 */
	private String startTime;
	
	/**
	 * 屏幕截图url
	 */
	private List<LqMemberTaskScreenshotBO> screenshotList;
	
	/**
	 * 状态(1:申请；2 : 工作；3：已提交；4：审核成功;5:审核失败)
	 */
	private Integer status=1;
	
	/**
	 * 审核状态(1:待提交;2:待审核；3：审核通过；4：审核驳回)
	 */
	private Integer auditStatus=1;
	
	/**
	 * 赚取奖励总额
	 */
	private Double totalReward;
	
	
	/*----------任务其他信息如任务攻略、详情等------------*/
	/**
	 * 任务攻略(截图)
	 */
	private String strategy;
	
	/**
	 * 任务攻略(截图)url地址
	 */
	private String strategyUrl;
	
	
	/**
	 * 任务详情(截图)
	 */
	private String detail;
	
	/**
	 * 任务详情(截图) url地址
	 */
	private String detailUrl;
	
	/**
	 * 奖励详情(下载)
	 */
	private String rewardDetail;
	
	
	/**
	 * 说明(截图)
	 */
	private String description;
	
	/**
	 * 摘要(下载)
	 * @return
	 */
	private String summary;
	
	
	
	
	/*---------扩展字段-----------*/
	/**
	 * 剩余时间 （秒）
	 * 如果residualTime>0表示申请任务过期剩余时间
	 * 如果residualTime=0表示任务未提交
	 * 如果residualTime<0表示任务已过期
	 */
	private Long residualTime=0l;
	
	
	/**
	 * 
	 * 签到次数
	 * 
	 */
	private Integer signCount;
	
	
	
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

	
	

	public Long getResidualTime() {
		return residualTime;
	}

	public void setResidualTime(Long residualTime) {
		this.residualTime = residualTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	

	public List<LqMemberTaskScreenshotBO> getScreenshotList() {
		return screenshotList;
	}

	public void setScreenshotList(List<LqMemberTaskScreenshotBO> screenshotList) {
		this.screenshotList = screenshotList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
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

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRewardDetail() {
		return rewardDetail;
	}

	public void setRewardDetail(String rewardDetail) {
		this.rewardDetail = rewardDetail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppPakageName() {
		return appPakageName;
	}

	public void setAppPakageName(String appPakageName) {
		this.appPakageName = appPakageName;
	}

	public String getAppVolume() {
		return appVolume;
	}

	public void setAppVolume(String appVolume) {
		this.appVolume = appVolume;
	}

	public String getRegisterReward() {
		return registerReward;
	}

	public void setRegisterReward(String registerReward) {
		this.registerReward = registerReward;
	}

	public String getSigninReward() {
		return signinReward;
	}

	public void setSigninReward(String signinReward) {
		this.signinReward = signinReward;
	}

	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getRegisterMode() {
		return registerMode;
	}

	public void setRegisterMode(String registerMode) {
		this.registerMode = registerMode;
	}

	public Integer getSignCount() {
		return signCount;
	}

	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrategyUrl() {
		return strategyUrl;
	}

	public void setStrategyUrl(String strategyUrl) {
		this.strategyUrl = strategyUrl;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Integer getTaskDuration() {
		return taskDuration;
	}

	public void setTaskDuration(Integer taskDuration) {
		this.taskDuration = taskDuration;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public Double getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(Double totalReward) {
		this.totalReward = totalReward;
	}

	public Integer getScreenshotCount() {
		return screenshotCount;
	}

	public void setScreenshotCount(Integer screenshotCount) {
		this.screenshotCount = screenshotCount;
	}

}
