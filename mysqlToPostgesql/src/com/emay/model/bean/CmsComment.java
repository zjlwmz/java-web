package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cms_comment")
public class CmsComment {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 栏目模块（article：文章；picture：图片；download：下载）
	 */
	@Column("module")
	private String module;
	/**
	 * 栏目内容的编号（Article.id、Photo.id、Download.id）
	 */
	@Column("content_id")
	private Long contentId;
	/**
	 * 栏目内容的标题（Article.title、Photo.title、Download.title）
	 */
	@Column("title")
	private String title;
	/**
	 * 评论内容
	 */
	@Column("content")
	private String content;
	/**
	 * 评论姓名
	 */
	@Column("name")
	private String name;
	/**
	 * 评论IP
	 */
	@Column("ip")
	private String ip;
	/**
	 * 评论时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 审核人
	 */
	@Column("audit_user_id")
	private Long auditUserId;
	/**
	 * 审核时间
	 */
	@Column("audit_date")
	private java.util.Date auditDate;
	/**
	 * 删除标记（0：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
}