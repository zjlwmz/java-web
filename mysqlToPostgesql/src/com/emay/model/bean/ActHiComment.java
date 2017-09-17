package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_hi_comment")
public class ActHiComment {

	/**
	 * 
	 */
	@Name
	@Column("ID_")
	private String id;
	/**
	 * 
	 */
	@Column("TYPE_")
	private String type;
	/**
	 * 
	 */
	@Column("TIME_")
	private java.util.Date time;
	/**
	 * 
	 */
	@Column("USER_ID_")
	private String userId;
	/**
	 * 
	 */
	@Column("TASK_ID_")
	private String taskId;
	/**
	 * 
	 */
	@Column("PROC_INST_ID_")
	private String procInstId;
	/**
	 * 
	 */
	@Column("ACTION_")
	private String action;
	/**
	 * 
	 */
	@Column("MESSAGE_")
	private String message;
	/**
	 * 
	 */
	@Column("FULL_MSG_")
	private byte[] fullMsg;
}