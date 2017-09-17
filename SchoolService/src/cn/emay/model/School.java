package cn.emay.model;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


/**
 * 学校
 * @author zjlWm
 * 
 */
@Table("s_school")
public class School {

	@Name
	@Column("id")
	private String id;
	
	/**
	 * 学校名称
	 */
	@Column("school_name")
	private String schoolName;
	
	
	/**
	 * 学校区域id
	 */
	@Column("area_id")
	private String areaId;
	
	/**
	 * 地址
	 */
	@Column("address")
	private String address;
	
	/**
	 * 状态
	 */
	@Column("status")
	private String status;
	
	/**
	 * 办学性质(1：公立；2：民办）
	 */
	@Column("school_property")
	private String schoolProperty;
	
	/**
	 * 是否冻结（0：未冻结；1：已冻结）
	 */
	@Column("freeze_flag")
	private String freezeFlag;
	
	/**
	 * 删除标记
	 */
	@Column("del_flag")
	private String delFlag;

	
	private List<Grade> list;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSchoolProperty() {
		return schoolProperty;
	}

	public void setSchoolProperty(String schoolProperty) {
		this.schoolProperty = schoolProperty;
	}

	public String getFreezeFlag() {
		return freezeFlag;
	}

	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public List<Grade> getList() {
		return list;
	}

	public void setList(List<Grade> list) {
		this.list = list;
	}
	
	
}
