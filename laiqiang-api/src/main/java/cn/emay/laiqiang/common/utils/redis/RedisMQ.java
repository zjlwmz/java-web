package cn.emay.laiqiang.common.utils.redis;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.redis.base.JedisBase;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 
 * @Title ������Ϣ
 * @author zjlwm
 * @date 2016-12-23 ����10:36:27
 *
 */
@Service
public class RedisMQ extends JedisBase{

	/** 
     * ������Ϣͨ�� 
     * @param jedisPubSub - �������� 
     * @param channels - Ҫ��������Ϣͨ�� 
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
     * ������Ϣͨ�� 
     * @param jedisPubSub - �������� 
     * @param channels - Ҫ��������Ϣͨ�� 
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
