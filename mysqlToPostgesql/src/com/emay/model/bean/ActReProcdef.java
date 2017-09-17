package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_re_procdef")
public class ActReProcdef {

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
	@Column("CATEGORY_")
	private String category;
	/**
	 * 
	 */
	@Column("NAME_")
	private String name;
	/**
	 * 
	 */
	@Column("KEY_")
	private String key;
	/**
	 * 
	 */
	@Column("VERSION_")
	private Integer version;
	/**
	 * 
	 */
	@Column("DEPLOYMENT_ID_")
	private String deploymentId;
	/**
	 * 
	 */
	@Column("RESOURCE_NAME_")
	private String resourceName;
	/**
	 * 
	 */
	@Column("DGRM_RESOURCE_NAME_")
	private String dgrmResourceName;
	/**
	 * 
	 */
	@Column("DESCRIPTION_")
	private String description;
	/**
	 * 
	 */
	@Column("HAS_START_FORM_KEY_")
	private Integer hasStartFormKey;
	/**
	 * 
	 */
	@Column("SUSPENSION_STATE_")
	private Integer suspensionState;
}