package cn.emay.model.mqtt;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;


/**
 * 主题
 * @author zjlWm
 * @version 2015-05-13
 */
@Table("s_mqtt_topic")
public class MqttTopicEntity {
	@Name
	@Column("topic_name")
	private String topicName;
	
	
	@Column("create_by")
	private String createBy;
	
	@Column("create_date")
	private Date createDate;
	
	@Column("del_flag")
	private String delFlag="0";

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
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

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
