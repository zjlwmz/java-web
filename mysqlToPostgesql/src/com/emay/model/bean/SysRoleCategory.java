package com.emay.model.bean;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Data
@Table("sys_role_category")
@PK({
"roleId" ,
"categoryId" 
})
public class SysRoleCategory {

	/**
	 * 角色编号
	 */
	@Column("role_id")
	private Long roleId;
	/**
	 * 内容分类编号
	 */
	@Column("category_id")
	private Long categoryId;
}