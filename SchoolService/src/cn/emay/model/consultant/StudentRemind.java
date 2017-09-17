package cn.emay.model.consultant;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 家长提醒
 * @author  zjlWm
 * @version 2015-03-24
 */
@Table("s_student_remind")
public class StudentRemind {

	@Name
	@Column(value="id")
	private String id;
	
	/**
	 * 会员id【家长】
	 */
	@Column(value="member_id")
	private String memberId;
	
	/**
	 * 会员名称
	 */
	private String memberName;
	
	private String hxid;
	
	/**
	 * 提醒内容
	 */
	@Column(value="content")
	private String content;
	
	/*
	 * 提醒时间
	 */
	@Column(value="remind_date")
	private Date remindDate;
	
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="create_by")
	private String createBy;
	
	
	@Column(value="del_flag")
	private String delFlag="0";

	/**
	 * 提醒状态
	 * 0：未处理；1已处理
	 */
	@Column(value="status")
	private String status="0";
	
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getRemindDate() {
		return remindDate;
	}


	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getHxid() {
		return hxid;
	}


	public void setHxid(String hxid) {
		this.hxid = hxid;
	}
	
	
	
}
