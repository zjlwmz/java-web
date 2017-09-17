package com.emay.model.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cms_site")
public class CmsSite {

	/**
	 * 编号
	 */
	@Id
	@Column("id")
	private Long id;
	/**
	 * 站点名称
	 */
	@Column("name")
	private String name;
	/**
	 * 站点标题
	 */
	@Column("title")
	private String title;
	/**
	 * 站点Logo
	 */
	@Column("logo")
	private String logo;
	/**
	 * 站点域名
	 */
	@Column("domain")
	private String domain;
	/**
	 * 描述，填写有助于搜索引擎优化
	 */
	@Column("description")
	private String description;
	/**
	 * 关键字，填写有助于搜索引擎优�?
	 */
	@Column("keywords")
	private String keywords;
	/**
	 * 主题
	 */
	@Column("theme")
	private String theme;
	/**
	 * 版权信息
	 */
	@Column("copyright")
	private String copyright;
	/**
	 * 自定义站点�v页�K�?
	 */
	@Column("custom_index_view")
	private String customIndexView;
	/**
	 * 创建�?
	 */
	@Column("create_by")
	private Long createBy;
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 更新�?
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
	 * 删除标记�?：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
}