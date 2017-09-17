package com.emay.sfa;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_device")
public class SysDevice {

	/**
	 * 
	 */
	@Id
	@Column("id")
	private Integer id;
	/**
	 * 设备号
	 */
	@Column("imel")
	private String imel;
	/**
	 * 
	 */
	@Column("createdon")
	private java.util.Date createdon;
	/**
	 * 
	 */
	@Column("createdby")
	private String createdby;
	/**
	 * 
	 */
	@Column("modifiedon")
	private java.util.Date modifiedon;
	/**
	 * 
	 */
	@Column("modifiedby")
	private String modifiedby;
	/**
	 * 
	 */
	@Column("user_name")
	private String userName;
	/**
	 * 
	 */
	@Column("user_id")
	private Integer userId;
	/**
	 * 
	 */
	@Column("office_id")
	private Integer officeId;
	/**
	 * 
	 */
	@Column("del_flag")
	private String delFlag;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the imel
	 */
	public String getImel() {
		return imel;
	}
	/**
	 * @param imel the imel to set
	 */
	public void setImel(String imel) {
		this.imel = imel;
	}
	/**
	 * @return the createdon
	 */
	public java.util.Date getCreatedon() {
		return createdon;
	}
	/**
	 * @param createdon the createdon to set
	 */
	public void setCreatedon(java.util.Date createdon) {
		this.createdon = createdon;
	}
	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the modifiedon
	 */
	public java.util.Date getModifiedon() {
		return modifiedon;
	}
	/**
	 * @param modifiedon the modifiedon to set
	 */
	public void setModifiedon(java.util.Date modifiedon) {
		this.modifiedon = modifiedon;
	}
	/**
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}
	/**
	 * @param modifiedby the modifiedby to set
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
}