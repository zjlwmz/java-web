package cn.emay.web;

import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import cn.emay.common.util.BaseController;
import cn.emay.common.util.IdGen;
import cn.emay.model.mqtt.MqttMessageEntity;
import cn.emay.model.mqtt.MqttTopicEntity;
import cn.emay.service.MqttTopicEntityService;
import cn.emay.service.mqtt.MqttServer;


/**
 * MQTT对外接口
 * @author zjlWm
 * @version 2015-05-12
 */

@IocBean
@At(value="/service/mqtt/")
public class MQTTController extends BaseController{
	static Logger logger = Logger.getLogger(MQTTController.class.getName());
	
	@Inject
	private MqttServer mqttServer;
	
	@Inject
	private MqttTopicEntityService mqttTopicEntityService;
	
	/**
	 * 添加主题
	 */
	@At("addTopic")
	public void addTopic(){
		System.out.println(mqttServer);
		MqttMessage message = new MqttMessage();
		message.setRetained(true);
		System.out.println(message.isRetained()+"------ratained状态");
		message.setQos(1);
		message.setPayload("eeeeeaaaaaawwwwww---".getBytes());
		for(int i=0;i<10;i++){
			MqttClient client=mqttServer.getClient();
			
			MqttTopic topic=client.getTopic("zjlwm/topic_"+i);
			try {
				topic.publish(message);
				MqttTopicEntity mqttTopicEntity=new MqttTopicEntity();
				mqttTopicEntity.setTopicName("zjlwm/topic_"+i);
				mqttTopicEntity.setCreateDate(new Date());
				mqttTopicEntityService.save(mqttTopicEntity);
			} catch (MqttPersistenceException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}catch (Exception e){
				e.printStackTrace();
			}
			System.out.println("topic_"+i);
		}
	}
	
	
	/**
	 * 发送消息
	 */
	@At("sendMessage")
	public void sendMessage(String send){
		MqttClient client=mqttServer.getClient();
		MqttTopic topic=client.getTopic("emaycms/member/snchronouse");
		
		MqttMessage message = new MqttMessage();
		message.setRetained(true);
		System.out.println(message.isRetained()+"------ratained状态");
		message.setQos(1);
		try {
			message.setPayload(("zjl---"+send).getBytes("GBK"));
			MqttDeliveryToken token=topic.publish(message);
			token.waitForCompletion();
			
			MqttMessageEntity mqttMessage=new MqttMessageEntity();
			mqttMessage.setId(IdGen.uuid());
			mqttMessage.setPayload("zjl---"+send);
			mqttMessage.setQos(1);
			mqttMessage.setRetained("0");
			mqttMessage.setCreateDate(new Date());
			mqttTopicEntityService.saveMessage(mqttMessage);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
