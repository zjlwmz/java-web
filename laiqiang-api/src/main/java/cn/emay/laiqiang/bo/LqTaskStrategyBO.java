package cn.emay.laiqiang.bo;

/**
 * 
 * 赚钱任务其他信息
 */
public class LqTaskStrategyBO {

	private Long taskId;
	
	/**
	 * 任务攻略(截图)
	 */
	private String strategy;
	
	/**
	 * 任务攻略是否外链(0:否；1:真)
	 */
	private Integer strategyIslink;
	
	/**
	 * 任务攻略第三方链接地址
	 */
	private String strategyLink;
	
	/**
	 * 任务详情(截图)
	 */
	private String detail;
	
	/**
	 * 任务详情是否外链(0:否；1:真)
	 */
	private Integer detailIslink;
	
	/**
	 * 任务详情第三方链接地址
	 */
	private String detailLink;
	
	
	/**
	 * 奖励详情(下载)
	 */
	private String rewardDetail;
	
	/**
	 * 奖励详情是否外链(0:否；1:真)
	 */
	private Integer rewardDetailIslink;
	
	
	/**
	 * 奖励详情第三方链接地址
	 */
	private String rewardDetailLink;
	
	
	
	/**
	 * 说明(截图)
	 */
	private String description;
	
	
	/**
	 * 注册应用描述(截图，注册方式为1：下载app时)
	 */
	private String appDesc;
	
	/**
	 * 摘要(下载)
	 * @return
	 */
	private String summary;
	
	
	/**
	 * 摘要是否外链(0:否；1:真)
	 */
	private Integer summaryIslink;
	
	
	/**
	 * 摘要第三方链接地址
	 */
	private String summaryLink;
	
	
	/**
	 * 分享页面内容
	 */
	private String share;

	/**
	 * 分享页面内容是否外链(0:否；1:真)
	 */
	private Integer shareIslink;
	
	
	/**
	 * 分享页面摘要第三方链接地址
	 */
	private String shareLink;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public Integer getStrategyIslink() {
		return strategyIslink;
	}

	public void setStrategyIslink(Integer strategyIslink) {
		this.strategyIslink = strategyIslink;
	}

	public String getStrategyLink() {
		return strategyLink;
	}

	public void setStrategyLink(String strategyLink) {
		this.strategyLink = strategyLink;
	}

	public Integer getDetailIslink() {
		return detailIslink;
	}

	public void setDetailIslink(Integer detailIslink) {
		this.detailIslink = detailIslink;
	}

	public String getDetailLink() {
		return detailLink;
	}

	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}

	public Integer getRewardDetailIslink() {
		return rewardDetailIslink;
	}

	public void setRewardDetailIslink(Integer rewardDetailIslink) {
		this.rewardDetailIslink = rewardDetailIslink;
	}

	public String getRewardDetailLink() {
		return rewardDetailLink;
	}

	public void setRewardDetailLink(String rewardDetailLink) {
		this.rewardDetailLink = rewardDetailLink;
	}

	public Integer getSummaryIslink() {
		return summaryIslink;
	}

	public void setSummaryIslink(Integer summaryIslink) {
		this.summaryIslink = summaryIslink;
	}

	public String getSummaryLink() {
		return summaryLink;
	}

	public void setSummaryLink(String summaryLink) {
		this.summaryLink = summaryLink;
	}

	public Integer getShareIslink() {
		return shareIslink;
	}

	public void setShareIslink(Integer shareIslink) {
		this.shareIslink = shareIslink;
	}

	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	
	
	
	
	
}
