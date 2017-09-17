package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ru_event_subscr")
public class ActRuEventSubscr {

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
	@Column("EVENT_TYPE_")
	private String eventType;
	/**
	 * 
	 */
	@Column("EVENT_NAME_")
	private String eventName;
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
	@Column("ACTIVITY_ID_")
	private String activityId;
	/**
	 * 
	 */
	@Column("CONFIGURATION_")
	private String configuration;
	/**
	 * 
	 */
	@Column("CREATED_")
	private java.util.Date created;
}