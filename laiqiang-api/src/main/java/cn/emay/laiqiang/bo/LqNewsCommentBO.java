package cn.emay.laiqiang.bo;


/**
 * @Title ����
 * @author zjlwm
 * @date 2016-12-7 ����11:37:17
 *
 */
public class LqNewsCommentBO {

	private String id;
	
	
	private Long memberId;
	
	private String newsId;
	
	/**
	 * ��������
	 */
	private String comment;
	
	
	/**
	 * ����ʱ��
	 */
	private String createdTime;
	
	
	/**
	 * ���״̬(2:����ˣ�3�����ͨ����4����˲���)
	 */
	private Integer auditStatus;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNewsId() {
		return newsId;
	}


	public void setNewsId(String newsId) {
		this.newsId = newsId;
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


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}


	public Integer getAuditStatus() {
		return auditStatus;
	}


	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}


	public Long getMemberId() {
		return memberId;
	}


	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
	
}
