package com.emay.model.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_role_office")
public class SysRoleOffice {

	/**
	 * 角色编号
	 */
	@Column("role_id")
	private String roleId;
	/**
	 * 机构编号
	 */
	@Column("office_id")
	private String officeId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
}