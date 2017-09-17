package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ru_job")
public class ActRuJob {

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
	@Column("LOCK_EXP_TIME_")
	private java.util.Date lockExpTime;
	/**
	 * 
	 */
	@Column("LOCK_OWNER_")
	private String lockOwner;
	/**
	 * 
	 */
	@Column("EXCLUSIVE_")
	private Boolean exclusive;
	/**
	 * 
	 */
	@Column("EXECUTION_ID_")
	private String executionId;
	/**
	 * 
	 */
	@Column("PROCESS_INSTANCE_ID_")
	private String processInstanceId;
	/**
	 * 
	 */
	@Column("PROC_DEF_ID_")
	private String procDefId;
	/**
	 * 
	 */
	@Column("RETRIES_")
	private Integer retries;
	/**
	 * 
	 */
	@Column("EXCEPTION_STACK_ID_")
	private String exceptionStackId;
	/**
	 * 
	 */
	@Column("EXCEPTION_MSG_")
	private String exceptionMsg;
	/**
	 * 
	 */
	@Column("DUEDATE_")
	private java.util.Date duedate;
	/**
	 * 
	 */
	@Column("REPEAT_")
	private String repeat;
	/**
	 * 
	 */
	@Column("HANDLER_TYPE_")
	private String handlerType;
	/**
	 * 
	 */
	@Column("HANDLER_CFG_")
	private String handlerCfg;
}