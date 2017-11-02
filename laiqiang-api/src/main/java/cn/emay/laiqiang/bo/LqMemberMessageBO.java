package cn.emay.laiqiang.bo;


/**
 * 我的消息
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 下午5:28:40
 *
 */
public class LqMemberMessageBO {

	private Long id;
	
	/**
	 * 会员id
	 */
	private Long memberId;
	
	/**
	 * 消息标题
	 */
	private String title;
	
	
	/**
	 * 内容简介
	 */
	private String briefintro;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	
	/**
	 * 是否已读
	 */
	private Integer isReaded;
	
	/**
	 * 创建时间
	 */
	private String publishTime;
	
	
	/**
	 * 群发消息id
	 */
	private Long messagePushId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(Integer isReaded) {
		this.isReaded = isReaded;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public Long getMessagePushId() {
		return messagePushId;
	}

	public void setMessagePushId(Long messagePushId) {
		this.messagePushId = messagePushId;
	}

	public String getBriefintro() {
		return briefintro;
	}

	public void setBriefintro(String briefintro) {
		this.briefintro = briefintro;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
}
