package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * app广告
 * @author zjlWm
 * @date 2015-07-131
 */
@Table("app_ad")
public class AppAd {

	@Column(value="id")
	@Name
	private String id;
	
	/**
	 * 广告名称
	 */
	@Column(value="ad_name")
	private String adName;
	
	/**
	 * 类型(1:登陆广告,2:首页广告)
	 */
	@Column(value="ad_type")
	private String adType;
	
	/**
	 * 是否报名（1是；2否）
	 */
	@Column(value="type")
	private String type;
	
	/**
	 * 排序
	 */
	@Column(value="ad_sort")
	private String adSort;
	
	/**
	 * 文字描述
	 */
	@Column(value="discription")
	private String discription;
	
	/**
	 * 图片地址
	 */
	@Column(value="picture_url")
	private String pictureUrl;
	
	/**
	 * 广告开始时间
	 */
	@Column(value="begin_date")
	private Date beginDate;
	
	/**
	 * 广告结束时间
	 */
	@Column(value="end_date")
	private Date endDate;
	
	/**
	 * app类型（1：家长app；2：班主任app；3：顾问app）
	 */
	@Column(value="type_app")
	private String typeApp;
	
	
	@Column(value="create_date")
	private Date createDate;
	
	
	
	@Column(value="url")
	private String url;
	
	@Column(value="del_flag")
	private String delFlag;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAdName() {
		return adName;
	}


	public void setAdName(String adName) {
		this.adName = adName;
	}


	public String getAdType() {
		return adType;
	}


	public void setAdType(String adType) {
		this.adType = adType;
	}


	public String getAdSort() {
		return adSort;
	}


	public void setAdSort(String adSort) {
		this.adSort = adSort;
	}


	public String getDiscription() {
		return discription;
	}


	public void setDiscription(String discription) {
		this.discription = discription;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


	public Date getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getTypeApp() {
		return typeApp;
	}


	public void setTypeApp(String typeApp) {
		this.typeApp = typeApp;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
}
