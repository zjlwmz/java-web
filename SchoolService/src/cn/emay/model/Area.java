package cn.emay.model;

import org.nutz.dao.entity.annotation.*;


/**
 * 区域实体
 * @author  zjlWm
 * @version 2015-03-24
 */
@Table("sys_area")
public class Area {

	/**
	 * 
	 */
	@Name
	@Column("id")
	private String areaid;
	/**
	 * 
	 */
	@Column("parent_id")
	private String parentId;
	/**
	 * 
	 */
	@Column("parent_ids")
	private String parentIds;
	/**
	 * 
	 */
	@Column("code")
	private String code;
	/**
	 * 
	 */
	@Column("name")
	private String name;
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
	/**
	 * 创建者
	 */
	@Column("create_by")
	private String createBy;
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private java.util.Date createDate;
	/**
	 * 
	 */
	@Column("lon")
	private Double lon;
	/**
	 * 
	 */
	@Column("lat")
	private Double lat;
	
	
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
}