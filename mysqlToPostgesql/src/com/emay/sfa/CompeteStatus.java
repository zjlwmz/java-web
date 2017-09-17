package com.emay.sfa;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Table("sfa2.compete_status")
public class CompeteStatus {

	/**
	 * 
	 */
	@Id
	@Column("id")
	private Integer id;
	/**
	 * 门店id
	 */
	@Column("storeid")
	private Integer storeid;
	/**
	 * 品牌
	 */
	@Column("brand")
	private String brand;
	/**
	 * 品牌名称
	 */
	@Column("brand_name")
	private String brandName;
	/**
	 * 规格
	 */
	@Column("standard")
	private String standard;
	/**
	 * 促销价
	 */
	@Column("sales_price")
	private Double salesPrice;
	/**
	 * 
	 */
	@Column("image_id")
	private Integer imageId;
	/**
	 * 
	 */
	@Column("image")
	private String image;
	/**
	 * 
	 */
	@Column("unit")
	private String unit;
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
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	/**
	 * @return the salesPrice
	 */
	public Double getSalesPrice() {
		return salesPrice;
	}
	/**
	 * @param salesPrice the salesPrice to set
	 */
	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
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
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
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