package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_re_model")
public class ActReModel {

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
	@Column("CATEGORY_")
	private String category;
	/**
	 * 
	 */
	@Column("CREATE_TIME_")
	private java.util.Date createTime;
	/**
	 * 
	 */
	@Column("LAST_UPDATE_TIME_")
	private java.util.Date lastUpdateTime;
	/**
	 * 
	 */
	@Column("VERSION_")
	private Integer version;
	/**
	 * 
	 */
	@Column("META_INFO_")
	private String metaInfo;
	/**
	 * 
	 */
	@Column("DEPLOYMENT_ID_")
	private String deploymentId;
	/**
	 * 
	 */
	@Column("EDITOR_SOURCE_VALUE_ID_")
	private String editorSourceValueId;
	/**
	 * 
	 */
	@Column("EDITOR_SOURCE_EXTRA_VALUE_ID_")
	private String editorSourceExtraValueId;
}