package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_hi_taskinst")
public class ActHiTaskinst {

	/**
	 * 
	 */
	@Name
	@Column("ID_")
	private String id;
	/**
	 * 
	 */
	@Column("PROC_DEF_ID_")
	private String procDefId;
	/**
	 * 
	 */
	@Column("TASK_DEF_KEY_")
	private String taskDefKey;
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
	@Column("START_TIME_")
	private java.util.Date startTime;
	/**
	 * 
	 */
	@Column("CLAIM_TIME_")
	private java.util.Date claimTime;
	/**
	 * 
	 */
	@Column("END_TIME_")
	private java.util.Date endTime;
	/**
	 * 
	 */
	@Column("DURATION_")
	private Long duration;
	/**
	 * 
	 */
	@Column("DELETE_REASON_")
	private String deleteReason;
	/**
	 * 
	 */
	@Column("PRIORITY_")
	private Integer priority;
	/**
	 * 
	 */
	@Column("DUE_DATE_")
	private java.util.Date dueDate;
	/**
	 * 
	 */
	@Column("FORM_KEY_")
	private String formKey;
}