package cn.emay.laiqiang.common.utils.redis;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.redis.base.JedisBase;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 
 * @Title 监听消息
 * @author zjlwm
 * @date 2016-12-23 下午10:36:27
 *
 */
@Service
public class RedisMQ extends JedisBase{

	/** 
     * 监听消息通道 
     * @param jedisPubSub - 监听任务 
     * @param channels - 要监听的消息通道 
     */  
    public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels) {  
    	Jedis jedis = getResource();    
        try {  
            jedis.subscribe(jedisPubSub, channels);  
        } finally {  
            jedis.close();  
        }  
    }  
  
    /** 
     * 监听消息通道 
     * @param jedisPubSub - 监听任务 
     * @param channels - 要监听的消息通道 
     */  
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {  
        Jedis jedis = null;  
        try {  
        	jedis = getResource();     
            jedis.subscribe(jedisPubSub, channels);  
        } finally {  
            jedis.close();  
        }  
    }  
    
}
