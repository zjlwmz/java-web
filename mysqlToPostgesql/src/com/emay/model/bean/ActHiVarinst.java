package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_hi_varinst")
public class ActHiVarinst {

	/**
	 * 
	 */
	@Name
	@Column("ID_")
	private String id;
	/**
	 * 
	 */
	@Column("PROC_INST_ID_")
	private String procInstId;
	/**
	 * 
	 */
	@Column("EXECUTION_ID_")
	private String executionId;
	/**
	 * 
	 */
	@Column("TASK_ID_")
	private String taskId;
	/**
	 * 
	 */
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("VAR_TYPE_")
	private String varType;
	/**
	 * 
	 */
	@Column("REV_")
	private Integer rev;
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