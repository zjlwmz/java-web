package com.emay.sfa;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Table("sfa2.s_images")
public class Simages {

	/**
	 * 
	 */
	@Id
	@Column("id")
	private Integer id;
	/**
	 * 
	 */
	@Column("image_id")
	private Integer imageId;
	/**
	 * 
	 */
	@Column("imageurl")
	private String imageurl;
	/**
	 * 
	 */
	@Column("status")
	private Integer status;
	/**
	 * 
	 */
	@Column("createdon")
	private java.util.Date createdon;
	/**
	 * 
	 */
	@Column("modifiedon")
	private java.util.Date modifiedon;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the imageId
	 */
	public Integer getImageId() {
		return imageId;
	}
	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	/**
	 * @return the imageurl
	 */
	public String getImageurl() {
		return imageurl;
	}
	/**
	 * @param imageurl the imageurl to set
	 */
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the createdon
	 */
	public java.util.Date getCreatedon() {
		return createdon;
	}
	/**
	 * @param createdon the createdon to set
	 */
	public void setCreatedon(java.util.Date createdon) {
		this.createdon = createdon;
	}
	/**
	 * @return the modifiedon
	 */
	public java.util.Date getModifiedon() {
		return modifiedon;
	}
	/**
	 * @param modifiedon the modifiedon to set
	 */
	public void setModifiedon(java.util.Date modifiedon) {
		this.modifiedon = modifiedon;
	}
	
	
	
}