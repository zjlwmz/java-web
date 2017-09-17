package com.emay.model.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_office")
public class SysOffice {

	/**
	 * 编号
	 */
	@Name
	@Column("id")
	private String id;
	/**
	 * 父级编号
	 */
	@Column("parent_id")
	private String parentId;
	/**
	 * 所有父级编号
	 */
	@Column("parent_ids")
	private String parentIds;
	/**
	 * 归属区域
	 */
	@Column("area_id")
	private String areaId;
	/**
	 * 区域编码
	 */
	@Column("code")
	private String code;
	/**
	 * 机构名称
	 */
	@Column("name")
	private String name;
	/**
	 * 机构类型（1：公司；2：部门；3：小组）
	 */
	@Column("type")
	private String type;
	/**
	 * 机构等级（1：一级；2：二级；3：三级；4：四级）
	 */
	@Column("grade")
	private String grade;
	/**
	 * 联系地址
	 */
	@Column("address")
	private String address;
	/**
	 * 邮政编码
	 */
	@Column("zip_code")
	private String zipCode;
	/**
	 * 负责人
	 */
	@Column("master")
	private String master;
	/**
	 * 电话
	 */
	@Column("phone")
	private String phone;
	/**
	 * 传真
	 */
	@Column("fax")
	private String fax;
	/**
	 * 邮箱
	 */
	@Column("email")
	private String email;
	/**
	 * 创建者
	 */
	@Column("create_by")
	private Long createBy;
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 更新者
	 */
	@Column("update_by")
	private Long updateBy;
	/**
	 * 更新时间
	 */
	@Column("update_date")
	private java.util.Date updateDate;
	/**
	 * 备注信息
	 */
	@Column("remarks")
	private String remarks;
	/**
	 * 删除标记（0：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the parentIds
	 */
	public String getParentIds() {
		return parentIds;
	}
	/**
	 * @param parentIds the parentIds to set
	 */
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the master
	 */
	public String getMaster() {
		return master;
	}
	/**
	 * @param master the master to set
	 */
	public void setMaster(String master) {
		this.master = master;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the createBy
	 */
	public Long getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the createDate
	 */
	public java.util.Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the updateBy
	 */
	public Long getUpdateBy() {
		return updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * @return the updateDate
	 */
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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