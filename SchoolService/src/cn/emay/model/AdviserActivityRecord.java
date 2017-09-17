/**
 * 
 */
package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


/**
 * 顾问活动记录表
 * @author zjlWm
 * @date
 *
 */
@Table(value="s_adviser_activity_record")
public class AdviserActivityRecord {

	@Name
	@Column(value="id")
	private String id;

	@Column(value="adviser_activity_id")
	private String adviserActivityId;
	
	private String adviserActivityName;
	
	private String type;
	
	
	/**
	 * 活动内容记录
	 */
	@Column(value="content")
	private String content;
	
	
	/**
	 * 参加活动状态（1未参加；2已参加；3已退款）
	 */
	@Column(value="status")
	private String status="1";
	
	@Column(value="member_id")
	private String memberId;
	
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="update_date")
	private Date updateDate;
	
	@Column(value="del_flag")
	private String delFlag;
	
	
	
	public AdviserActivityRecord(){
		super();
		this.delFlag="0";
	}
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	

	public String getAdviserActivityId() {
		return adviserActivityId;
	}

	public void setAdviserActivityId(String adviserActivityId) {
		this.adviserActivityId = adviserActivityId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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




	public String getAdviserActivityName() {
		return adviserActivityName;
	}




	public void setAdviserActivityName(String adviserActivityName) {
		this.adviserActivityName = adviserActivityName;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
