package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cms_guestbook")
public class CmsGuestbook {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 留言分类（1咨询、2建议、3投诉、4其它）
	 */
	@Column("type")
	private String type;
	/**
	 * 留言内容
	 */
	@Column("content")
	private String content;
	/**
	 * 姓名
	 */
	@Column("name")
	private String name;
	/**
	 * 邮箱
	 */
	@Column("email")
	private String email;
	/**
	 * 电话
	 */
	@Column("phone")
	private String phone;
	/**
	 * 单位
	 */
	@Column("workunit")
	private String workunit;
	/**
	 * IP
	 */
	@Column("ip")
	private String ip;
	/**
	 * 留言时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 回复人
	 */
	@Column("re_user_id")
	private Long reUserId;
	/**
	 * 回复时间
	 */
	@Column("re_date")
	private java.util.Date reDate;
	/**
	 * 回复内容
	 */
	@Column("re_content")
	private String reContent;
	/**
	 * 删除标记（0：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
}