package cn.emay.laiqiang.common.utils.redis.pub;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import cn.emay.laiqiang.common.utils.redis.base.JedisBase;

/**
 * 
 * @Title 消息发布工具类型
 * @author zjlwm
 * @date 2016-12-23 下午10:25:38
 * 
 */
@Service
public class JedisPublish extends JedisBase {

	/**
	 * 发布一个消息
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
	 * 发布一个消息
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
