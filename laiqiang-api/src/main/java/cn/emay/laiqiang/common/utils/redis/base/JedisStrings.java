package cn.emay.laiqiang.common.utils.redis.base;

import java.util.List;

import org.springframework.stereotype.Service;


import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;


@Service
public class JedisStrings extends JedisBase{

	 /** 
     * ����key��ȡ��¼ 
     * @param String  key 
     * @return ֵ 
     * */  
    public String get(String key) {  
        //ShardedJedis sjedis = getShardedJedis();  
        Jedis sjedis = getResource();    
        String value = sjedis.get(key);  
        returnResource(sjedis);  
        return value;  
    }  

    /** 
     * ����key��ȡ��¼ 
     * @param byte[] key 
     * @return ֵ 
     * */  
    public byte[] get(byte[] key) {  
        //ShardedJedis sjedis = getShardedJedis();  
        Jedis sjedis = getResource();    
        byte[] value = sjedis.get(key);  
        returnResource(sjedis);  
        return value;  
    }  

    /** 
     * ����й���ʱ��ļ�¼ 
     *  
     * @param String  key 
     * @param int seconds ����ʱ�䣬����Ϊ��λ 
     * @param String value 
     * @return String ����״̬ 
     * */  
    public String setEx(String key, int seconds, String value) {  
        Jedis jedis = getResource();  
        String str = jedis.setex(key, seconds, value);  
        returnResource(jedis);  
        return str;  
    }  

    /** 
     * ����й���ʱ��ļ�¼ 
     *  
     * @param String key 
     * @param int seconds ����ʱ�䣬����Ϊ��λ 
     * @param String  value 
     * @return String ����״̬ 
     * */  
    public String setEx(byte[] key, int seconds, byte[] value) {  
        Jedis jedis = getResource();  
        String str = jedis.setex(key, seconds, value);  
        returnResource(jedis);  
        return str;  
    }  

    /** 
     * ���һ����¼������������key������ʱ�Ų��� 
     * @param String key 
     * @param String value 
     * @return long ״̬�룬1����ɹ���key�����ڣ�0δ���룬key���� 
     * */  
    public long setnx(String key, String value) {  
        Jedis jedis = getResource();  
        long str = jedis.setnx(key, value);  
        returnResource(jedis);  
        return str;  
    }  

    /** 
     * ��Ӽ�¼,�����¼�Ѵ��ڽ�����ԭ�е�value 
     * @param String key 
     * @param String value 
     * @return ״̬�� 
     * */  
    public String set(String key, String value) {  
        return set(SafeEncoder.encode(key), SafeEncoder.encode(value));  
    }  

    /** 
     * ��Ӽ�¼,�����¼�Ѵ��ڽ�����ԭ�е�value 
     * @param String  key 
     * @param String value 
     * @return ״̬�� 
     * */  
    public String set(String key, byte[] value) {  
        return set(SafeEncoder.encode(key), value);  
    }  

    /** 
     * ��Ӽ�¼,�����¼�Ѵ��ڽ�����ԭ�е�value 
     * @param byte[] key 
     * @param byte[] value 
     * @return ״̬�� 
     * */  
    public String set(byte[] key, byte[] value) {  
        Jedis jedis = getResource();  
        String status = jedis.set(key, value);  
        returnResource(jedis);  
        return status;  
    }  

    /** 
     * ��ָ��λ�ÿ�ʼ�������ݣ���������ݻḲ��ָ��λ���Ժ������<br/> 
     * ��:String str1="123456789";<br/> 
     * ��str1������setRange(key,4,0000)��str1="123400009"; 
     * @param String  key 
     * @param long offset 
     * @param String  value 
     * @return long value�ĳ��� 
     * */  
    public long setRange(String key, long offset, String value) {  
        Jedis jedis = getResource();  
        long len = jedis.setrange(key, offset, value);  
        returnResource(jedis);  
        return len;  
    }  

    /** 
     * ��ָ����key��׷��value 
     * @param String  key 
     * @param String value 
     * @return long ׷�Ӻ�value�ĳ��� 
     * **/  
    public long append(String key, String value) {  
        Jedis jedis = getResource();  
        long len = jedis.append(key, value);  
        returnResource(jedis);  
        return len;  
    }  

    /** 
     * ��key��Ӧ��value��ȥָ����ֵ��ֻ��value����תΪ����ʱ�÷����ſ��� 
     * @param String key 
     * @param long number Ҫ��ȥ��ֵ 
     * @return long ��ָ��ֵ���ֵ 
     * */  
    public long decrBy(String key, long number) {  
        Jedis jedis = getResource();  
        long len = jedis.decrBy(key, number);  
        returnResource(jedis);  
        return len;  
    }  

    /** 
     * <b>������Ϊ��ȡΨһid�ķ���</b><br/> 
     * ��key��Ӧ��value����ָ����ֵ��ֻ��value����תΪ����ʱ�÷����ſ��� 
     * @param String  key 
     * @param long number Ҫ��ȥ��ֵ 
     * @return long ��Ӻ��ֵ 
     * */  
    public long incrBy(String key, long number) {  
        Jedis jedis = getResource();  
        long len = jedis.incrBy(key, number);  
        returnResource(jedis);  
        return len;  
    }  

    /** 
     * ��ָ��key��Ӧ��value���н�ȡ  
     * @param String   key 
     * @param long startOffset ��ʼλ��(����) 
     * @param long endOffset ����λ��(����) 
     * @return String ��ȡ��ֵ 
     * */  
    public String getrange(String key, long startOffset, long endOffset) {  
        //ShardedJedis sjedis = getShardedJedis();  
        Jedis sjedis = getResource();    
        String value = sjedis.getrange(key, startOffset, endOffset);  
        returnResource(sjedis);   
        return value;  
    }  

    /** 
     * ��ȡ������ָ��key��Ӧ��value<br/> 
     * ���key���ڷ���֮ǰ��value,���򷵻�null 
     * @param String  key 
     * @param String value 
     * @return String ԭʼvalue��null 
     * */  
    public String getSet(String key, String value) {  
        Jedis jedis = getResource();  
        String str = jedis.getSet(key, value);  
        returnResource(jedis);  
        return str;  
    }  

    /** 
     * ������ȡ��¼,���ָ����key�����ڷ���List�Ķ�Ӧλ�ý���null 
     * @param String keys 
     * @return List<String> ֵ�ü��� 
     * */  
    public List<String> mget(String... keys) {  
        Jedis jedis = getResource();  
        List<String> str = jedis.mget(keys);  
        returnResource(jedis);  
        return str;  
    }  

    /** 
     * �����洢��¼ 
     * @param String keysvalues ��:keysvalues="key1","value1","key2","value2"; 
     * @return String ״̬��  
     * */  
    public String mset(String... keysvalues) {  
        Jedis jedis = getResource();  
        String str = jedis.mset(keysvalues);  
        returnResource(jedis);  
        return str;  
    }  

    /** 
     * ��ȡkey��Ӧ��ֵ�ĳ��� 
     * @param String key 
     * @return valueֵ�ó��� 
     * */  
    public long strlen(String key) {  
        Jedis jedis = getResource();  
        long len = jedis.strlen(key);  
        returnResource(jedis);  
        return len;  
    }
    
    
    
    
    
}
