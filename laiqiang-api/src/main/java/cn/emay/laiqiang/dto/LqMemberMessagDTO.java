package cn.emay.laiqiang.dto;

import cn.emay.laiqiang.common.utils.DateUtils;

/**
 * 
 * @Title �ҵ���Ϣ
 * @author zjlwm
 * @date 2016-12-9 ����4:39:01
 * 
 */
public class LqMemberMessagDTO implements Comparable<LqMemberMessagDTO>{

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
	 * ��Ϣ��������url
	 */
	private String messageDetailUrl;

	/**
	 * �Ƿ��Ѷ�
	 */
	private Integer isReaded;

	/**
	 * ����ʱ��
	 */
	private String publishTime;


	/**
	 * ��Ϣ���� 0���� 1Ⱥ��
	 */
	private String messageType;


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

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageDetailUrl() {
		return messageDetailUrl;
	}

	public void setMessageDetailUrl(String messageDetailUrl) {
		this.messageDetailUrl = messageDetailUrl;
	}

	public String getBriefintro() {
		return briefintro;
	}

	public void setBriefintro(String briefintro) {
		this.briefintro = briefintro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int compareTo(LqMemberMessagDTO o) {
		LqMemberMessagDTO lqMemberMessagDTO=o;
		Long starttime1=DateUtils.parseDate(lqMemberMessagDTO.getPublishTime()).getTime();
		Long starttime2=DateUtils.parseDate(this.publishTime).getTime();
		return starttime1.compareTo(starttime2);
	}


	
	
	
}
