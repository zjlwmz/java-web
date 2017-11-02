package cn.emay.laiqiang.bo;

import cn.emay.laiqiang.common.utils.DateUtils;


/**
 * @Title ¡Ù—‘
 * @author zjlwm
 * @date 2016-12-8 …œŒÁ10:55:50
 *
 */
public class LqGuestbookBO implements Comparable<LqGuestbookBO>{

	private Long id;
	
	/**
	 * ª·‘±ID
	 */
	private Long memberId;
	
	
	/**
	 * ¡Ù—‘
	 */
	private String comment;
	
	
	/**
	 * ¡Ù—‘Õº∆¨
	 */
	private String imageUrl;
	
	
	/**
	 * ¡Ù—‘ ±º‰
	 */
	private String createdTime;
	
	/**
	 * ¥∏¥ƒ⁄»›
	 */
	private String reply;
	
	/**
	 * ¥∏¥ ±º‰
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
