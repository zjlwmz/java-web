package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cms_article")
public class CmsArticle {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 栏目编号
	 */
	@Column("category_id")
	private Long categoryId;
	/**
	 * 标题
	 */
	@Column("title")
	private String title;
	/**
	 * 标题颜色（red：红色；green：绿色；blue：蓝色；yellow：黄色；orange：橙色）
	 */
	@Column("color")
	private String color;
	/**
	 * 文章图片
	 */
	@Column("image")
	private String image;
	/**
	 * 关键字
	 */
	@Column("keywords")
	private String keywords;
	/**
	 * 描述、摘要
	 */
	@Column("description")
	private String description;
	/**
	 * 权重，越大越靠前
	 */
	@Column("weight")
	private Integer weight;
	/**
	 * 点击数
	 */
	@Column("hits")
	private Integer hits;
	/**
	 * 推荐位，多选（1：首页焦点图；2：栏目页文章推荐；）
	 */
	@Column("posid")
	private String posid;
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