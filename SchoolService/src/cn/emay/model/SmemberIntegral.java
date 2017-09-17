package cn.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
* 会员积分明细
*/
@Table("s_member_integral")
public class SmemberIntegral {

	/**
	 * 
	 */
	@Name
	@Column("id")
	private String id;
	/**
	 * 积分数
	 */
	@Column("integral")
	private Integer integral;
	/**
	 * 会员Id
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
	/**
	 * 描述
	 */
	@Column("description")
	private String description;
	/**
	 * 
	 */
	@Column("create_by")
	private String createBy;
	/**
	 * 
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 
	 */
	@Column("update_by")
	private String updateBy;
	/**
	 * 
	 */
	@Column("update_date")
	private java.util.Date updateDate;
	/**
	 * 
	 */
	@Column("del_flag")
	private String delFlag;
	/**
	 * 
	 */
	@Column("remarks")
	private String remarks;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}