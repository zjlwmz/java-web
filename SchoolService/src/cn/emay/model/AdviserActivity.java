/**
 * 
 */
package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 获取列表
 * @author zjlWm
 * @date
 *
 */
@Table("s_adviser_activity")
public class AdviserActivity {

	@Name
	private String id;
	
	/**
	 * 活动标题
	 */
	@Column(value="title")
	private String title;
	
	/**
	 * 活动类型
	 */
	@Column(value="type")
	private String type;
	
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="update_date")
	private Date updateDate;
	
	@Column(value="del_flag")
	private String delFlag;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	
	
	
	
}
