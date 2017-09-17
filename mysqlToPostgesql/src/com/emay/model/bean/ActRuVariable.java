package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ru_variable")
public class ActRuVariable {

	/**
	 * 
	 */
	@Name
	@Column("ID_")
	private String id;
	/**
	 * 
	 */
	@Column("REV_")
	private Integer rev;
	/**
	 * 
	 */
	@Column("TYPE_")
	private String type;
	/**
	 * 
	 */
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("EXECUTION_ID_")
	private String executionId;
	/**
	 * 
	 */
	@Column("PROC_INST_ID_")
	private String procInstId;
	/**
	 * 
	 */
	@Column("TASK_ID_")
	private String taskId;
	/**
	 * 
	 */
	@Column("BYTEARRAY_ID_")
	private String bytearrayId;
	/**
	 * 
	 */
	@Column("DOUBLE_")
	private Double double_;
	/**
	 * 
	 */
	@Column("LONG_")
	private Long long_;
	/**
	 * 
	 */
	@Column("TEXT_")
	private String text;
	/**
	 * 
	 */
	@Column("TEXT2_")
	private String text2;
}