package com.emay.model.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_role_menu")
public class SysRoleMenu {

	/**
	 * 角色编号
	 */
	@Column("role_id")
	private String roleId;
	/**
	 * 菜单编号
	 */
	@Column("menu_id")
	private String menuId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	
}