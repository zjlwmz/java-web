package cn.emay.laiqiang.common.utils.redis.base;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;


@Service
public class JedisKeys extends JedisBase{
	
	private  Logger logger = LoggerFactory.getLogger(JedisKeys.class);
	
	 /** 
     * �������key 
     */  
    public String flushAll() {
        Jedis jedis = null;
        String result=null;
        try{
        	jedis = getResource();
        	result = jedis.flushAll();  
        	returnResource(jedis);
        }catch (Exception e) {
        	logger.warn("get {} = {}", e);
		}
        return result;  
    }  

    
    
    /** 
     * ����key 
     * @param String oldkey 
     * @param String  newkey 
     * @return ״̬�� 
     * */  
	public String changeKeyName(String oldkey, String newkey) {
		return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
	} 

    /** 
     * ����key,������key������ʱ��ִ�� 
     * @param String oldkey 
     * @param String newkey  
     * @return ״̬�� 
     * */
    public long renamenx(String oldkey, String newkey) {  
    	 Jedis jedis = null;
         Long status=null;
         try{
         	jedis = getResource();
         	status = jedis.renamenx(oldkey, newkey);   
         	returnResource(jedis);
         }catch (Exception e) {
         	logger.warn("get {} = {}", e);
 		}
        return status; 
    }  

    /** 
     * ����key 
     * @param String oldkey 
     * @param String newkey 
     * @return ״̬�� 
     * */  
    public String rename(byte[] oldkey, byte[] newkey) {  
        Jedis jedis = getResource();  
        String status = jedis.rename(oldkey, newkey);  
        returnResource(jedis);  
        return status;  
    }  

    /** 
     * ����key�Ĺ���ʱ�䣬����Ϊ��λ 
     * @param String key 
     * @param ʱ��,����Ϊ��λ 
     * @return Ӱ��ļ�¼�� 
     * */  
    public long expired(String key, int seconds) {  
        Jedis jedis = getResource();  
        long count = jedis.expire(key, seconds);  
        returnResource(jedis);  
        return count;  
    }  

    /** 
     * ����key�Ĺ���ʱ��,���Ǿ���Ԫ�����������α�׼ʱ�� 1970 �� 1 �� 1 �յ� 00:00:00���������������ƫ������ 
     * @param String key 
     * @param ʱ��,����Ϊ��λ 
     * @return Ӱ��ļ�¼�� 
     * */  
    public long expireAt(String key, long timestamp) {  
        Jedis jedis = getResource();  
        long count = jedis.expireAt(key, timestamp);  
        returnResource(jedis);  
        return count;  
    }  

    /** 
     * ��ѯkey�Ĺ���ʱ�� 
     * @param String key 
     * @return ����Ϊ��λ��ʱ���ʾ 
     * */  
    public long ttl(String key) {  
        //ShardedJedis sjedis = getShardedJedis();  
        Jedis sjedis=getResource();   
        long len = sjedis.ttl(key);  
        returnResource(sjedis);  
        return len;  
    }  

    /** 
     * ȡ����key����ʱ������� 
     * @param key 
     * @return Ӱ��ļ�¼�� 
     * */  
    public long persist(String key) {  
        Jedis jedis = getResource();  
        long count = jedis.persist(key);  
        returnResource(jedis);  
        return count;  
    }  

    /** 
     * ɾ��keys��Ӧ�ļ�¼,�����Ƕ��key 
     * @param String  ... keys 
     * @return ɾ���ļ�¼�� 
     * */  
    public long del(String... keys) {  
        Jedis jedis = getResource();  
        long count = jedis.del(keys);  
        returnResource(jedis);  
        return count;  
    }  

    /** 
     * ɾ��keys��Ӧ�ļ�¼,�����Ƕ��key 
     * @param String .. keys 
     * @return ɾ���ļ�¼�� 
     * */  
    public long del(byte[]... keys) {  
        Jedis jedis = getResource();  
        long count = jedis.del(keys);  
        returnResource(jedis);  
        return count;  
    }  

    /** 
     * �ж�key�Ƿ���� 
     * @param String key 
     * @return boolean 
     * */  
    public boolean exists(String key) {  
        //ShardedJedis sjedis = getShardedJedis();  
        Jedis sjedis=getResource();    
        boolean exis = sjedis.exists(key);  
        returnResource(sjedis);  
        return exis;  
    }  

    /** 
     * ��List,Set,SortSet��������,����������ݽϴ�Ӧ����ʹ��������� 
     * @param String key 
     * @return List<String> ���ϵ�ȫ����¼ 
     * **/  
    public List<String> sort(String key) {  
        //ShardedJedis sjedis = getShardedJedis();  
        Jedis sjedis=getResource();    
        List<String> list = sjedis.sort(key);  
        returnResource(sjedis);  
        return list;  
    }  

    /** 
     * ��List,Set,SortSet���������limit 
     * @param String key 
     * @param SortingParams parame �����������ͻ�limit����ֹλ��. 
     * @return List<String> ȫ���򲿷ּ�¼ 
     * **/  
    public List<String> sort(String key, SortingParams parame) {  
        Jedis sjedis=getResource();   
        List<String> list = sjedis.sort(key, parame);  
        returnResource(sjedis);  
        return list;  
    }  

    /** 
     * ����ָ��key�洢������ 
     * @param String key 
     * @return String string|list|set|zset|hash 
     * **/  
    public String type(String key) {  
        Jedis sjedis=getResource();    
        String type = sjedis.type(key);   
        returnResource(sjedis);  
        return type;  
    }  

    /** 
     * ��������ƥ�������ģʽ�ļ� 
     * @param String  key�ı��ʽ,*��ʾ���������ʾһ�� 
     * */  
    public Set<String> keys(String pattern) {  
        Jedis jedis = getResource();  
        Set<String> set = jedis.keys(pattern);  
        returnResource(jedis);  
        return set;  
    }  
    
    
}
