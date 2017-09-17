/**
 * 
 */
package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 积分明细
 * @author zjlWm
 * @date 2015-09-08
 */
@Table("s_member_integral")
public class MemberIntegral {

	@Name
	@Column("id")
	private String id;
	
	/**
	 * 积分
	 */
	@Column("integral")
	private Integer  integral;
	
	
	/**
	 * 会员id
	 */
	@Column("member_id")
	private String memberId;
	
	
	/**
	 * 状态(1：增加积分；2：消费积分)
	 */
	@Column("status")
	private String status;
	
	/**
	 * 积分相关内容的url
	 */
	@Column("content_url")
	private String contentUrl;
	
	/**
	 * 积分内容类型
	 */
	@Column("content_type")
	private String contentType;
	
	
	@Column("create_date")
	private Date createDate;

	
	@Column("del_flag")
	private String delFlag;
	
	public MemberIntegral(){
		super();
		this.delFlag="0";
		this.createDate=new Date();
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Integer getIntegral() {
		return integral;
	}


	public void setIntegral(Integer integral) {
		this.integral = integral;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getContentUrl() {
		return contentUrl;
	}


	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
