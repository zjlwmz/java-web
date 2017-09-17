package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("act_ge_bytearray")
public class ActGeBytearray {

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
	@Column("DEPLOYMENT_ID_")
	private String deploymentId;
	/**
	 * 
	 */
	@Column("BYTES_")
	private byte[] bytes;
	/**
	 * 
	 */
	@Column("GENERATED_")
	private Integer generated;
}