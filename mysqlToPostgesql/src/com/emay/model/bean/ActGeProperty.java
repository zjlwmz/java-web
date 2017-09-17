package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ge_property")
public class ActGeProperty {

	/**
	 * 
	 */
	@Name
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("VALUE_")
	private String value;
	/**
	 * 
	 */
	@Column("REV_")
	private Integer rev;
}