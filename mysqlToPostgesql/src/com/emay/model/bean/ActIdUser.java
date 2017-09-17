package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_id_user")
public class ActIdUser {

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
	@Column("FIRST_")
	private String first;
	/**
	 * 
	 */
	@Column("LAST_")
	private String last;
	/**
	 * 
	 */
	@Column("EMAIL_")
	private String email;
	/**
	 * 
	 */
	@Column("PWD_")
	private String pwd;
	/**
	 * 
	 */
	@Column("PICTURE_ID_")
	private String pictureId;
}