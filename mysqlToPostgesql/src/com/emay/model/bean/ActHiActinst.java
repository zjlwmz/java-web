package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_hi_actinst")
public class ActHiActinst {

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
	@Column("ACT_ID_")
	private String actId;
	/**
	 * 
	 */
	@Column("TASK_ID_")
	private String taskId;
	/**
	 * 
	 */
	@Column("CALL_PROC_INST_ID_")
	private String callProcInstId;
	/**
	 * 
	 */
	@Column("ACT_NAME_")
	private String actName;
	/**
	 * 
	 */
	@Column("ACT_TYPE_")
	private String actType;
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
	@Column("END_TIME_")
	private java.util.Date endTime;
	/**
	 * 
	 */
	@Column("DURATION_")
	private Long duration;
}