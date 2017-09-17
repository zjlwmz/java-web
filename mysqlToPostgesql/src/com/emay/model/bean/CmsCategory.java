package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cms_category")
public class CmsCategory {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 站点编号
	 */
	@Column("site_id")
	private Long siteId;
	/**
	 * 归属机构
	 */
	@Column("office_id")
	private Long officeId;
	/**
	 * 父级编号
	 */
	@Column("parent_id")
	private Long parentId;
	/**
	 * 所有父级编号
	 */
	@Column("parent_ids")
	private String parentIds;
	/**
	 * 栏目模块（article：文章；picture：图片；download：下载；link：链接；special：专题）
	 */
	@Column("module")
	private String module;
	/**
	 * 栏目名称
	 */
	@Column("name")
	private String name;
	/**
	 * 栏目图片
	 */
	@Column("image")
	private String image;
	/**
	 * 链接
	 */
	@Column("href")
	private String href;
	/**
	 * 目标（ _blank、_self、_parent、_top）
	 */
	@Column("target")
	private String target;
	/**
	 * 描述，填写有助于搜索引擎优化
	 */
	@Column("description")
	private String description;
	/**
	 * 关键字，填写有助于搜索引擎优化
	 */
	@Column("keywords")
	private String keywords;
	/**
	 * 排序（升序）
	 */
	@Column("sort")
	private Integer sort;
	/**
	 * 是否在导航中显示（1：显示；0：不显示）
	 */
	@Column("in_menu")
	private String inMenu;
	/**
	 * 是否在分类页中显示列表（1：显示；0：不显示）
	 */
	@Column("in_list")
	private String inList;
	/**
	 * 展现方式（0:有子栏目显示栏目列表，无子栏目显示内容列表;1：首栏目内容列表；2：栏目第一条内容）
	 */
	@Column("show_modes")
	private String showModes;
	/**
	 * 是否允许评论
	 */
	@Column("allow_comment")
	private String allowComment;
	/**
	 * 是否需要审核
	 */
	@Column("is_audit")
	private String isAudit;
	/**
	 * 创建者
	 */
	@Column("create_by")
	private Long createBy;
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 更新者
	 */
	@Column("update_by")
	private Long updateBy;
	/**
	 * 更新时间
	 */
	@Column("update_date")
	private java.util.Date updateDate;
	/**
	 * 备注信息
	 */
	@Column("remarks")
	private String remarks;
	/**
	 * 删除标记（0：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
}