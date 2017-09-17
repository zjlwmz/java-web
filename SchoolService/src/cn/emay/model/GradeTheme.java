package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;



/**
 * 年纪主题
 * @author zjlWm
 * @date 2015-07-07
 */
@Table("c_gradeactivity")
public class GradeTheme {
	
	@Name
	@Column(value="id")
	private String id;
	
	@Column(value="title")
	private String title;
	
	@Column(value="content")
	private String content;
	
	private Date publishEnddate;
	
	@Column(value="grade_code")
	private String gradeCode;
	
	@Column(value="picture_url")
	private String pictureUrl;
	
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="update_date")
	private Date updateDate;
	
	@Column(value="del_flag")
	private String delFlag;
	
	

	/**
	 * 描述
	 */
	@Column(value="discription")
	private String discription;
	
	/**
	 * 跳转地址
	 */
	@Column(value="url")
	private String url;

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

	public Date getPublishEnddate() {
		return publishEnddate;
	}

	public void setPublishEnddate(Date publishEnddate) {
		this.publishEnddate = publishEnddate;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}
	
	
	
}
