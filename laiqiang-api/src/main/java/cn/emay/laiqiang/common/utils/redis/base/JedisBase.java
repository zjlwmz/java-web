package cn.emay.laiqiang.common.utils.redis.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Service
public class JedisBase {

	private  Logger logger = LoggerFactory.getLogger(JedisBase.class);
	
	/**
	 * app reidis �������ӳ�
	 */
	@Autowired
	private JedisPool appReadpool;
	

	
	
	/**
	 * Ĭ�ϻ���ʱ��
	 */
    private final int expire = 60000; 
	
	/** 
     * ���ù���ʱ�� 
     * @author ruan 2013-4-11 
     * @param key 
     * @param seconds 
     */  
    public void expire(String key, int seconds) {  
        if (seconds <= 0) {   
            return;  
        }
        Jedis jedis =null;
        try{
        	 jedis = getResource();
        	 jedis.expire(key, seconds);  
             returnResource(jedis);  
        }catch (Exception e) {
        	logger.warn("get {} = {}", key, e);
		}
       
    }  
    
    
    
    /** 
     * ����Ĭ�Ϲ���ʱ�� 
     * @author ruan 2013-4-11 
     * @param key 
     */  
    public void expire(String key) {  
        expire(key, expire);  
    } 
    
    
    
    
    
    
	
	
	
	/**
	 * ��ȡ��Դ
	 * 
	 * @return
	 * @throws JedisException
	 */
	public  Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = appReadpool.getResource();
		} catch (JedisException e) {
			logger.error("getResource.", e);
//			returnBrokenResource(jedis);
			returnResource(jedis);
			throw e;
		}
		return jedis;
	}

//	/**
//	 * �黹��Դ
//	 * 
//	 * @param jedis
//	 * @param isBroken
//	 */
//	public  void returnBrokenResource(Jedis jedis) {
//		if (jedis != null) {
//			appReadpool.returnBrokenResource(jedis);
//		}
//	}

	/**
	 * �ͷ���Դ
	 * 
	 * @param jedis
	 * @param isBroken
	 */
	public  void returnResource(Jedis jedis) {
		if (jedis != null) {
			appReadpool.returnResource(jedis);
		}
	}
	
	
}
