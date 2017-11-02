package cn.emay.laiqiang.dto;

import java.util.List;

import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;

/**
 * ׬Ǯ����
 * 
 * @author lenovo
 * 
 */
public class LqTaskDTO{

	/**
	 * ����id
	 */
	private String taskId;
	
	/**
	 * ��������
	 */
	private String name;
	
	
	/**
	 * appӦ������
	 */
	private String appName;
	
	/**
	 * appӦ�ð���
	 */
	private String appPakageName;
	
	/**
	 * app��װ�����(M)(����)
	 */
	private String appVolume;
	
	/**
	 * ע�ά��(����
	 */
	private String registerReward;
	
	/**
	 * ǩ������
	 */
	private String signinReward;
	
	/**
	 * app���ص�ַ
	 */
	private String appDownloadUrl;
	
	
	/**
	 * ��������תurl</br>
	 * ��������
	 */
	private String shareUrl;
	
	
	
	
	/**
	 * appӦ��ͼ��url
	 */
	private String appIcon;
	
	
	
	/**
	 * ���
	 */
	private String briefIntro;
	
	/**
	 * ��������(1:��ͼ��2�����أ�3������)
	 */
	private Integer taskType;
	
	
	/**
	 * ��������(1:������2���ֽ�)
	 */
	private Integer rewardType;
	
	
	/**
	 * ��ͼ����
	 */
	private Integer screenshotCount;
	
	
	
	
	/**
	 * ��������(�����)
	 */
	private Double rewardQuantity;

	/**
	 * ����ʱ��
	 */
	private String publishedTime;
	
	/**
	 * ������������
	 */
	private String endDate;

	
	/**
	 * �������(��)
	 */
	private Integer auditPeriod;
	
	
	/**
	 * �����������(����)
	 */
	private Integer taskDuration;
	
	
	
	
	/**
	 * �Ѷȵȼ�(1:���ף�2��һ�㣻3������)
	 */
	private String difficultyGrade;
	
	
	/**
	 * 
	 * ��������������
	 */
	private Integer totalQuantity;
	
	
	/**
	 * ����
	 * ������ʣ�����
	 */
	private Integer surplusQuantity;
	
	
	/**
	 * ������Ӧ��ע��Url(��ͼ)
	 */
	private String registerUrl;
	
	
	
	/**
	 * ע�᷽ʽ(1:����appע�᣻2����ת��Html5��ҳע��)
	 */
	private String registerMode;
	
	/**
	 * ����ʱ��
	 * ǩ���������ʱ��(��)
	 */
	private Integer timeLimit;
	
	
	
	
	/*--------��Ա׬Ǯ������Ϣ------------*/
	
	/**
	 * ��Աid
	 */
	private Long memberId;
	
	/**
	 * ����ʱ��(����ʼʱ��)
	 */
	private String startTime;
	
	/**
	 * ��Ļ��ͼurl
	 */
	private List<LqMemberTaskScreenshotBO> screenshotList;
	
	/**
	 * ״̬(1:���룻2 : ������3�����ύ��4����˳ɹ�;5:���ʧ��)
	 */
	private Integer status=1;
	
	/**
	 * ���״̬(1:���ύ;2:����ˣ�3�����ͨ����4����˲���)
	 */
	private Integer auditStatus=1;
	
	/**
	 * ׬ȡ�����ܶ�
	 */
	private Double totalReward;
	
	
	/*----------����������Ϣ�������ԡ������------------*/
	/**
	 * ������(��ͼ)
	 */
	private String strategy;
	
	/**
	 * ������(��ͼ)url��ַ
	 */
	private String strategyUrl;
	
	
	/**
	 * ��������(��ͼ)
	 */
	private String detail;
	
	/**
	 * ��������(��ͼ) url��ַ
	 */
	private String detailUrl;
	
	/**
	 * ��������(����)
	 */
	private String rewardDetail;
	
	
	/**
	 * ˵��(��ͼ)
	 */
	private String description;
	
	/**
	 * ժҪ(����)
	 * @return
	 */
	private String summary;
	
	
	
	
	/*---------��չ�ֶ�-----------*/
	/**
	 * ʣ��ʱ�� ���룩
	 * ���residualTime>0��ʾ�����������ʣ��ʱ��
	 * ���residualTime=0��ʾ����δ�ύ
	 * ���residualTime<0��ʾ�����ѹ���
	 */
	private Long residualTime=0l;
	
	
	/**
	 * 
	 * ǩ������
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
