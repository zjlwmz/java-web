package cn.emay.model.mqtt;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


/**
 * mqtt消息
 * @author zjlWm
 * @version 2015-05-13
 */
@Table("s_mqtt_message")
public class MqttMessageEntity {
	
	@Name
	@Column("id")
	private String id;
	
	@Column("qos")
	private Integer qos;
	
	@Column("retained")
	private String retained;//0:true;1:false;
	
	@Column("payload")
	private String payload;
	
	@Column("status")
	private String status;
	
	
	@Column("create_date")
	private Date createDate;
	
	@Column("create_by")
	private String createBy;
	
	@Column("del_flag")
	private String delFlag="0";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getQos() {
		return qos;
	}

	public void setQos(Integer qos) {
		this.qos = qos;
	}

	public String getRetained() {
		return retained;
	}

	public void setRetained(String retained) {
		this.retained = retained;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
