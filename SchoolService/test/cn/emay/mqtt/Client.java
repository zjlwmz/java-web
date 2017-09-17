package cn.emay.mqtt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Client {

	private String host = "tcp://172.16.1.88:61613";
	private String userName = "admin";
	private String passWord = "password";
	private MqttClient client;

	private String myTopic = "school/notice_topic";//主题名称

	private MqttConnectOptions options;
	
	private ScheduledExecutorService scheduler;
	
	
	public static void main(String[] args) {
		
		Client client=new Client();
		client.init();
		client.startReconnect();
		
	}
	
	private void init() {
		try {
                       //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(host, "131614546700000",
					new MemoryPersistence());
                       //MQTT的连接设置
			options = new MqttConnectOptions();
                       //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(false);
                       //设置连接的用户名
			options.setUserName(userName);
                       //设置连接的密码
			options.setPassword(passWord.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
                        //设置回调
			client.setCallback(new MqttCallback() {

				public void connectionLost(Throwable cause) {
                                        //连接丢失后，一般在这里面进行重连
					System.out.println("connectionLost-------client---");
					connect();
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
                                        //publish后会执行到这里
					System.out.println("deliveryComplete-------client--"
							+ token.isComplete());
				}

				 /** 
			     * 接收到信息的处理 
			     */
				public void messageArrived(String topicName, MqttMessage message)
						throws Exception {
                                        //subscribe后得到的消息会执行到这里面
					
					String messageContent=new String(message.getPayload(),"UTF-8");
					System.out.println("messageArrived-----接收client-----"+messageContent);
				}
			});
//			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void startReconnect() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {

			public void run() {
				if(!client.isConnected()) {
					connect();
				}
			}
		}, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
	}
	
	
	private void connect() {
		new Thread(new Runnable() {

			public void run() {
				try {
					client.connect(options);
					System.out.println("----------Message--2--------------");
					System.out.println("----------连接成功--------------");
					try {
						client.subscribe(myTopic,1);
						client.subscribe(myTopic, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("----------Message--3--------------");
					System.out.println("----------连接失败，系统正在重连--------------");
				}
			}
		}).start();
	}
}
