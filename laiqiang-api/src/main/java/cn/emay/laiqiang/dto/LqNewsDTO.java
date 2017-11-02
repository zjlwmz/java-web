package cn.emay.laiqiang.dto;


/**
 * ͼ����Ѷ
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 ����4:09:33
 *
 */
public class LqNewsDTO {

	/**
	 * ͼ����ѯid
	 */
	private Integer id;
	/**
	 * ����
	 */
	private String title;

	/**
	 * ����
	 */
	private String author;

	/**
	 * ����ͼƬurl
	 */
	private String coverUrl;


	/**
	 * ���
	 */
	private String briefIntro;
	
	
	/**
	 * ����
	 */
	private Integer displayorder;

	/**
	 * ����(1����ʷ���2�����ร��)
	 */
	private String newsType;

	/**
	 * ��ʼʱ��
	 */
	private String startTime;

	/**
	 * ��ʼʱ��
	 */
	private String endTime;

	/**
	 * ���ı�չʾurl
	 */
	private String detailUrl;

	
	/**
	 * ֻ����ʷ�ʹ��
	 * �Ƿ����
	 * 0 δ���� ;1�ѹ���
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
