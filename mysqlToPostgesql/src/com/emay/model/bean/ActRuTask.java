package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ru_task")
public class ActRuTask {

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
	@Column("PROC_DEF_ID_")
	private String procDefId;
	/**
	 * 
	 */
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("PARENT_TASK_ID_")
	private String parentTaskId;
	/**
	 * 
	 */
	@Column("DESCRIPTION_")
	private String description;
	/**
	 * 
	 */
	@Column("TASK_DEF_KEY_")
	private String taskDefKey;
	/**
	 * 
	 */
	@Column("OWNER_")
	private String owner;
	/**
	 * 
	 */
	@Column("ASSIGNEE_")
	private String assignee;
	/**
	 * 
	 */
	@Column("DELEGATION_")
	private String delegation;
	/**
	 * 
	 */
	@Column("PRIORITY_")
	private Integer priority;
	/**
	 * 
	 */
	@Column("CREATE_TIME_")
	private java.util.Date createTime;
	/**
	 * 
	 */
	@Column("DUE_DATE_")
	private java.util.Date dueDate;
	/**
	 * 
	 */
	@Column("SUSPENSION_STATE_")
	private Integer suspensionState;
}