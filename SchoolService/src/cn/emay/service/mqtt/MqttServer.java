package cn.emay.service.mqtt;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * Mqtt 工具类型
 * @author zjlWm
 * @version 2015-05-12
 */
@IocBean
public class MqttServer {
	static Logger logger = Logger.getLogger(MqttServer.class.getName());

	private String host = "tcp://172.16.1.88:61613";
	private String userName = "admin";
	private String passWord = "password";
	private MqttTopic topic;	//主题
	private String myTopic = "emaycms/member/snchronouse"; //主题名称
	
	
	private MqttMessage message;
	private MqttClient client;

	
	
	/**
	 * 初始化
	 */
	public MqttServer(){
		try {
			client = new MqttClient(host, "Server",
					new MemoryPersistence());
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 连接远程MQTT
	 */
	private void connect() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		options.setUserName(userName);
		options.setPassword(passWord.toCharArray());
		// 设置超时时间
		options.setConnectionTimeout(10);
		// 设置会话心跳时间
		options.setKeepAliveInterval(20);
		try {
			client.setCallback(new MqttCallback() {

				public void connectionLost(Throwable cause) {
					logger.debug("connectionLost-----------");
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
					logger.debug("deliveryComplete---------"+token.isComplete());
				}

				public void messageArrived(String topic, MqttMessage arg1)
						throws Exception {
					logger.debug("messageArrived------接收----");
				}
			});

			/**
			 * 创建主题
			 */
			topic = client.getTopic(myTopic);
			message = new MqttMessage();
			message.setQos(1);
			message.setRetained(true);
//			message.setPayload("eeeeeaaaaaawwwwww---".getBytes());
			System.out.println(topic);
			client.connect(options);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public MqttClient getClient() {
		return client;
	}
	
	
	
}
