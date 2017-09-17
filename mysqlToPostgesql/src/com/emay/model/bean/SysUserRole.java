package com.emay.model.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_user_role")
public class SysUserRole {

	/**
	 * 用户编号
	 */
	@Column("user_id")
	private String userId;
	/**
	 * 角色编号
	 */
	@Column("role_id")
	private String roleId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
}