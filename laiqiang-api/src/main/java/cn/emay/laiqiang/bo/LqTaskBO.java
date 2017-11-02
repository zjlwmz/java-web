package cn.emay.laiqiang.bo;

/**
 * ׬Ǯ����
 * @author zjlwm
 * @date 2016-11-25
 */
public class LqTaskBO {

	private String id;
	
	/**
	 * ��������
	 */
	private String name;
	
	
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
	 * ������Ӧ��ע��Url
	 */
	private String registerUrl;
	
	
	/**
	 * ע�᷽ʽ(1:����appע�᣻2����ת��Html5��ҳע��)
	 */
	private String registerMode;
	
	
	
	
	/**
	 * �������ʱ��(Сʱ)
	 */
	private Integer timeLimit;
	
	
	
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
	 * ע�ά��(����
	 */
	private String registerReward;
	
	/**
	 * ǩ������
	 */
	private Double signinReward;
	
	/**
	 * app���ص�ַ
	 */
	private String appDownloadUrl;
	
	/**
	 * �������id
	 */
	private Long cpId;
	
	
	/**
	 * ��Чǩ������(���غ��������)
	 */
	private Integer signinDays;
	
	/**
	 * ��������
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
