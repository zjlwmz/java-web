package cn.emay.laiqiang.common.utils.redis.pub;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import cn.emay.laiqiang.common.utils.redis.base.JedisBase;

/**
 * 
 * @Title ��Ϣ������������
 * @author zjlwm
 * @date 2016-12-23 ����10:25:38
 * 
 */
@Service
public class JedisPublish extends JedisBase {

	/**
	 * ����һ����Ϣ
	 * 
	 * @param channel
	 * @param message
	 */
	public void publishMsg(String channel, String message) {
		Jedis jedis = getResource();
		try {
			jedis = getResource();
			jedis.publish(channel, message);
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * ����һ����Ϣ
	 * 
	 * @param channel
	 * @param message
	 */
	public void publishMsg(byte[] channel, byte[] message) {
		Jedis jedis = getResource();
		try {
			jedis = getResource();
			jedis.publish(channel, message);
		} catch (Exception e) {
		} finally {
			returnResource(jedis);
		}
	}

}
