package cn.emay.laiqiang.bo;


/**
 * �ҵ���Ϣ
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 ����5:28:40
 *
 */
public class LqMemberMessageBO {

	private Long id;
	
	/**
	 * ��Աid
	 */
	private Long memberId;
	
	/**
	 * ��Ϣ����
	 */
	private String title;
	
	
	/**
	 * ���ݼ��
	 */
	private String briefintro;
	
	/**
	 * ��Ϣ����
	 */
	private String content;
	
	
	/**
	 * �Ƿ��Ѷ�
	 */
	private Integer isReaded;
	
	/**
	 * ����ʱ��
	 */
	private String publishTime;
	
	
	/**
	 * Ⱥ����Ϣid
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
