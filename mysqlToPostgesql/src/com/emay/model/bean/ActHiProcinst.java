package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_hi_procinst")
public class ActHiProcinst {

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
	@Column("BUSINESS_KEY_")
	private String businessKey;
	/**
	 * 
	 */
	@Column("PROC_DEF_ID_")
	private String procDefId;
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
	/**
	 * 
	 */
	@Column("START_USER_ID_")
	private String startUserId;
	/**
	 * 
	 */
	@Column("START_ACT_ID_")
	private String startActId;
	/**
	 * 
	 */
	@Column("END_ACT_ID_")
	private String endActId;
	/**
	 * 
	 */
	@Column("SUPER_PROCESS_INSTANCE_ID_")
	private String superProcessInstanceId;
	/**
	 * 
	 */
	@Column("DELETE_REASON_")
	private String deleteReason;
}