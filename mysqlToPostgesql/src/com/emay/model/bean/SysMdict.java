package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("sys_mdict")
public class SysMdict {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 父级编号
	 */
	@Column("parent_id")
	private Long parentId;
	/**
	 * 所有父级编号
	 */
	@Column("parent_ids")
	private String parentIds;
	/**
	 * 角色名称
	 */
	@Column("name")
	private String name;
	/**
	 * 描述
	 */
	@Column("description")
	private String description;
	/**
	 * 排序（升序）
	 */
	@Column("sort")
	private Integer sort;
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
}