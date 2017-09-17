package cn.emay.model.consultant;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


/**
 * 顾问记录对学生的备注
 * @author zjlWm
 * @date 2015-07-11
 */
@Table("s_student_remarks")
public class StudentRemarks {
	@Name
	@Column(value="id")
	private String id;
	
	
	/**
	 * 家长
	 */
	@Column(value="member_id")
	private String memberId;
	
	
	/**
	 * 备注内容
	 */
	@Column(value="remarks")
	private String remarks;
	
	
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="create_by")
	private String createBy;
	
	
	@Column(value="del_flag")
	private String delFlag="0";


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


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
	
	
	
}
