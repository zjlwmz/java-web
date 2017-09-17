package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_re_deployment")
public class ActReDeployment {

	/**
	 * 
	 */
	@Name
	@Column("ID_")
	private String id;
	/**
	 * 
	 */
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("CATEGORY_")
	private String category;
	/**
	 * 
	 */
	@Column("DEPLOY_TIME_")
	private java.util.Date deployTime;
}