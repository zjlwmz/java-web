package com.emay.model.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_user")
public class SysUser {

	/**
	 * 编号
	 */
	@Name
	@Column("id")
	private String id;
	/**
	 * 归属公司
	 */
	@Column("company_id")
	private String companyId;
//	/**
//	 * 归属部门
//	 */
//	@Column("office_id")
//	private String officeId;
	/**
	 * 登录名
	 */
	@Column("login_name")
	private String loginName;
	/**
	 * 密码
	 */
	@Column("password")
	private String password;
	/**
	 * 工号
	 */
	@Column("no")
	private String no;
	/**
	 * 姓名
	 */
	@Column("name")
	private String name;
	/**
	 * 邮箱
	 */
	@Column("email")
	private String email;
	/**
	 * 电话
	 */
	@Column("phone")
	private String phone;
	/**
	 * 手机
	 */
//	@Column("mobile")
//	private String mobile;
	/**
	 * 用户类型
	 */
	@Column("user_type")
	private String userType;
	/**
	 * 最后登陆IP
	 */
	@Column("login_ip")
	private String loginIp;
	/**
	 * 最后登陆时间
	 */
	@Column("login_date")
	private java.util.Date loginDate;
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
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
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
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}
	/**
	 * @param loginIp the loginIp to set
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	/**
	 * @return the loginDate
	 */
	public java.util.Date getLoginDate() {
		return loginDate;
	}
	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(java.util.Date loginDate) {
		this.loginDate = loginDate;
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