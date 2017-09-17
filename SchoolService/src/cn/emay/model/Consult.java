/**
 * 
 */
package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;



/**
 * 咨询记录表
 * @author zjlWm
 * @date 2015-10-08
 */
@Table("s_consult")
public class Consult {

	@Name
	@Column(value="id")
	private String id;
	
	
	/**
	 * 家长id
	 */
	@Column(value="member_id")
	private String memberId;
	
	@Column(value="discription")
	private String discription;
	
	@Column(value="create_by")
	private String createBy;
	
	@Column(value="create_date")
	private Date createDate; 
	
	
	@Column(value="update_by")
	private String updateBy;
	
	
	@Column(value="update_date")
	private Date updateDate;
	
	@Column(value="del_flag")
	private String delFlag;
	
	@Column(value="remarks")
	private String remarks;
	
	@Column(value="images")
	private String images;

	
	public Consult(){
		super();
		this.delFlag="0";
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	
	
	
}
