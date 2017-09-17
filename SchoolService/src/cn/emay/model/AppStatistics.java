package cn.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * app统计
 * @author zjlWm
 * @version 2015-05-18
 * 
 */
@Table("s_app_statistics")
public class AppStatistics {

	@Name
	@Column("id")
	private String id;
	
	/**
	 * 统计类型
	 */
	@Column("type")
	private String type;
	
	/**
	 * app类型
	 */
	@Column("app_type")
	private String appType;
	
	@Column("device_id")
	private String deviceId;
	
	/**
	 * 创建时间
	 */
	@Column("create_date")
	private Date create_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
