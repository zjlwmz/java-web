package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cms_article_data")
public class CmsArticleData {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 文章内容
	 */
	@Column("content")
	private String content;
	/**
	 * 文章来源
	 */
	@Column("copyfrom")
	private String copyfrom;
	/**
	 * 相关文章
	 */
	@Column("relation")
	private String relation;
	/**
	 * 是否允许评论
	 */
	@Column("allow_comment")
	private String allowComment;
}