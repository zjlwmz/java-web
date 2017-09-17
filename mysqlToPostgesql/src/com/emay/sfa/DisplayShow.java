package com.emay.sfa;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Table("sfa2.display_show")
public class DisplayShow {

	/**
	 * 
	 */
	@Id
	@Column("id")
	private Integer id;
	/**
	 * 
	 */
	@Column("storeid")
	private Integer storeid;
	/**
	 * 类型
	 */
	@Column("type")
	private String type;
	/**
	 * 内容
	 */
	@Column("displaycontent")
	private String displaycontent;
	/**
	 * 图片地址
	 */
	@Column("image")
	private String image;
	/**
	 * 
	 */
	@Column("image_id")
	private Integer imageId;
	/**
	 * 
	 */
	@Column("createdon")
	private java.util.Date createdon;
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
	 * @return the storeid
	 */
	public Integer getStoreid() {
		return storeid;
	}
	/**
	 * @param storeid the storeid to set
	 */
	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the displaycontent
	 */
	public String getDisplaycontent() {
		return displaycontent;
	}
	/**
	 * @param displaycontent the displaycontent to set
	 */
	public void setDisplaycontent(String displaycontent) {
		this.displaycontent = displaycontent;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
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
	
	
}