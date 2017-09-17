package cn.emay.service;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.MqttTopicEntityDao;

import cn.emay.model.mqtt.MqttMessageEntity;
import cn.emay.model.mqtt.MqttTopicEntity;


/**
 * 主题接口
 * @author zjlWm
 * @version 2015-05-13
 */
@IocBean
public class MqttTopicEntityService {
	
	@Inject
	private MqttTopicEntityDao mqttTopicEntityDao;
	
	/**
	 * 保存主题
	 */
	public void save(MqttTopicEntity mqttTopicEntity){
		if(mqttTopicEntityDao.find(mqttTopicEntity.getTopicName(), MqttTopicEntity.class)==null){;
			mqttTopicEntityDao.save(mqttTopicEntity);
		}else{
			mqttTopicEntityDao.update(mqttTopicEntity);
		}
	}
	
	/**
	 * 保存消息
	 */
	public void saveMessage(MqttMessageEntity mqttMessageEntity){
		mqttTopicEntityDao.save(mqttMessageEntity);
	}
}
