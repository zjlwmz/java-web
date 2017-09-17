package com.emay.check;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
* 客户信息表
*/
@Table("checkingin.v_customer")
public class Customer {

	/**
	 * 
	 */
	@Id
	@Column("id")
	private Integer id;
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
	 * @return the officeId
	 */
	public Integer getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId the officeId to set
	 */
	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}
	/**
	 * @return the ywyid
	 */
	public Integer getYwyid() {
		return ywyid;
	}
	/**
	 * @param ywyid the ywyid to set
	 */
	public void setYwyid(Integer ywyid) {
		this.ywyid = ywyid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the lon
	 */
	public Double getLon() {
		return lon;
	}
	/**
	 * @param lon the lon to set
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}
	/**
	 * @return the lat
	 */
	public Double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lxr
	 */
	public String getLxr() {
		return lxr;
	}
	/**
	 * @param lxr the lxr to set
	 */
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	/**
	 * @return the bm
	 */
	public String getBm() {
		return bm;
	}
	/**
	 * @param bm the bm to set
	 */
	public void setBm(String bm) {
		this.bm = bm;
	}
	/**
	 * @return the zw
	 */
	public String getZw() {
		return zw;
	}
	/**
	 * @param zw the zw to set
	 */
	public void setZw(String zw) {
		this.zw = zw;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the zsbj
	 */
	public String getZsbj() {
		return zsbj;
	}
	/**
	 * @param zsbj the zsbj to set
	 */
	public void setZsbj(String zsbj) {
		this.zsbj = zsbj;
	}
	/**
	 * @return the xqah
	 */
	public String getXqah() {
		return xqah;
	}
	/**
	 * @param xqah the xqah to set
	 */
	public void setXqah(String xqah) {
		this.xqah = xqah;
	}
	/**
	 * @return the cgqx
	 */
	public String getCgqx() {
		return cgqx;
	}
	/**
	 * @param cgqx the cgqx to set
	 */
	public void setCgqx(String cgqx) {
		this.cgqx = cgqx;
	}
	/**
	 * @return the zt
	 */
	public Integer getZt() {
		return zt;
	}
	/**
	 * @param zt the zt to set
	 */
	public void setZt(Integer zt) {
		this.zt = zt;
	}
	/**
	 * @return the bfcs
	 */
	public Integer getBfcs() {
		return bfcs;
	}
	/**
	 * @param bfcs the bfcs to set
	 */
	public void setBfcs(Integer bfcs) {
		this.bfcs = bfcs;
	}
	/**
	 * @return the bz
	 */
	public String getBz() {
		return bz;
	}
	/**
	 * @param bz the bz to set
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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
	/**
	 * @return the modifiedby
	 */
	public String getModifiedby() {
		return modifiedby;
	}
	/**
	 * @param modifiedby the modifiedby to set
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	/**
	 * 部门id
	 */
	@Column("office_id")
	private Integer officeId;
	/**
	 * 所属业务员id
	 */
	@Column("ywyid")
	private Integer ywyid;
	/**
	 * 名称
	 */
	@Column("name")
	private String name;
	/**
	 * 图片地址
	 */
	@Column("image_url")
	private String imageUrl;
	/**
	 * 地址
	 */
	@Column("address")
	private String address;
	/**
	 * 经度
	 */
	@Column("lon")
	private Double lon;
	/**
	 * 纬度
	 */
	@Column("lat")
	private Double lat;
	/**
	 * 联系人
	 */
	@Column("lxr")
	private String lxr;
	/**
	 * 部门
	 */
	@Column("bm")
	private String bm;
	/**
	 * 职位
	 */
	@Column("zw")
	private String zw;
	/**
	 * 座机
	 */
	@Column("telephone")
	private String telephone;
	/**
	 * 移动电话
	 */
	@Column("mobile")
	private String mobile;
	/**
	 * 知识背景
	 */
	@Column("zsbj")
	private String zsbj;
	/**
	 * 兴趣爱好
	 */
	@Column("xqah")
	private String xqah;
	/**
	 * 采购倾向
	 */
	@Column("cgqx")
	private String cgqx;
	/**
	 * 状态（0:新增、1:跟踪、2:成交）
	 */
	@Column("zt")
	private Integer zt;
	/**
	 * 拜访次数
	 */
	@Column("bfcs")
	private Integer bfcs;
	/**
	 * 备注
	 */
	@Column("bz")
	private String bz;
	/**
	 * 删除标记（0：正常；1：删除）
	 */
	@Column("del_flag")
	private String delFlag;
	/**
	 * 创建时间（展示：录入日期）
	 */
	@Column("createdon")
	private java.util.Date createdon;
	/**
	 * 创建人
	 */
	@Column("createdby")
	private String createdby;
	/**
	 * 修改时间（展示：修改日期）
	 */
	@Column("modifiedon")
	private java.util.Date modifiedon;
	/**
	 * 修改人
	 */
	@Column("modifiedby")
	private String modifiedby;
}