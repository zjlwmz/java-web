package cn.emay.mqtt;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.nutz.json.Json;

import cn.emay.model.PushMessage;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton button;

	private MqttClient client;
	private String host = "tcp://172.16.1.88:61613";
//	private String host = "tcp://124.127.89.41:61613";
	
//	private String host = "tcp://localhost:1883";
	private String userName = "admin";
	private String passWord = "password";
	private MqttTopic topic;
	private MqttMessage message;

//	private String myTopic = "tianrui/message_notice_topic";
	private String myTopic = "school/notice_topic";

	public Server() {

		try {
			client = new MqttClient(host, "Server-school",
					new MemoryPersistence());
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Container container = this.getContentPane();
		panel = new JPanel();
		button = new JButton("发布话题");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				try {
					PushMessage pushMessage=new PushMessage();
					pushMessage.setContent("推送消息测试内容");
					pushMessage.setTitle("消息推送标题");
					pushMessage.setGrade("一年级");
					pushMessage.setPictureUrl("http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg");
					pushMessage.setHref("http://www.baidu.com");
					message.setPayload((Json.toJson(pushMessage)).getBytes());
					MqttDeliveryToken token = topic.publish(message);
					System.out.println(token.isComplete()+"========");
					token.waitForCompletion();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(button);
		container.add(panel, "North");

	}

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
					System.out.println("connectionLost----server-------");
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete------server---"+token.isComplete());
				}

				public void messageArrived(String topic, MqttMessage arg1)
						throws Exception {
					System.out.println("messageArrived------接收----server");

				}
			});

			/**
			 * 创建主题
			 */
			topic = client.getTopic(myTopic);

			message = new MqttMessage();
			message.setQos(1);
			message.setRetained(false);
			System.out.println(message.isRetained()+"------ratained状态");
			message.setPayload("eeeeeaaaaaawwwwww---".getBytes());

			client.connect(options);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
		s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.setSize(600, 370);
		s.setLocationRelativeTo(null);
		s.setVisible(true);
	}
}