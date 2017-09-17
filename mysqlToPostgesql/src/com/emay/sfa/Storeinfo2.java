package com.emay.sfa;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

/**
 * 门店信息表
 * @author Administrator
 * 
 *
 */
@Table("storeinfo")
public class Storeinfo2 {
	/**
	 * 门店id
	 */
//	@Id
	@Column("id")
	private Integer storeid;
	
	
	/**
	 * 巡店员id
	 */
	@Column("userid")
	private Integer userid;
	
	/**
	 * 门店名称
	 */
	@Column("storename")
	private String storename;
	
	/**
	 * 店长姓名
	 */
	@Column("ownername")
	private String ownername;
	
	/**
	 * 门店电话
	 */
	@Column("storetel")
	private String storetel;
	
	/**
	 * 门店地址
	 */
	@Column("storeaddr")
	private String storeaddr;
	
	/**
	 * 纬度
	 */
	@Column("storelat")
	private Double storelat;
	
	/**
	 * 经度
	 */
	@Column("storelong")
	private Double storelong;
	
	@Transient
	private String lonlat;
	
	/**
	 * 创建者
	 */
	@Column("createdby")
	public String createdby;
	/**
	 * 创建时间
	 */
	@Column("createdon")
	private Date createdon;
	
	/**
	 * 修改者
	 */
	@Column("modifiedby")
	public String modifiedby;
	/**
	 * 修改时间
	 * 
	 */
	@Column("modifiedon")
	private Date modifiedon;
	/**
	 * 相对距离
	 * 
	 */
	private Double distance;
	
	/**
	 * checkboox
	 * @return
	 */
	private String check  ="<input type=\"checkbox\" class=\"row_checkbox\"/>";
	
	
	/**
	 * 部门id
	 */
	@Column("office_id")
	private Integer officeId;
	
	@Column("del_flag")
	private String delFlag="0"; //0正常;1删除;
	
	/**
	 * 状态 是否巡店
	 * 1未巡店;0 已经巡店;
	 */
	private Integer status;
	
	/**
	 * 巡店日期
	 */
	private Date taskTime;
	
	/**
	 * 巡店日期(周几)
	 * 周一到周六--1;
	 * 周一、周三、周五--2;
	 * 周二、周四、周六--3;
	 * 周一、周四--4;
	 * 周三、周六 --5
	 * 
	 * @return
	 */	
	@Column("round_week")
	private String roundWeek;
	
	/**
	 * 门店图片
	 * @return
	 */
	@Column("image_id")
	private long imageId;
	
	
	/**
	 * 图片路径
	 * @return
	 */
	@Column("image_url")
	private String imageUrl;

	
	
	/**
	 * 商品列表
	 */
//	@ManyMany(target = Product.class, relation = "sfa2.store_product", from = "storeid_id", to = "product_id")
//	private List<Product> productList;
	
//	@Column("the_geom")
//	private PGgeometry  geom;
	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getRoundWeek() {
		return roundWeek;
	}

	public void setRoundWeek(String roundWeek) {
		this.roundWeek = roundWeek;
	}


	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}


	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getStoretel() {
		return storetel;
	}

	public void setStoretel(String storetel) {
		this.storetel = storetel;
	}

	public String getStoreaddr() {
		return storeaddr;
	}

	public void setStoreaddr(String storeaddr) {
		this.storeaddr = storeaddr;
	}

	public Double getStorelat() {
		return storelat;
	}

	public void setStorelat(Double storelat) {
		this.storelat = storelat;
	}

	public Double getStorelong() {
		return storelong;
	}

	public void setStorelong(Double storelong) {
		this.storelong = storelong;
	}

	public String getLonlat() {
		return lonlat;
	}

	public void setLonlat(String lonlat) {
		this.lonlat = lonlat;
		if(null!=lonlat){
			String points[]=lonlat.split(",");
			this.storelong=Double.parseDouble(points[0]);
			this.storelat=Double.parseDouble(points[1]);
			
		}
	}

	@Override
	public String toString() {
		return ownername+","+storeaddr+","+storeid+","+storename+","+storetel+","+storelat+","+storelong;
	}


	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedon() {
		return modifiedon;
	}

	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public Date getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public Integer getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

//	public PGgeometry getGeom() {
//		return geom;
//	}
//
//	public void setGeom(PGgeometry geom) {
//		this.geom = geom;
//	}

	

	
	
}
