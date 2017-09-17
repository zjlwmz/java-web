package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_id_info")
public class ActIdInfo {

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
	@Column("TYPE_")
	private String type;
	/**
	 * 
	 */
	@Column("KEY_")
	private String key;
	/**
	 * 
	 */
	@Column("VALUE_")
	private String value;
	/**
	 * 
	 */
	@Column("PASSWORD_")
	private byte[] password;
	/**
	 * 
	 */
	@Column("PARENT_ID_")
	private String parentId;
}