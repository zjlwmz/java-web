package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ru_execution")
public class ActRuExecution {

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
	@Column("PARENT_ID_")
	private String parentId;
	/**
	 * 
	 */
	@Column("PROC_DEF_ID_")
	private String procDefId;
	/**
	 * 
	 */
	@Column("SUPER_EXEC_")
	private String superExec;
	/**
	 * 
	 */
	@Column("ACT_ID_")
	private String actId;
	/**
	 * 
	 */
	@Column("IS_ACTIVE_")
	private Integer isActive;
	/**
	 * 
	 */
	@Column("IS_CONCURRENT_")
	private Integer isConcurrent;
	/**
	 * 
	 */
	@Column("IS_SCOPE_")
	private Integer isScope;
	/**
	 * 
	 */
	@Column("IS_EVENT_SCOPE_")
	private Integer isEventScope;
	/**
	 * 
	 */
	@Column("SUSPENSION_STATE_")
	private Integer suspensionState;
	/**
	 * 
	 */
	@Column("CACHED_ENT_STATE_")
	private Integer cachedEntState;
}