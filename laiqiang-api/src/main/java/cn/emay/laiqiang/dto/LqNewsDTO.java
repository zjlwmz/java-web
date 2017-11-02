package cn.emay.laiqiang.dto;


/**
 * 图文资讯
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 下午4:09:33
 *
 */
public class LqNewsDTO {

	/**
	 * 图文咨询id
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 封面图片url
	 */
	private String coverUrl;


	/**
	 * 简介
	 */
	private String briefIntro;
	
	
	/**
	 * 排序
	 */
	private Integer displayorder;

	/**
	 * 类型(1、历史活动；2：更多福利)
	 */
	private String newsType;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 开始时间
	 */
	private String endTime;

	/**
	 * 富文本展示url
	 */
	private String detailUrl;

	
	/**
	 * 只限历史活动使用
	 * 是否过期
	 * 0 未过期 ;1已过期
	 */
	private String overdue="0";
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}


	public Integer getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(Integer displayorder) {
		this.displayorder = displayorder;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
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

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBriefIntro() {
		return briefIntro;
	}

	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}

	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	
	
	
	
}
