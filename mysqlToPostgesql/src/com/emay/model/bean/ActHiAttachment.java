package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_hi_attachment")
public class ActHiAttachment {

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
	@Column("USER_ID_")
	private String userId;
	/**
	 * 
	 */
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("DESCRIPTION_")
	private String description;
	/**
	 * 
	 */
	@Column("TYPE_")
	private String type;
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
	@Column("URL_")
	private String url;
	/**
	 * 
	 */
	@Column("CONTENT_ID_")
	private String contentId;
}