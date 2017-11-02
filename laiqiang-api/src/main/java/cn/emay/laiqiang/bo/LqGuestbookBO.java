package cn.emay.laiqiang.bo;

import cn.emay.laiqiang.common.utils.DateUtils;


/**
 * @Title ����
 * @author zjlwm
 * @date 2016-12-8 ����10:55:50
 *
 */
public class LqGuestbookBO implements Comparable<LqGuestbookBO>{

	private Long id;
	
	/**
	 * ��ԱID
	 */
	private Long memberId;
	
	
	/**
	 * ����
	 */
	private String comment;
	
	
	/**
	 * ����ͼƬ
	 */
	private String imageUrl;
	
	
	/**
	 * ����ʱ��
	 */
	private String createdTime;
	
	/**
	 * ������
	 */
	private String reply;
	
	/**
	 * ��ʱ��
	 */
	private String replyTime;
	

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	@Override
	public int compareTo(LqGuestbookBO lqGuestbookBO) {
		Long starttime1=DateUtils.parseDate(lqGuestbookBO.getCreatedTime()).getTime();
		Long starttime2=DateUtils.parseDate(this.getCreatedTime()).getTime();
		return starttime1.compareTo(starttime2);
	}
	
}
