package cn.emay.laiqiang.bo;

/**
 * 图文资讯
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 下午2:42:30
 *
 */
public class LqNewsBO {

	private Long id;
	
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
	 * 内容是否外链(0:否；1:真)
	 */
	private String isLink;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 第三方链接地址
	 */
	private String link;
	
	
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

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getBriefIntro() {
		return briefIntro;
	}

	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}

	public String getIsLink() {
		return isLink;
	}

	public void setIsLink(String isLink) {
		this.isLink = isLink;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	
}
