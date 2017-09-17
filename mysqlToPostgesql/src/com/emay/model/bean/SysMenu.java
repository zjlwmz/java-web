package com.emay.model.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sys_menu")
public class SysMenu {

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
	 * 菜单名称
	 */
	@Column("name")
	private String name;
	/**
	 * 链接
	 */
	@Column("href")
	private String href;
	/**
	 * 目标（mainFrame、 _blank、_self、_parent、_top）
	 */
	@Column("target")
	private String target;
	/**
	 * 图标
	 */
	@Column("icon")
	private String icon;
	/**
	 * 排序（升序）
	 */
	@Column("sort")
	private Integer sort;
	/**
	 * 是否在菜单中显示（1：显示；0：不显示）
	 */
	@Column("is_show")
	private String isShow;
	/**
	 * 权限标识
	 */
	@Column("permission")
	private String permission;
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
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
	
	
}