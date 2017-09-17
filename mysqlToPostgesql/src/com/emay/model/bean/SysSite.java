package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("sys_site")
public class SysSite {

	/**
	 * 
	 */
	@Column("id")
	private Integer id;
	/**
	 * 
	 */
	@Column("name")
	private String name;
	/**
	 * 
	 */
	@Column("title")
	private String title;
	/**
	 * 
	 */
	@Column("logo")
	private String logo;
	/**
	 * 
	 */
	@Column("domain")
	private String domain;
	/**
	 * 
	 */
	@Column("company_id")
	private Long companyId;
	/**
	 * 
	 */
	@Column("description")
	private String description;
	/**
	 * 
	 */
	@Column("keywords")
	private String keywords;
	/**
	 * 
	 */
	@Column("theme")
	private String theme;
	/**
	 * 
	 */
	@Column("status")
	private String status;
	/**
	 * 
	 */
	@Column("copyright")
	private String copyright;
	/**
	 * 
	 */
	@Column("custom_index_view")
	private String customIndexView;
	/**
	 * 
	 */
	@Column("create_by")
	private String createBy;
	/**
	 * 
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 
	 */
	@Column("update_by")
	private String updateBy;
	/**
	 * 
	 */
	@Column("update_date")
	private java.util.Date updateDate;
	/**
	 * 
	 */
	@Column("remarks")
	private String remarks;
	/**
	 * 
	 */
	@Column("del_flag")
	private String delFlag;
}