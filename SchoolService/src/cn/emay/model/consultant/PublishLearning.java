package cn.emay.model.consultant;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 顾问发布学习
 * @author zjlWm
 * @date 2015-07-10
 */
@Table("s_publish_learning")
public class PublishLearning {

	@Name
	@Column(value="id")
	private String id;
	
	
	@Column(value="title")
	private String title;
	
	@Column(value="content")
	private String content;
	
	/**
	 * 图片url串
	 */
	@Column(value="images")
	private String images;
	
	/**
	 * 1、区域、2年级
	 */
	@Column(value="type")
	private String type;
	
	
	/**
	 * 区域id
	 */
	@Column(value="area_id")
	private String areaId;
	
	/***
	 * 学校id
	 */
	@Column(value="school_id")
	private String schoolId;
	
	/**
	 * 年级id
	 */
	@Column(value="grade_code")
	private String gradeCode;
	
	@Column(value="create_by")
	private String createBy;
	
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="update_date")
	private Date updateDate;
	
	@Column(value="update_by")
	private String updateBy;
	
	@Column(value="del_flag")
	private String delFlag="0";

	/**
	 * 详情查看页面
	 */
	private String url;
	
	
	private String memberId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}


	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
